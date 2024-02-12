package dr.achim.brewgiant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dr.achim.brewgiant.ui.screens.Screen

typealias NavigateUp = (() -> Unit)

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val initialRoute = Screen.Search.route

    var canNavigateBack by rememberSaveable { mutableStateOf(false) }
    navController.addOnDestinationChangedListener{ controller, _, _ ->
        canNavigateBack = controller.previousBackStackEntry != null
    }

    val navigateUp: NavigateUp? = if (canNavigateBack) {
        { navController.navigateUp() }
    } else null

    NavHost(
        navController = navController,
        startDestination = initialRoute,
        modifier = modifier
    ) {
        searchScreen(navigateToDetails = { beerId ->
            navController.navigateToDetails(beerId)
        })
        detailsScreen(navigateUp = navigateUp)
    }
}