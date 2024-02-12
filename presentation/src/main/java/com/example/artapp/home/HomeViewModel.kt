package com.example.artapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ArtLocalRepositoryImpl
import com.example.data.repository.ArtRemoteRepositoryImpl
import com.example.data.source.local.AppDataBase
import com.example.data.source.remote.RetrofitInstance
import com.example.domain.useCase.GetRemoteArtsUseCase
import com.example.domain.models.ArtEntity
import com.example.domain.useCase.LocalArtsUseCase
import com.example.domain.useCase.base.executeSafely
import kotlinx.coroutines.flow.Flow
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
    private val allFavoriteArts: Flow<List<ArtEntity>> = localArtsUseCase.getAllArts()
    val liveState = MutableLiveData<States>(States.Loading)
    private var remoteArtsResult: Result<List<ArtEntity>>? = null

    init {
        observeAndLoadArts()
    }

    private fun observeAndLoadArts() {
        viewModelScope.launch {
            remoteArtsResult = getRemoteArtsUseCase.executeSafely(Unit)
            allFavoriteArts.collect { localArtList ->
                val result = remoteArtsResult!!.fold(
                    onSuccess = { remoteArts ->
                        val artsWithFavorites = remoteArts.map { remoteArt ->
                            val isFavorite = localArtList.any { it.id == remoteArt.id }
                            remoteArt.copy(isFavorite = isFavorite)
                        }
                        States.Data(artsWithFavorites)
                    },
                    onFailure = {
                        States.Error
                    }
                )
                liveState.value = result
            }
        }
    }

    fun refreshData() {
        liveState.value = States.Loading
        observeAndLoadArts()
    }

    fun toggleFavoriteStatus(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            remoteArtsResult?.getOrNull()?.find { it.id == id }?.let { remoteArt ->
                val updatedArt = remoteArt.copy(isFavorite = isFavorite)
                if (isFavorite) {
                    localArtsUseCase.addAsFavorite(updatedArt)
                } else {
                    localArtsUseCase.deleteFromFavorite(updatedArt)
                }
            }
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

