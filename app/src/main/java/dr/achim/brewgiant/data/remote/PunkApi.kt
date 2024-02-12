package dr.achim.brewgiant.data.remote

import dr.achim.brewgiant.data.remote.dto.BeerInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApi {

    @GET("beers")
    suspend fun getBeers(
        @Query("beer_name") beerName: String? = null,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<BeerInfoDto>

    @GET("beers/random")
    suspend fun getRandomBeer(): BeerInfoDto

    @GET("beers")
    suspend fun getBeers(@Query("ids") ids: List<Int>): List<BeerInfoDto>

    companion object {
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }
}
