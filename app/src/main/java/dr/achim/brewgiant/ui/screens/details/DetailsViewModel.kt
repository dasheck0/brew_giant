package dr.achim.brewgiant.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dr.achim.brewgiant.domain.Resource
import dr.achim.brewgiant.domain.model.BeerInfo
import dr.achim.brewgiant.domain.usecase.GetBeer
import dr.achim.brewgiant.ui.navigation.BeerArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBeer: GetBeer
) : ViewModel() {

    private val _viewState = MutableStateFlow<DetailsViewState>(DetailsViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val beerArgs = BeerArgs(savedStateHandle)

    init {
        getBeer(beerArgs.beerId)
            .onEach(::handleData)
            .launchIn(viewModelScope)
    }

    private fun handleData(resource: Resource<BeerInfo>) {
        _viewState.value = when (resource) {
            is Resource.Success -> DetailsViewState.Data(resource.data)
            is Resource.Error -> DetailsViewState.Error
            is Resource.Loading -> DetailsViewState.Loading
        }
    }
}