package com.example.artapp.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArtEntity
import com.example.domain.useCase.LocalArtsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val localArtsUseCase: LocalArtsUseCase
) : ViewModel() {
    val allFavoriteArts: LiveData<List<ArtEntity>> = localArtsUseCase.getAllArts().asLiveData()

    fun toggleFavoriteStatus(id: String) {
        allFavoriteArts.value?.find { it.id == id }.let {
            viewModelScope.launch {
                if (it != null) {
                    localArtsUseCase.deleteFromFavorite(it.id)
                }
            }
        }
    }

    class FavouriteViewModelFactory(
        private val localArtsUseCase: LocalArtsUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavouriteViewModel(localArtsUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}