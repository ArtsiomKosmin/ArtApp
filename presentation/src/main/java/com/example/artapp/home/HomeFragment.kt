package com.example.artapp.home

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.artapp.Adapter
import com.example.artapp.activities.MainApp
import com.example.artapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy { Adapter(this::toggleFavoriteStatus) }
    private lateinit var pref: SharedPreferences
    private val viewModel: HomeViewModel by activityViewModels {
        HomeViewModel.HomeViewModelFactory((context?.applicationContext as MainApp).database)
    }
    private val paginationListener = PaginationListener {
        viewModel.loadRemoteArts()
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
        init()
    }

    override fun onResume() {
        super.onResume()
        observeChanges()
    }

    private fun init() {
        pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        binding.rcView.layoutManager = getLayoutManager()
        binding.rcView.adapter = adapter
        binding.rcView.addOnScrollListener(paginationListener)
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return if (pref.getString("arts_style_key", "linear") == "linear") {
            LinearLayoutManager(requireActivity())
        } else {
            GridLayoutManager(requireContext(), 2)
        }
    }

    private fun observeChanges() {
        viewModel.liveState.observe(viewLifecycleOwner) {
            it.updateUI()
            paginationListener.setLoading(false)
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
        binding.rcView.removeOnScrollListener(paginationListener)
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