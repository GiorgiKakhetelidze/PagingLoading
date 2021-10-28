package com.example.homework14.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData

class UserViewModel : ViewModel() {


    fun loadUsersFlow() =
        Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { UserPagingSource() })
            .flow.cachedIn(viewModelScope)

}