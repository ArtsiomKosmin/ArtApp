package com.example.artapp.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artapp.activities.MainApp
import com.example.artapp.databinding.FragmentFavouriteBinding
import com.example.artapp.Adapter
import com.example.artapp.FragmentManager
import com.example.artapp.activities.MainActivity
import com.example.artapp.details.DetailsFragment

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val adapter by lazy { Adapter(this::toggleFavoriteStatus) }
    private val viewModel: FavouriteViewModel by activityViewModels {
        FavouriteViewModel.FavouriteViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.favoriteLiveState.observe(viewLifecycleOwner) {
            it.updateUI()
        }
        binding.toolbar.setNavigationOnClickListener {
            FragmentManager.popBackStack(requireActivity() as MainActivity)
        }
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("key", it)
            }
            FragmentManager.setFragment(DetailsFragment.newInstance(), requireActivity() as MainActivity, bundle)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadLocalList()
        }
    }

    private fun initRcView() {
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoriteRecyclerView.adapter = adapter
    }

    private fun toggleFavoriteStatus(id: String, isFavorite: Boolean) {
        viewModel.toggleFavoriteStatus(id)
    }

    private fun FavoriteStates.updateUI() = when (this) {
        is FavoriteStates.Data -> {
            binding.favoriteProgressBar.visibility = View.GONE
            binding.favoriteTextView.visibility = View.GONE
            adapter.submitList(arts)

            binding.swipeRefreshLayout.isRefreshing = false
        }

        is FavoriteStates.Error -> {
            binding.favoriteProgressBar.visibility = View.GONE
            binding.favoriteTextView.visibility = View.VISIBLE

            binding.swipeRefreshLayout.isRefreshing = false
        }

        is FavoriteStates.Update -> {
            viewModel.loadLocalList()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }
}