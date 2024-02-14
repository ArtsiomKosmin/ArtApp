package com.example.artapp.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.artapp.FragmentManager
import com.example.artapp.ImageLoader
import com.example.artapp.activities.MainActivity
import com.example.artapp.activities.MainApp
import com.example.artapp.databinding.FragmentDetailsBinding
import com.example.domain.models.ArtEntity

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var artEntity: ArtEntity
    private val viewModel: DetailsViewModel by activityViewModels {
        DetailsViewModel.DetailsViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artEntity = arguments?.getSerializable("key") as? ArtEntity ?: return
    }

    override fun onResume() {
        super.onResume()
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.allFavoriteArts.observe(viewLifecycleOwner) { favoriteArts ->
            val isFavorite = favoriteArts.any { it.id == artEntity.id }
            binding.favoriteButton.isSelected = isFavorite
            artEntity.isFavorite = isFavorite
        }

        binding.favoriteButton.setOnClickListener {
            viewModel.favoriteOperations(artEntity)
        }

        binding.toolbar.setNavigationOnClickListener {
            FragmentManager.popBackStack(requireActivity() as MainActivity)
        }

        loadInfo(artEntity)
    }

    override fun onPause() {
        super.onPause()
        viewModel.allFavoriteArts.removeObservers(viewLifecycleOwner)
    }

    private fun loadInfo(result: ArtEntity) {
        ImageLoader.load(
            binding.imageView.context,
            result.webImage.url,
            binding.imageView,
            binding.progressBar
        )
        binding.longTitleDetails.text = result.longTitle
    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }

}