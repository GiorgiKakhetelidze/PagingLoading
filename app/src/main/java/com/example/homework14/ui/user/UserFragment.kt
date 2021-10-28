package com.example.homework14.ui.user


import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework14.base.BaseFragment
import com.example.homework14.databinding.UserFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserFragment : BaseFragment<UserFragmentBinding>(UserFragmentBinding::inflate) {

    private val viewModel by viewModels<UserViewModel>()
    private val userPagingAdapter = UsersPagingAdapter()

    override fun init() {
        setRecycler()
        setListeners()
        setObservers()
    }

    private fun setRecycler() {
        binding.recyclerView.layoutManager = GridLayoutManager(context,1)
             binding.recyclerView.adapter = userPagingAdapter.withLoadStateFooter(
                 footer = LoadingStateAdapter()
             )
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadUsersFlow().collectLatest { pagingData ->
                userPagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun setListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            userPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading

            }
        }
    }
}