package com.example.artapp.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.artapp.ImageLoader
import com.example.artapp.activities.MainApp
import com.example.artapp.databinding.FragmentDetailsBinding
import com.example.domain.models.ArtEntity
import javax.inject.Inject

class DetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: DetailsViewModel.DetailsViewModelFactory
    private lateinit var binding: FragmentDetailsBinding
    private val navArgs: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModels { viewModelFactory }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            (requireActivity().application as MainApp).appComponent.injectDetails(this)
            binding = FragmentDetailsBinding.inflate(inflater, container, false)
            return binding.root
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
                binding.progressBar,
                result.isFavorite
            )
            binding.longTitleDetails.text = result.longTitle
        }
    }