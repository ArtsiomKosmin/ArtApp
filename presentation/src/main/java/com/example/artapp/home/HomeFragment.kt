package com.example.artapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artapp.Adapter
import com.example.artapp.activities.MainApp
import com.example.artapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy { Adapter(this::toggleFavoriteStatus) }
    private val viewModel: HomeViewModel by activityViewModels {
        HomeViewModel.HomeViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    override fun onResume() {
        super.onResume()
        observeChanges()
    }

    private fun initRcView() {
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = adapter
    }

    private fun observeChanges() {
        viewModel.liveState.observe(viewLifecycleOwner) {
            it.updateUI()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        adapter.setOnItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun toggleFavoriteStatus(id: String, isFavorite: Boolean) {
        viewModel.toggleFavoriteStatus(id, isFavorite)
    }

    override fun onPause() {
        super.onPause()
        viewModel.liveState.removeObservers(viewLifecycleOwner)
    }

    private fun States.updateUI() = when (this) {
        is States.Data -> {
            binding.loadingBar.visibility = View.GONE
            binding.tvError.visibility = View.GONE
            binding.rcView.visibility = View.VISIBLE
            adapter.submitList(arts)

            binding.swipeRefreshLayout.isRefreshing = false
        }

        is States.Loading -> {
            binding.loadingBar.visibility = View.VISIBLE
            binding.tvError.visibility = View.GONE
            binding.rcView.visibility = View.GONE
        }

        is States.Error -> {
            binding.loadingBar.visibility = View.GONE
            binding.tvError.visibility = View.VISIBLE
            binding.rcView.visibility = View.GONE

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