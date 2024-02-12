package dr.achim.brewgiant.domain.repository

import androidx.paging.PagingData
import dr.achim.brewgiant.domain.Resource
import dr.achim.brewgiant.domain.model.BeerInfo
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    fun getBeers(searchQuery: String?): Flow<PagingData<BeerInfo>>
    fun getRandomBeer(): Flow<Resource<BeerInfo>>
    fun getBeer(id: String): Flow<Resource<BeerInfo>>
}
