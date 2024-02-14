package com.example.artapp.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ArtLocalRepositoryImpl
import com.example.data.source.local.AppDataBase
import com.example.domain.models.ArtEntity
import com.example.domain.useCase.LocalArtsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(dataBase: AppDataBase) : ViewModel() {
    private val localArtsUseCase by lazy {
        LocalArtsUseCase(artLocalRepository = ArtLocalRepositoryImpl(dao = dataBase.getDao()))
    }
    val allFavoriteArts = localArtsUseCase.getAllArts().asLiveData()

    fun favoriteOperations(artEntity: ArtEntity) {
        viewModelScope.launch {
            if (artEntity.isFavorite) {
                localArtsUseCase.deleteFromFavorite(artEntity)
            } else {
                localArtsUseCase.addAsFavorite(artEntity)
            }
        }
    }

    class DetailsViewModelFactory(private val dataBase: AppDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(dataBase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
