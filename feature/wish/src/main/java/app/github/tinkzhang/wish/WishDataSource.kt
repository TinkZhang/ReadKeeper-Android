package app.github.tinkzhang.wish

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.model.WishBook

const val PAGE_SIZE = 10

class WishDataSource (
    private val userRepository: UserRepository
) : PagingSource<Int, WishBook>() {
    override fun getRefreshKey(state: PagingState<Int, WishBook>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, WishBook> {
        val nextPage = params.key ?: 0
        val response: List<WishBook> = userRepository.getList(nextPage)
        return PagingSource.LoadResult.Page(
            data = response,
            prevKey = null,
            nextKey = if (response.size < PAGE_SIZE) null else nextPage + 1
        )
    }
}