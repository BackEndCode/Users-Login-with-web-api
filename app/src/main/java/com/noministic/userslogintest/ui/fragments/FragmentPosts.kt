package com.noministic.userslogintest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.noministic.userslogintest.databinding.FragmentPostsLayoutBinding
import com.noministic.userslogintest.ui.adapters.PostsAdapter
import com.noministic.userslogintest.viewmodels.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPosts : Fragment() {
    private lateinit var binding: FragmentPostsLayoutBinding
    private val viewModel: PostsViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsLayoutBinding.inflate(layoutInflater, container, false)
        viewModel.loadPosts(viewModel.getUserIdFromPrefs())
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner, {
            it.let { binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            it.let {
                if (it.isNotEmpty()) {
                    binding.errorTextview.visibility = View.VISIBLE
                } else {
                    binding.errorTextview.visibility = View.GONE
                }

            }
        })
        viewModel.user.observe(viewLifecycleOwner, {
            it.let { viewModel.loadPosts(it.id) }
        })
        viewModel.posts.observe(viewLifecycleOwner, {
            if (it.size > 0) {
                postsAdapter = PostsAdapter(it)
                binding.recyleUserPosts.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = postsAdapter
                }
            }
        })
    }
}