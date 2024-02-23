package com.example.data.source.remote

import com.example.data.source.remote.models.ArtsResponse
import com.example.data.util.Constants.INVOLVED_MARKER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtApi {
    @GET("collection")
    suspend fun getAllArts(
        @Query("involvedMaker") involvedMaker: String = INVOLVED_MARKER,
        @Query("p") page: Int,
        @Query("imgonly")imageOnly: String = "True"
    ): Response<ArtsResponse>
}