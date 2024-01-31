package com.example.artapp.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.artapp.databinding.FragmentHomeBinding
import com.example.data.source.remote.RetrofitInstance
import com.example.data.repository.ArtRepositoryImpl
import com.example.domain.GetArtsUseCase
import com.example.domain.useCase.base.executeSafely
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter()
        init()
    }

    private fun init() {
        val getArtsUseCase = GetArtsUseCase(
            artRepository = ArtRepositoryImpl(
                artApi = RetrofitInstance.artApi
            )
        )

        binding.apply {
            rcView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            CoroutineScope(Dispatchers.IO).launch {
                getArtsUseCase.executeSafely(Unit).fold(
                    onSuccess = {
                        CoroutineScope(Dispatchers.Main).launch {
                            homeAdapter.submitList(it.artObjects)
                            rcView.adapter = homeAdapter
                            Log.d("CHECK", "good")
                        }
                    },
                    onFailure = {
                        Log.d("CHECK", "Bad", it)
                    }
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment
    }
}