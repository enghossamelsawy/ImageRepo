package com.example.paybacktask

import com.example.paybacktask.data.constant.NetworkStatus
import com.example.paybacktask.data.local.datasource.ImagesLocalDataSource
import com.example.paybacktask.data.remote.datasource.RemoteImagesDataSource
import com.example.paybacktask.data.remote.entity.Hit
import com.example.paybacktask.data.remote.entity.ImageResponse
import com.example.paybacktask.data.repository.ImageRepoImpl
import com.example.paybacktask.helper.BaseCoroutineTest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImageRepoImplTest : BaseCoroutineTest() {
    // Create mock dependencies
    private val remoteImagesDataSource: RemoteImagesDataSource = mockk()
    private val localDataSource: ImagesLocalDataSource = mockk()

    // Create instance of ImageRepoImpl
    private val imageRepo = ImageRepoImpl(remoteImagesDataSource, localDataSource)

    @Test
    fun `fetchImage should return images from remote data source if available`() =
        coTestScope.runTest {
            // Set up mock behavior for remote data source

            val fakeImagesDomainItem = Hit(
                id = 123,
                comments = 10,
                downloads = 50,
                fullHDURL = "https://example.com/image",
                imageHeight = 1080,
                imageSize = 500000,
                imageURL = "https://example.com/image.jpg",
                imageWidth = 1920,
                largeImageURL = "https://example.com/large-image.jpg",
                likes = 20,
                pageURL = "https://example.com/page",
                previewHeight = 480,
                previewURL = "https://example.com/preview.jpg",
                previewWidth = 640,
                tags = "tag1, tag2",
                type = "jpg",
                user = "user1",
                userId = 456,
                userImageURL = "https://example.com/user-image.jpg",
                views = 100,
                webformatHeight = 720,
                webformatURL = "https://example.com/webformat.jpg",
                webformatWidth = 1280
            )

            val mockImages = listOf(
                fakeImagesDomainItem,
                fakeImagesDomainItem,
                fakeImagesDomainItem
            )
            val imageResponse = ImageResponse(
                listOf(
                    fakeImagesDomainItem,
                    fakeImagesDomainItem,
                    fakeImagesDomainItem
                ), null, null
            )
            val mockNetworkStatus = NetworkStatus.Success<ImageResponse>(imageResponse)
            coEvery { remoteImagesDataSource.getImages(any()) } returns mockNetworkStatus

            // Set up mock behavior for local data source
            coEvery { localDataSource.saveImages(any()) } just Runs
            coEvery { localDataSource.fetchImages(any()) } returns emptyList()

            // Call fetchImage method with search string
            val searchString = "cats"
            val result = imageRepo.fetchImages(searchString)

            coTestScheduler.advanceUntilIdle()

            // Verify that remote data source was called with search string
            coVerify { remoteImagesDataSource.getImages(searchString) }


            assertEquals(mockImages, result.data)
        }

    @Test
    fun `fetchImage should return images from local data source if remote data source returns error`() =
        runBlocking {
            // Set up mock behavior for remote data source
            val mockNetworkStatus = NetworkStatus.Error<ImageResponse>("Error fetching images")
            coEvery { remoteImagesDataSource.getImages(any()) } returns mockNetworkStatus

            val fakeImagesDomainItem = Hit(
                id = 123,
                comments = 10,
                downloads = 50,
                fullHDURL = "https://example.com/image",
                imageHeight = 1080,
                imageSize = 500000,
                imageURL = "https://example.com/image.jpg",
                imageWidth = 1920,
                largeImageURL = "https://example.com/large-image.jpg",
                likes = 20,
                pageURL = "https://example.com/page",
                previewHeight = 480,
                previewURL = "https://example.com/preview.jpg",
                previewWidth = 640,
                tags = "tag1, tag2",
                type = "jpg",
                user = "user1",
                userId = 456,
                userImageURL = "https://example.com/user-image.jpg",
                views = 100,
                webformatHeight = 720,
                webformatURL = "https://example.com/webformat.jpg",
                webformatWidth = 1280
            )

            // Set up mock behavior for local data source
            val mockImages = listOf(
                fakeImagesDomainItem,
                fakeImagesDomainItem,
                fakeImagesDomainItem
            )
            coEvery { localDataSource.fetchImages(any()) } returns mockImages

            // Call fetchImage method with search string
            val searchString = "dogs"
            val result = imageRepo.fetchImages(searchString)

            // Verify that remote data source was called with search string
            coVerify { remoteImagesDataSource.getImages(searchString) }

            // Verify that local data source was called to fetch images
            coVerify { localDataSource.fetchImages(searchString) }

            // Verify that result contains the mock images
            assertEquals(mockImages, result.data)
        }
}


