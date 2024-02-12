package dr.achim.brewgiant.ui.screens.details

import dr.achim.brewgiant.domain.model.BeerInfo

sealed interface DetailsViewState {
    data object Loading : DetailsViewState
    data class Data(val beerInfo: BeerInfo) : DetailsViewState
    data object Error : DetailsViewState
}