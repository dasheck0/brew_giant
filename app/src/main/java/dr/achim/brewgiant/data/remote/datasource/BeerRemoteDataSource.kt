package dr.achim.brewgiant.data.remote.datasource

import dr.achim.brewgiant.data.remote.dto.BeerInfoDto

interface BeerRemoteDataSource {
    suspend fun getBeers(searchQuery: String?, page: Int, perPage: Int): List<BeerInfoDto>
    suspend fun getRandomBeer(): BeerInfoDto
    suspend fun getBeers(ids: List<Int>): List<BeerInfoDto>
}