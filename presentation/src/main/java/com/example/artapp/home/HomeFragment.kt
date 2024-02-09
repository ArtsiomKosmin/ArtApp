package com.example.artapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.artapp.FragmentManager
import com.example.artapp.MainActivity
import com.example.artapp.R
import com.example.artapp.databinding.FragmentHomeBinding
import com.example.artapp.details.DetailsFragment
import com.example.domain.models.ArtEntity


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeAdapter by lazy { HomeAdapter(this::toggleFavoriteStatus) }
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
        homeAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("key", it)
            }
            FragmentManager.setFragment(DetailsFragment.newInstance(), requireActivity() as MainActivity, bundle)
        }
    }

    private fun initUI() {
        binding.rcView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rcView.adapter = homeAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.liveState.observe(viewLifecycleOwner, Observer{
            it.updateUI()
        })
    }

    private fun toggleFavoriteStatus(id: String, isFavorite: Boolean) {
        viewModel.toggleFavoriteStatus(id, isFavorite)
    }

    private fun States.updateUI() = when (this) {
        is States.Data -> {
            binding.loadingBar.visibility = View.GONE
            binding.tvError.visibility = View.GONE
            binding.rcView.visibility = View.VISIBLE
            homeAdapter.submitList(arts)

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