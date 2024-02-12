package dr.achim.brewgiant.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dr.achim.brewgiant.domain.model.BeerInfo
import dr.achim.brewgiant.domain.usecase.GetBeers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getBeers: GetBeers) : ViewModel() {

    private val _beersFlow = MutableStateFlow<PagingData<BeerInfo>>(PagingData.empty())
    val beersFlow = _beersFlow.asStateFlow()

    init {
        search("")
    }

    fun search(query: String) {
        viewModelScope.launch {
            getBeers(query)
                .distinctUntilChanged()
                .cachedIn(this)
                .collectLatest {
                    _beersFlow.value = it
                }
        }
    }
}
