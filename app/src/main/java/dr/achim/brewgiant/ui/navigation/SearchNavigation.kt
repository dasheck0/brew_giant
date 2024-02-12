package dr.achim.brewgiant.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dr.achim.brewgiant.ui.screens.Screen
import dr.achim.brewgiant.ui.screens.search.SearchScreen
import dr.achim.brewgiant.ui.screens.search.SearchViewModel

fun NavGraphBuilder.searchScreen(navigateToDetails: (Int) -> Unit) {
    composable(Screen.Search.route) {
        val viewModel: SearchViewModel = hiltViewModel()
        SearchScreen(
            viewModel = viewModel,
            navigateToDetails = navigateToDetails,
        )
    }
}

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(Screen.Search.route, navOptions)
}