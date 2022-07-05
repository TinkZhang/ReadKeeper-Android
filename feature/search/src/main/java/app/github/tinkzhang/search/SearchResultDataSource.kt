package app.github.tinkzhang.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.github.tinkzhang.search.network.GoogleBookService
import app.github.tinkzhang.search.network.SIZE
import app.github.tinkzhang.search.network.googlebook.GoogleBookItem
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class SearchResultDataSource(
    private val keyword: String,
) : PagingSource<Int, GoogleBookItem>() {

    override fun getRefreshKey(state: PagingState<Int, GoogleBookItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GoogleBookItem> {
        return try {
            val nextPage = params.key ?: 0
            val response = GoogleBookService.instance.search(keyword, nextPage * SIZE)
            val totalItems = response.totalItems
            Timber.d(
                """
                Search total items: $totalItems.
                The current page is ${params.key}, and the next page will be $nextPage
                Result for current page is ${response.googleBookItems.map { it.volumeInfo.title }}
            """
            )
            LoadResult.Page(
                data = response.googleBookItems,
                prevKey = null,
                nextKey = if (totalItems < SIZE) null else nextPage + 1
            )
        } catch (exception: IOException) {
            Timber.d("Search failed: ${exception.localizedMessage}")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Timber.d("Search failed: ${exception.code()}")
            return LoadResult.Error(exception)
        }
    }
}