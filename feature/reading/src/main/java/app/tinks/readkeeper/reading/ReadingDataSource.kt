package app.tinks.readkeeper.reading

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.model.ReadingBook

const val PAGE_SIZE = 10

class ReadingDataSource (
    private val userRepository: UserRepository
) : PagingSource<Int, ReadingBook>() {
    override fun getRefreshKey(state: PagingState<Int, ReadingBook>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReadingBook> {
        val nextPage = params.key ?: 0
        val response: List<ReadingBook> = userRepository.getList(nextPage)
        return LoadResult.Page(
            data = response,
            prevKey = null,
            nextKey = if (response.size < PAGE_SIZE) null else nextPage + 1
        )
    }
}