package dr.achim.brewgiant.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dr.achim.brewgiant.ui.screens.Screen
import dr.achim.brewgiant.ui.screens.details.DetailsScreen
import dr.achim.brewgiant.ui.screens.details.DetailsViewModel

fun NavGraphBuilder.detailsScreen(navigateUp: NavigateUp?) {
    composable("${Screen.Details.route}/{$beerIdArg}") {
        val viewModel: DetailsViewModel = hiltViewModel()
        DetailsScreen(
            viewModel = viewModel,
            navigateUp = navigateUp,
        )
    }
}

fun NavController.navigateToDetails(beerId: Int) {
    navigate("${Screen.Details.route}/$beerId")
}

private const val beerIdArg = "beerId"

internal class BeerArgs(val beerId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[beerIdArg]) as String)
}