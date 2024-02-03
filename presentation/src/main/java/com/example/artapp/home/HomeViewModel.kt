package com.example.artapp.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ArtRepositoryImpl
import com.example.data.source.remote.RetrofitInstance
import com.example.domain.useCase.GetArtsUseCase
import com.example.domain.models.ArtEntity
import com.example.domain.useCase.base.executeSafely
import kotlinx.coroutines.launch

sealed class States {
    data object Loading : States()
    data class Data(val arts: List<ArtEntity>) : States()
    data object Error : States()
}


class HomeViewModel : ViewModel() {
    private val getArtsUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetArtsUseCase(artRepository = ArtRepositoryImpl(artApi = RetrofitInstance.artApi))
    }

    val liveState = MutableLiveData<States>(States.Loading)

    init {
        loadArts()
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun refreshData() {
        loadArts()
    }

    private fun loadArts() {
        viewModelScope.launch {
            liveState.value = States.Loading

            val result = getArtsUseCase.executeSafely(Unit).fold(
                onSuccess = States::Data,
                onFailure = {
                    States.Error
                }
            )
            liveState.value = result
        }
    }
}