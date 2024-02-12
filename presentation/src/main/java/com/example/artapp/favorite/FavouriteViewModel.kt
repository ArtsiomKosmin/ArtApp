package com.example.artapp.favorite

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
import com.example.domain.useCase.base.executeSafely
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(
    dataBase: AppDataBase
) : ViewModel() {
    private val localArtsUseCase by lazy {
        LocalArtsUseCase(artLocalRepository = ArtLocalRepositoryImpl(dao = dataBase.getDao()))
    }
    val allFavoriteArts: LiveData<List<ArtEntity>> = localArtsUseCase.getAllArts().asLiveData()


    fun toggleFavoriteStatus(id: String) {
        allFavoriteArts.value?.find { it.id == id }.let {
            viewModelScope.launch {
                if (it != null) {
                    localArtsUseCase.deleteFromFavorite(it)
                }
            }
        }
    }

    class FavouriteViewModelFactory(private val dataBase: AppDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavouriteViewModel(dataBase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}