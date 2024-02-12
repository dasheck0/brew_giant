package dr.achim.brewgiant.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dr.achim.brewgiant.data.remote.datasource.BeerRemoteDataSource
import dr.achim.brewgiant.data.remote.repository.paging.BeerPagingSource
import dr.achim.brewgiant.domain.Resource
import dr.achim.brewgiant.domain.model.BeerInfo
import dr.achim.brewgiant.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(private val remoteDataSource: BeerRemoteDataSource) :
    BeerRepository {

    private val pagingConfig = PagingConfig(pageSize = 20, prefetchDistance = 2)

    override fun getBeers(searchQuery: String?): Flow<PagingData<BeerInfo>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                BeerPagingSource(remoteDataSource, searchQuery)
            }
        ).flow
    }

    override fun getRandomBeer(): Flow<Resource<BeerInfo>> = flow {
        emit(Resource.Loading())

        val result = try {
            val remoteBeer = remoteDataSource.getRandomBeer().toModel()
            Resource.Success(remoteBeer)
        } catch (e: Exception) {
            Resource.Error(message = "Oops, something went wrong!")
        }

        emit(result)
    }

    override fun getBeer(id: String): Flow<Resource<BeerInfo>> = flow {
        emit(Resource.Loading())

        val result = try {
            val remoteBeer = remoteDataSource.getBeers(listOf(id.toInt()))
                .single()
                .toModel()
            Resource.Success(remoteBeer)
        } catch (e: Exception) {
            Resource.Error(message = "Oops, something went wrong!")
        }

        emit(result)
    }
}