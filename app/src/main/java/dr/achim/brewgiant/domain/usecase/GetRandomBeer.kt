package dr.achim.brewgiant.domain.usecase

import dr.achim.brewgiant.domain.Resource
import dr.achim.brewgiant.domain.model.BeerInfo
import dr.achim.brewgiant.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomBeer @Inject constructor(private val repository: BeerRepository) {
    operator fun invoke(): Flow<Resource<BeerInfo>> {
        return repository.getRandomBeer()
    }
}