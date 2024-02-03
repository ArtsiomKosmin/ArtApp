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

data class UIState(val state: States) {

    sealed class States {
        data object Loading : States()
        data class Data(val arts: List<ArtEntity>) : States()
        data object Error : States()
    }
}

class HomeViewModel : ViewModel() {
    private val getArtsUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetArtsUseCase(artRepository = ArtRepositoryImpl(artApi = RetrofitInstance.artApi))
    }

    val liveState = MutableLiveData<UIState.States>(UIState.States.Loading)

    init {
        loadArts()
        Log.d("Check", "vm started")
    }

    override fun onCleared() {
        Log.d("Check", "vm destroyed")
        super.onCleared()
    }

    fun refreshData() {
        loadArts()
    }

    private fun loadArts() {
        viewModelScope.launch {
            liveState.value = UIState.States.Loading

            val result = getArtsUseCase.executeSafely(Unit).fold(
                onSuccess = UIState.States::Data,
                onFailure = {UIState.States.Error
                }
            )
            liveState.value = result
        }
    }
}