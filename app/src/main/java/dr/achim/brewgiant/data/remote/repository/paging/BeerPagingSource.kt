package dr.achim.brewgiant.data.remote.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dr.achim.brewgiant.common.Constants
import dr.achim.brewgiant.data.remote.datasource.BeerRemoteDataSource
import dr.achim.brewgiant.domain.model.BeerInfo
import retrofit2.HttpException
import java.io.IOException

class BeerPagingSource(
    private val remoteDataSource: BeerRemoteDataSource,
    private val searchQuery: String?,
) : PagingSource<Int, BeerInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerInfo> {
        return try {
            val pageNumber = params.key ?: Constants.INITIAL_PAGE
            val beers = remoteDataSource.getBeers(
                searchQuery = searchQuery,
                page = pageNumber,
                perPage = Constants.MAX_PAGE_SIZE
            )
            LoadResult.Page(
                data = beers.map { it.toModel() },
                prevKey = if (pageNumber > Constants.INITIAL_PAGE) pageNumber - 1 else null,
                nextKey = if (beers.isNotEmpty()) pageNumber + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BeerInfo>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}