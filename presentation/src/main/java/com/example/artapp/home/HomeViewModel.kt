package com.example.artapp.home

import androidx.lifecycle.ViewModel
import com.example.data.repository.ArtRepositoryImpl
import com.example.data.source.remote.RetrofitInstance
import com.example.domain.GetArtsUseCase
import com.example.domain.entity.ArtsResponse

data class UIState(
    val state: States
) {
    companion object {
        val INITIAL = UIState(
            state = States.Loading
        )
    }

    sealed class States {
        data object Loading : States()
        data class Data(val arts: ArtsResponse) : States()
        data object Error : States()
    }
}
class HomeViewModel : ViewModel() {
    val getArtsUseCase = GetArtsUseCase(
        artRepository = ArtRepositoryImpl(
            artApi = RetrofitInstance.artApi
        )
    )
}