package com.example.homework14.ui.user

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homework14.model.User
import com.example.homework14.model.UserPage
import com.example.homework14.network.NetworkClient
import kotlinx.coroutines.delay
import java.lang.Exception

class UserPagingSource : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageNumber = params.key ?: 1
        return try {
            val response = NetworkClient.userService.getUsers(page = pageNumber)
            val result = response.body()
            if (response.isSuccessful && result != null) {

                var preview: Int? = null
                var next: Int? = null

                if (result.totalPages > pageNumber)
                    next = pageNumber + 1

                if (pageNumber != 1)
                    preview = pageNumber - 1


                LoadResult.Page(data = result.data, prevKey = preview, nextKey = next)
            } else
                LoadResult.Error(Throwable())
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}