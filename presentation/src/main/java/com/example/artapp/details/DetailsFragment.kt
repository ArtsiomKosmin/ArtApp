package com.example.artapp.details

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.artapp.ImageLoader
import com.example.artapp.databinding.FragmentDetailsBinding
import com.example.domain.models.ArtEntity

class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = arguments?.getSerializable("key", ArtEntity::class.java)
        if (result != null) loadInfo(result)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun loadInfo(result: ArtEntity) {
        ImageLoader.load(binding.imageView.context, result.webImage.url, binding.imageView, binding.progressBar)
        binding.longTitleDetails.text = result.longTitle
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }
}