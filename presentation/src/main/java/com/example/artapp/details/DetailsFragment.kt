package com.example.artapp.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.artapp.ImageLoader
import com.example.artapp.activities.MainApp
import com.example.artapp.databinding.FragmentDetailsBinding
import com.example.domain.models.ArtEntity

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val navArgs: DetailsFragmentArgs by navArgs()
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
    }

    override fun onResume() {
        super.onResume()
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.allFavoriteArts.observe(viewLifecycleOwner) { favoriteArts ->
            val isFavorite = favoriteArts.any { it.id == navArgs.ArtEntity.id }
            binding.favoriteButton.isSelected = isFavorite
            navArgs.ArtEntity.isFavorite = isFavorite
        }

        binding.favoriteButton.setOnClickListener {
            viewModel.favoriteOperations(navArgs.ArtEntity)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        loadInfo(navArgs.ArtEntity)
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