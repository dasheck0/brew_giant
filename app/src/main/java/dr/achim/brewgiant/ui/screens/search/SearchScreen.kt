package dr.achim.brewgiant.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import dr.achim.brewgiant.R
import dr.achim.brewgiant.domain.model.BeerInfo
import dr.achim.brewgiant.ui.components.EmptyView
import dr.achim.brewgiant.ui.components.SearchBarWithHistory
import dr.achim.brewgiant.ui.theme.AppTheme
import dr.achim.brewgiant.ui.theme.LocalSpacing

@Composable
fun SearchScreen(viewModel: SearchViewModel, navigateToDetails: (beerId: Int) -> Unit) {

    val beers = viewModel.beersFlow.collectAsLazyPagingItems()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val loading by remember {
        derivedStateOf {
            beers.loadState.refresh is LoadState.Loading
        }
    }

    Scaffold(
        topBar = {
            SearchBarWithHistory(
                searchQuery = searchQuery,
                placeholderText = stringResource(R.string.placeholder_search),
                onQueryChange = { searchQuery = it },
                onSearch = viewModel::search
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            if (beers.itemCount == 0) {
                val (title, description) = if (searchQuery.isEmpty()) {
                    stringResource(R.string.empty_title_default) to stringResource(R.string.empty_description_default)
                } else {
                    stringResource(R.string.empty_title_no_results) to stringResource(
                        R.string.empty_description_for_result,
                        searchQuery
                    )
                }
                EmptyView(
                    imageVector = Icons.Default.Search,
                    title = title,
                    description = description
                )
            } else {
                ItemList(beers, navigateToDetails)
            }
        }
    }
}


@Composable
private fun ItemList(
    lazyPagingItems: LazyPagingItems<BeerInfo>,
    navigateToDetails: (beerId: Int) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(LocalSpacing.current.s)
    ) {

        items(
            lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            val beerInfo = lazyPagingItems[index]
            if (beerInfo != null) {
                BeerItem(
                    id = beerInfo.id,
                    name = beerInfo.name,
                    description = beerInfo.description,
                    abv = beerInfo.abv,
                    imageUrl = beerInfo.imageUrl,
                    navigateToDetails = navigateToDetails
                )
            }

            if (index <= lazyPagingItems.itemCount) {
                Spacer(Modifier.height(LocalSpacing.current.s))
            }
        }
    }
}

@Composable
private fun BeerItem(
    id: Int,
    name: String,
    description: String,
    abv: Double,
    imageUrl: String?,
    navigateToDetails: (beerId: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .heightIn(min = 128.dp)
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        onClick = {
            navigateToDetails(id)
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {

        ListItem(
            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            leadingContent = {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    loading = {
                        CircularProgressIndicator(Modifier.padding(24.dp))
                    },
                    error = {
                        Icon(
                            imageVector = Icons.Default.SportsBar,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp)
                        )
                    },
                    modifier = Modifier.size(96.dp),
                )
            },
            overlineContent = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = LocalSpacing.current.s),
                        text = "$abv %",
                        maxLines = 1,
                        textAlign = TextAlign.End
                    )
                }
            },
            headlineContent = {
                Text(
                    text = description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            trailingContent = {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier.padding(start = LocalSpacing.current.s)
                    )
                }
            }
        )
    }
}


@Preview
@Composable
private fun BeerItemPreview() {
    AppTheme {
        BeerItem(
            id = 1,
            name = "Füchschen",
            description = "Echt lekker",
            abv = 5.0,
            "https://fuechschen.de/wp-content/uploads/2022/08/Alt-Bier-Dose-tropfen24.png"
        ) {}
    }
}