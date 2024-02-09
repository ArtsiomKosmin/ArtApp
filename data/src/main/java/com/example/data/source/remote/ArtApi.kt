package com.example.data.source.remote

import com.example.data.models.ArtsResponse
import com.example.data.util.Constants.API_KEY
import com.example.data.util.Constants.INVOLVED_MARKER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtApi {
    @GET("collection")
    suspend fun getAllArts(
        @Query("key") apiKey: String = API_KEY,
        @Query("involvedMaker") involvedMaker: String = INVOLVED_MARKER
    ): Response<ArtsResponse>
}