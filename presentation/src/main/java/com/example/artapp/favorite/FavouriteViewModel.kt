package com.example.artapp.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ArtLocalRepositoryImpl
import com.example.data.source.local.AppDataBase
import com.example.domain.models.ArtEntity
import com.example.domain.useCase.LocalArtsUseCase
import com.example.domain.useCase.base.executeSafely
import kotlinx.coroutines.launch


sealed class FavoriteStates {
    data class Data(val arts: List<ArtEntity>) : FavoriteStates()
    data object Error : FavoriteStates()
    data object Update : FavoriteStates()
}

class FavouriteViewModel(
    dataBase: AppDataBase
) : ViewModel() {
    private var localArtsList: List<ArtEntity> = emptyList()
    val favoriteLiveState = MutableLiveData<FavoriteStates>()
    private val localArtsUseCase by lazy {
        LocalArtsUseCase(artLocalRepository = ArtLocalRepositoryImpl(dao = dataBase.getDao()))
    }

    init {
        loadLocalList()
    }

    fun loadLocalList() {
        viewModelScope.launch {
            val result = localArtsUseCase.executeSafely(Unit).fold(
                onSuccess = {
                    localArtsList = it
                    FavoriteStates.Data(it)
                },
                onFailure = {
                    FavoriteStates.Error
                }
            )
            favoriteLiveState.value = result
        }
    }

    fun toggleFavoriteStatus(id: String) {
        localArtsList.find { it.id == id }?.let { deletedArt ->
            localArtsList = localArtsList.filterNot { it.id == id }
            viewModelScope.launch {
                localArtsUseCase.deleteFromFavorite(deletedArt)
                favoriteLiveState.value = FavoriteStates.Data(localArtsList)
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