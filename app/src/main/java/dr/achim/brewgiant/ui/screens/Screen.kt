package dr.achim.brewgiant.ui.screens

enum class Screen {
    Search,
    Details,
    ;

    val route get() = name
}