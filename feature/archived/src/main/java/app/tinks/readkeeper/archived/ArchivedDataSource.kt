package app.tinks.readkeeper.archived

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.model.ArchivedBook

const val PAGE_SIZE = 10

class ArchivedDataSource (
    private val userRepository: UserRepository
) : PagingSource<Int, ArchivedBook>() {
    override fun getRefreshKey(state: PagingState<Int, ArchivedBook>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, ArchivedBook> {
        val nextPage = params.key ?: 0
        val response: List<ArchivedBook> = userRepository.getList(nextPage)
        return PagingSource.LoadResult.Page(
            data = response,
            prevKey = null,
            nextKey = if (response.size < PAGE_SIZE) null else nextPage + 1
        )
    }
}