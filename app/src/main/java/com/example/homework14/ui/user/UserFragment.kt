package com.example.homework14.ui.user


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework14.base.BaseFragment
import com.example.homework14.databinding.UserFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserFragment : BaseFragment<UserFragmentBinding>(UserFragmentBinding::inflate) {

    private val viewModel by viewModels<UserViewModel>()
    private val userPagingAdapter = UsersPagingAdapter()

    override fun init() {
        setRecycler()
        setListeners()
        setObservers()
    }

    private fun setRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = userPagingAdapter

        //this is Adapter way and dont needed

/*        binding.recyclerView.adapter = userPagingAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter(userPagingAdapter::retry),
            footer = LoadingStateAdapter(userPagingAdapter::retry)
        )*/
    }

    private fun setListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            userPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadUsersFlow().collectLatest { pagingData ->
                userPagingAdapter.submitData(pagingData)
            }
        }
    }
}