package com.example.artapp.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ArtLocalRepositoryImpl
import com.example.data.repository.ArtRemoteRepositoryImpl
import com.example.data.source.local.AppDataBase
import com.example.data.source.remote.RetrofitInstance
import com.example.domain.useCase.GetRemoteArtsUseCase
import com.example.domain.models.ArtEntity
import com.example.domain.useCase.LocalArtsUseCase
import com.example.domain.useCase.base.executeSafely
import kotlinx.coroutines.launch

sealed class States {
    data object Loading : States()
    data class Data(val arts: List<ArtEntity>) : States()
    data object Error : States()
}

class HomeViewModel(dataBase: AppDataBase) : ViewModel() {
    private val getRemoteArtsUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetRemoteArtsUseCase(artRemoteRepository = ArtRemoteRepositoryImpl(artApi = RetrofitInstance.artApi))
    }
    private val localArtsUseCase by lazy {
        LocalArtsUseCase(artLocalRepository = ArtLocalRepositoryImpl(dao = dataBase.getDao()))
    }
    val allFavoriteArts: LiveData<List<ArtEntity>> =
        localArtsUseCase.getAllArts().asLiveData()
    val liveState = MutableLiveData<States>(States.Loading)
    private var artsList: List<ArtEntity> = emptyList()

    init {
        loadArts()
    }

    fun toggleFavoriteStatus(id: String, isFavorite: Boolean) {
        artsList.map { art ->
            if (art.id == id) {
                val updatedArt = art.copy(isFavorite = isFavorite)
                viewModelScope.launch {
                    if (isFavorite) {
                        localArtsUseCase.addAsFavorite(updatedArt)
                    } else {
                        localArtsUseCase.deleteFromFavorite(updatedArt)
                    }
                }
                updatedArt
            } else {
                art
            }
        }
    }

    fun refreshData() {
        liveState.value = States.Loading
        loadArts()
    }

    fun loadArts() {
        viewModelScope.launch {
            val result = getRemoteArtsUseCase.executeSafely(Unit).fold(
                onSuccess = { remoteArts ->
                    val artsWithFavorites = remoteArts.map { remoteArt ->
                        val isFavorite =
                            allFavoriteArts.value?.any { localArt -> localArt.id == remoteArt.id } ?: false
                        remoteArt.copy(isFavorite = isFavorite)
                    }
                    artsList = artsWithFavorites
                    States.Data(artsWithFavorites)
                },
                onFailure = {
                    States.Error
                }
            )
            liveState.value = result
        }
    }

    class HomeViewModelFactory(private val dataBase: AppDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(dataBase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}