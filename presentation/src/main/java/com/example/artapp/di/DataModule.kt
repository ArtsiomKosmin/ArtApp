package com.example.artapp.di

import android.content.Context
import com.example.data.repository.ArtRemoteRepositoryImpl
import com.example.data.source.local.AppDataBase
import com.example.data.source.local.dao.ArtDao
import com.example.data.source.remote.ArtApi
import com.example.data.source.remote.RetrofitInstance
import com.example.domain.repository.ArtRemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideAppDataBase(context: Context): AppDataBase {
        return AppDataBase.getDataBase(context)
    }

    @Provides
    @Singleton
    fun provideArtDao(appDataBase: AppDataBase): ArtDao {
        return appDataBase.getDao()
    }

    @Provides
    @Singleton
    fun provideArtRemoteRepository(artApi: ArtApi): ArtRemoteRepository {
        return ArtRemoteRepositoryImpl(artApi)
    }

    @Provides
    @Singleton
    fun provideArtApi(): ArtApi {
        return RetrofitInstance.artApi
    }

}