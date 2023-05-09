package com.example.paybacktask.presentation.ui.composeui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.paybacktask.domain.entity.ImageViewEntities
import com.example.paybacktask.presentation.ui.theme.PayBackTaskTheme
import com.example.paybacktask.presentation.viewmodel.MainViewModel


@Composable
fun DetailsScreen(
    viewModel: MainViewModel,
    itemId: Int
) {
    LaunchedEffect(viewModel.uiState) {
        viewModel.getImage(itemId)
    }
    val state by viewModel.detailsUiState.collectAsState()

    PayBackTaskTheme {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            val imagesDomainItem = state.images.firstOrNull()
            imagesDomainItem?.let {
                DetailsItem(imagesDomainItem)
            }

        }
    }
}


@Composable
fun DetailsItem(imagesDomainItem: ImageViewEntities.ImagesDomainMappedItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            painter = rememberAsyncImagePainter(imagesDomainItem.largeImageURL),
            contentDescription = "",
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalTags(imagesDomainItem.tags)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Details",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        StatisticsRow(
            views = imagesDomainItem.statistics.views,
            likes = imagesDomainItem.statistics.likes,
            downloads = imagesDomainItem.statistics.downloads,
            comments = imagesDomainItem.statistics.comments,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "About",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = imagesDomainItem.userInfo.user.toString(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun StatisticsRow(views: Int, likes: Int, downloads: Int, comments: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Statistic(text = "Views", value = views)
        Statistic(text = "Likes", value = likes)
        Statistic(text = "Downloads", value = downloads)
        Statistic(text = "Comments", value = comments)
    }
}

@Composable
fun Statistic(text: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}
