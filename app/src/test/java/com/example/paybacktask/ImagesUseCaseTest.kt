import com.example.paybacktask.data.constant.Constants
import com.example.paybacktask.data.constant.NetworkStatus
import com.example.paybacktask.data.remote.entity.Hit
import com.example.paybacktask.data.remote.entity.getImagesDomainMappedItem
import com.example.paybacktask.domain.ImageRepo
import com.example.paybacktask.domain.entity.ImageViewEntities
import com.example.paybacktask.domain.usecases.ImagesUseCase
import com.example.paybacktask.helper.BaseCoroutineTest
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ImagesUseCaseTest : BaseCoroutineTest() {

    @MockK
    private lateinit var imageRepo: ImageRepo

    private lateinit var imagesUseCase: ImagesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        imagesUseCase = ImagesUseCase(imageRepo)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when fetchImage returns data, then ImageDomainEntity is sent to the channel`() =
        coTestScope.runTest {
            // Given
            val searchString = "nature"
            val hit = Hit(
                id = 1,
                tags = "nature,outdoors",
                comments = 123,
                likes = 11,
                downloads = 11,
                fullHDURL = "sss",
                user = "",
                userId = 11,
                webformatHeight = 11,
                webformatURL = "11",
                imageHeight = 11,
                imageSize = 11,
                largeImageURL = "",
                imageURL = "",
                previewURL = "",
                imageWidth = 12,
                pageURL = "",
                previewHeight = 11,
                previewWidth = 11,
                type = "",
                userImageURL = "",
                views = 11,
                webformatWidth = 11
            )

            coEvery { imageRepo.fetchImages(searchString) } returns NetworkStatus.Success(listOf(hit))

            // When
            val result = imagesUseCase(searchString).toList()

            // Then
            val expectedResponse = ImageViewEntities.ImageDomainEntity(
                listOf(hit.getImagesDomainMappedItem())
            )
            assertEquals(expectedResponse, result.first())
            coVerify(exactly = 1) { imageRepo.fetchImages(searchString) }
            confirmVerified(imageRepo)
        }

    @Test
    fun `when fetchImage returns empty list, then Failure is sent to the channel`() =
        coTestScope.runTest {
            // Given
            val searchString = "nature"
            coEvery { imageRepo.fetchImages(searchString) } returns NetworkStatus.Success(emptyList())

            // When
            val result = imagesUseCase(searchString).toList()

            // Then
            val expectedResponse = ImageViewEntities.Failure(Constants.UNKNOWN_NETWORK_EXCEPTION)
            assertEquals(expectedResponse, result.first())
            coVerify(exactly = 1) { imageRepo.fetchImages(searchString) }
            confirmVerified(imageRepo)
        }
}
