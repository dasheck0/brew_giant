package dr.achim.brewgiant.data.remote.datasource

import dr.achim.brewgiant.data.remote.PunkApi
import dr.achim.brewgiant.data.remote.dto.BeerInfoDto
import javax.inject.Inject

class BeerRemoteDataSourceImpl @Inject constructor(private val api: PunkApi) :
    BeerRemoteDataSource {

    override suspend fun getBeers(
        searchQuery: String?,
        page: Int,
        perPage: Int
    ): List<BeerInfoDto> {
        return api.getBeers(
            beerName = searchQuery,
            page = page,
            perPage = perPage
        )
    }

    override suspend fun getRandomBeer(): BeerInfoDto {
        return api.getRandomBeer()
    }

    override suspend fun getBeers(ids: List<Int>): List<BeerInfoDto> {
        return api.getBeers(ids)
    }
}