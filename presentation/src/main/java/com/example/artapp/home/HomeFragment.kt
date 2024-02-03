package com.example.artapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.artapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeAdapter by lazy { HomeAdapter() }
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initViewModel()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun initUI() {
//        binding.rcView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rcView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rcView.adapter = homeAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.liveState.observe(viewLifecycleOwner, Observer{
            it.updateUI()
        })
    }

    private fun UIState.States.updateUI() = when (this) {
        is UIState.States.Data -> {
            binding.loadingBar.visibility = View.INVISIBLE
            binding.tvError.visibility = View.INVISIBLE
            binding.rcView.visibility = View.VISIBLE
            homeAdapter.submitList(arts)

            binding.swipeRefreshLayout.isRefreshing = false
        }

        is UIState.States.Loading -> {
            binding.loadingBar.visibility = View.VISIBLE
            binding.tvError.visibility = View.INVISIBLE
            binding.rcView.visibility = View.INVISIBLE
        }
        is UIState.States.Error -> {
            binding.loadingBar.visibility = View.INVISIBLE
            binding.tvError.visibility = View.VISIBLE
            binding.rcView.visibility = View.INVISIBLE

            binding.swipeRefreshLayout.isRefreshing = false
        }

    }

    companion object {
        @JvmStatic
        private var instance: HomeFragment? = null

        fun getInstance(): HomeFragment {
            if (instance == null) {
                instance = HomeFragment()
            }
            return instance!!
        }
    }
}