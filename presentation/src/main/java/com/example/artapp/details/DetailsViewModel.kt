package com.example.artapp.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel: ViewModel() {
    val isFavorite = MutableLiveData<Boolean>()

    fun checkFavoriteStatus(photoId: Long) {
//        isFavorite.value =
    }
}