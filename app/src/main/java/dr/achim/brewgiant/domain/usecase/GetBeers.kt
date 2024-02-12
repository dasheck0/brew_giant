package dr.achim.brewgiant.domain.usecase

import androidx.paging.PagingData
import dr.achim.brewgiant.domain.model.BeerInfo
import dr.achim.brewgiant.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeers @Inject constructor(private val repository: BeerRepository) {
    operator fun invoke(searchQuery: String?): Flow<PagingData<BeerInfo>> {
        return repository.getBeers(searchQuery?.ifBlank { null })
    }
}