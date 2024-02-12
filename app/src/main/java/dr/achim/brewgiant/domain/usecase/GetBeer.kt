package dr.achim.brewgiant.domain.usecase

import dr.achim.brewgiant.domain.Resource
import dr.achim.brewgiant.domain.model.BeerInfo
import dr.achim.brewgiant.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeer @Inject constructor(private val repository: BeerRepository) {
    operator fun invoke(id: String): Flow<Resource<BeerInfo>> {
        return repository.getBeer(id)
    }
}