package com.example.artapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.GetRemoteArtsUseCase
import com.example.domain.models.ArtEntity
import com.example.domain.useCase.LocalArtsUseCase
import com.example.domain.useCase.base.executeSafely
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class States {
    data object Loading : States()
    data class Data(val arts: List<ArtEntity>) : States()
    data object Error : States()
}

class HomeViewModel @Inject constructor(
    private val getRemoteArtsUseCase: GetRemoteArtsUseCase,
    private val localArtsUseCase: LocalArtsUseCase
) : ViewModel() {
    private val allFavoriteArts: Flow<List<ArtEntity>> = localArtsUseCase.getAllArts()
    private val remoteArts = MutableLiveData<List<ArtEntity>>()

    val liveState = MutableLiveData<States>(States.Loading)
    private var currentPage = 1

    init {
        loadRemoteArts()
    }

    fun loadRemoteArts() {
        viewModelScope.launch {
            getRemoteArtsUseCase.executeSafely(currentPage).fold(
                onSuccess = { remoteArtsList ->
                    if (currentPage == 1) {
                        remoteArts.value = remoteArtsList
                    } else {
                        remoteArts.value = remoteArts.value?.plus(remoteArtsList)
                    }
                    checkFavoriteStatus()
                    currentPage++
                },
                onFailure = {
                    liveState.value = States.Error
                }
            )
        }
    }

    private fun checkFavoriteStatus() {
        viewModelScope.launch {
            allFavoriteArts.collect { localArtList ->
                val result = remoteArts.value?.map { remoteArt ->
                    val isFavorite = localArtList.any { it.id == remoteArt.id }
                    remoteArt.copy(isFavorite = isFavorite)
                }
                liveState.value = result?.let { States.Data(it) }
            }
        }
    }

    fun refreshData() {
        currentPage = 1
        liveState.value = States.Loading
        loadRemoteArts()
    }

    fun toggleFavoriteStatus(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            remoteArts.value?.find { it.id == id }?.let { remoteArt ->
                val updatedArt = remoteArt.copy(isFavorite = isFavorite)
                if (isFavorite) {
                    localArtsUseCase.addAsFavorite(updatedArt)
                } else {
                    localArtsUseCase.deleteFromFavorite(updatedArt.id)
                }
            }
        }
    }

    class HomeViewModelFactory(
        private val getRemoteArtsUseCase: GetRemoteArtsUseCase,
        private val localArtsUseCase: LocalArtsUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(
                    getRemoteArtsUseCase = getRemoteArtsUseCase,
                    localArtsUseCase = localArtsUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

