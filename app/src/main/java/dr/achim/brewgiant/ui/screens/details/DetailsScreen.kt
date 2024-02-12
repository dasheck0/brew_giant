package dr.achim.brewgiant.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dr.achim.brewgiant.R
import dr.achim.brewgiant.domain.model.BeerInfo
import dr.achim.brewgiant.ui.navigation.NavigateUp
import dr.achim.brewgiant.ui.theme.LocalSpacing

@Composable
fun DetailsScreen(viewModel: DetailsViewModel, navigateUp: NavigateUp?) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    when (val state = viewState) {
        is DetailsViewState.Loading -> DetailsScreenLoading()
        is DetailsViewState.Data -> DetailsScreenData(navigateUp, state.beerInfo)
        is DetailsViewState.Error -> DetailsScreenError()
    }
}

@Composable
private fun DetailsScreenLoading() {
    Surface(Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun DetailsScreenError() {
    Surface(Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.common_error))
        }
    }
}

@Composable
private fun DetailsScreenData(navigateUp: NavigateUp?, beerInfo: BeerInfo) {
    Scaffold(
        topBar = { DetailsScreenAppBar(navigateUp) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(beerInfo.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(LocalSpacing.current.m),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.xs)
            ) {

                AsyncImage(
                    modifier = Modifier.sizeIn(maxHeight = 196.dp),
                    model = beerInfo.imageUrl,
                    contentDescription = null
                )

                Section(stringResource(R.string.section_name))
                Text(
                    text = beerInfo.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Section(stringResource(R.string.section_description))
                Text(
                    text = beerInfo.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Section(stringResource(R.string.section_attributes))
                InfoRow(
                    label = stringResource(R.string.attribute_alcohol),
                    value = "${beerInfo.abv} %"
                )
                InfoRow(
                    label = stringResource(R.string.attribute_volume),
                    value = "${beerInfo.volume.value} ${beerInfo.volume.unit}"
                )

                Section(stringResource(R.string.section_ingredients))
                beerInfo.ingredients.hops.forEach {
                    InfoRow(label = it.name, value = "${it.amount.value} ${it.amount.unit}")
                }
                beerInfo.ingredients.malt.forEach {
                    InfoRow(label = it.name, value = "${it.amount.value} ${it.amount.unit}")
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreenAppBar(navigateUp: NavigateUp?) {
    CenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            navigateUp?.let {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = LocalSpacing.current.s,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alignByBaseline()
        )
    }
}

@Composable
private fun Section(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(
            top = LocalSpacing.current.l,
            bottom = LocalSpacing.current.s
        )
    )
}