package com.example.paybacktask.presentation.ui.composeui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.paybacktask.R
import com.example.paybacktask.domain.entity.ImageViewEntities
import com.example.paybacktask.presentation.ui.theme.PayBackTaskTheme
import com.example.paybacktask.presentation.viewmodel.MainViewModel


@Composable
fun SearchImageScreen(
    viewModel: MainViewModel,
    onImageClicked: (ImageViewEntities.ImagesDomainMappedItem) -> Unit
) {

    LaunchedEffect(viewModel.uiState) {
        viewModel.getImages("fruits")
    }
    val state by viewModel.uiState.collectAsState()

    PayBackTaskTheme {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            FullScreenProgressBar(state.inProgress)

            if (state.errorMessage.isNotBlank()) {
                FullScreenErrorView(state.errorMessage)
            }
            Column {
                ExpandableSearchView(
                    searchDisplay = "fruits",
                    expandedInitially = true,
                    onSearchDisplayChanged = {
                        if (it.length > 3) {
                            viewModel.getImages(it)
                        }
                    },
                    onSearchDisplayClosed = {

                    })

                SetContentList(state.images, PaddingValues(), onImageClicked)

            }
        }
    }

}


@Composable
fun ExpandableSearchView(
    searchDisplay: String,
    onSearchDisplayChanged: (String) -> Unit,
    onSearchDisplayClosed: () -> Unit,
    modifier: Modifier = Modifier,
    expandedInitially: Boolean = false,
    tint: Color = MaterialTheme.colorScheme.onPrimary
) {
    val (expanded, onExpandedChanged) = remember {
        mutableStateOf(expandedInitially)
    }


    Crossfade(targetState = expanded, label = "") { isSearchFieldVisible ->
        when (isSearchFieldVisible) {
            true -> ExpandedSearchView(
                searchDisplay = searchDisplay,
                onSearchDisplayChanged = onSearchDisplayChanged,
                onSearchDisplayClosed = onSearchDisplayClosed,
                onExpandedChanged = onExpandedChanged,
                modifier = modifier,
                tint = tint
            )

            false -> CollapsedSearchView(
                onExpandedChanged = onExpandedChanged, modifier = modifier, tint = tint
            )
        }
    }
}

@Composable
fun SearchIcon(iconTint: Color) {
    Icon(
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = "search icon",
        tint = iconTint
    )
}

@Composable
fun CollapsedSearchView(
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Tasks",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
        IconButton(onClick = { onExpandedChanged(true) }) {
            SearchIcon(iconTint = tint)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedSearchView(
    searchDisplay: String,
    onSearchDisplayChanged: (String) -> Unit,
    onSearchDisplayClosed: () -> Unit,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val focusManager = LocalFocusManager.current

    val textFieldFocusRequester = remember { FocusRequester() }

    SideEffect {
        textFieldFocusRequester.requestFocus()
    }

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(searchDisplay, TextRange(searchDisplay.length)))
    }


    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onExpandedChanged(false)
            onSearchDisplayClosed()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "back icon",
                tint = tint
            )
        }
        TextField(value = textFieldValue, onValueChange = {
            textFieldValue = it
            onSearchDisplayChanged(it.text)
        }, trailingIcon = {
            SearchIcon(iconTint = tint)
        }, modifier = Modifier
            .fillMaxWidth()
            .focusRequester(textFieldFocusRequester), label = {
            Text(text = "Search", color = tint)
        }, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ), keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        })
        )
    }
}


@Preview
@Composable
fun CollapsedSearchViewPreview() {
    PayBackTaskTheme {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            ExpandableSearchView(searchDisplay = "",
                onSearchDisplayChanged = {},
                onSearchDisplayClosed = {})
        }
    }
}

@Composable
fun SetContentList(
    images: List<ImageViewEntities.ImagesDomainMappedItem>,
    paddingValues: PaddingValues,
    onImageClicked: (ImageViewEntities.ImagesDomainMappedItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(
            images
        ) {
            ImageCard(it, onImageClicked = onImageClicked)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    image: ImageViewEntities.ImagesDomainMappedItem,
    onImageClicked: (ImageViewEntities.ImagesDomainMappedItem) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(8.dp),
        onClick = { onImageClicked.invoke(image) }) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .heightIn(min = 80.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                painter = rememberAsyncImagePainter(image.imageThumbnailUrl),
                contentDescription = "",
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Taken By: ${image.userInfo.user ?: ""}",
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tags",
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalTags(image.tags)
            }
        }
    }
}


@Preview
@Composable
fun ExpandedSearchViewPreview() {
    PayBackTaskTheme {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            ExpandableSearchView(searchDisplay = "",
                onSearchDisplayChanged = {},
                expandedInitially = true,
                onSearchDisplayClosed = {})
        }
    }
}


