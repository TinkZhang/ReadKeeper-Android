package com.github.tinkzhang.readkeeper.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.tinkzhang.readkeeper.search.network.GoogleBookService
import com.github.tinkzhang.readkeeper.search.network.SIZE
import github.tinkzhang.readkeeper.search.model.googlebook.Item
import retrofit2.HttpException
import java.io.IOException

class SearchResultDataSource(
    val keyword: String,
) : PagingSource<Int, Item>() {

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val nextPage = params.key ?: 1
            val response = GoogleBookService.instance.search(keyword, nextPage * SIZE)
            val totalItems = response.totalItems
            LoadResult.Page(
                data = response.items,
                prevKey = null,
                nextKey = if (totalItems < nextPage * SIZE) null else nextPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}