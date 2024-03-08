package com.example.artapp.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artapp.activities.MainApp
import com.example.artapp.databinding.FragmentFavouriteBinding
import com.example.artapp.Adapter
import com.example.artapp.viewBinding
import javax.inject.Inject

class FavouriteFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: FavouriteViewModel.FavouriteViewModelFactory
    private val binding by viewBinding(FragmentFavouriteBinding::inflate)
    private val adapter by lazy { Adapter(this::toggleFavoriteStatus) }
    private val viewModel: FavouriteViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MainApp).appComponent.injectFavorite(this)
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

    private fun observeChanges() {
        viewModel.allFavoriteArts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        adapter.setOnItemClickListener {
            val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.allFavoriteArts.removeObservers(viewLifecycleOwner)
    }

    private fun initRcView() {
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoriteRecyclerView.adapter = adapter
    }

    private fun toggleFavoriteStatus(id: String, isFavorite: Boolean) {
        viewModel.toggleFavoriteStatus(id)
    }
}