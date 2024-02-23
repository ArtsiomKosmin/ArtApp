package com.example.artapp.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import javax.inject.Inject

class FavouriteFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: FavouriteViewModel.FavouriteViewModelFactory
    private lateinit var binding: FragmentFavouriteBinding
    private val adapter by lazy { Adapter(this::toggleFavoriteStatus) }
    private val viewModel: FavouriteViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MainApp).appComponent.injectFavorite(this)
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun observeChanges() {
        viewModel.allFavoriteArts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d("Check", "all observe: ${it.size}")
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        adapter.setOnItemClickListener {
            val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(it)
            Log.d("Check", "click on item: ${it}")
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