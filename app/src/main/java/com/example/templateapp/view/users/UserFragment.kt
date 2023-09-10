package com.example.templateapp.view.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.templateapp.databinding.FragmentUserBinding
import com.example.templateapp.utils.Resource
import com.example.templateapp.utils.gone
import com.example.templateapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment: Fragment() {

    private lateinit var binding: FragmentUserBinding

    private val viewModel by viewModels<UserViewModel>()
    private val userAdapter by lazy {
        UserAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            swipeRefreshLayout.setOnRefreshListener {
                lifecycleScope.launch {
                    viewModel.getUsers()
                }
            }

            restaurantsList.apply {
                adapter = userAdapter
            }

            viewModel.user.observe(viewLifecycleOwner) { result ->
                userAdapter.submitList(result.data)
                when (result) {
                    is Resource.Loading -> {
                        if (!swipeRefreshLayout.isRefreshing) {
                            progressBar.visible()
                        }
                        progressBar.isVisible = result.data.isNullOrEmpty()
                    }
                    is Resource.Success -> {
                        progressBar.gone()
                        swipeRefreshLayout.isRefreshing = false
                    }
                    is Resource.Error -> {
                        progressBar.gone()
                        textViewError.isVisible = result.data.isNullOrEmpty()
                        swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
        }
    }

}