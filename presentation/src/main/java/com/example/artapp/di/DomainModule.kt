package com.example.artapp.di

import com.example.data.repository.ArtLocalRepositoryImpl
import com.example.data.repository.ArtRemoteRepositoryImpl
import com.example.data.repository.SharedPrefsImpl
import com.example.domain.repository.SharedPrefs
import com.example.domain.useCase.GetRemoteArtsUseCase
import com.example.domain.useCase.GetSharedPrefsUseCase
import com.example.domain.useCase.LocalArtsUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetRemoteArtsUseCase(
        artRemoteRepositoryImpl: ArtRemoteRepositoryImpl
    ): GetRemoteArtsUseCase {
        return GetRemoteArtsUseCase(artRemoteRepository = artRemoteRepositoryImpl)
    }

    @Provides
    fun provideLocalArtsUseCase(
        artLocalRepositoryImpl: ArtLocalRepositoryImpl
    ): LocalArtsUseCase {
        return LocalArtsUseCase(artLocalRepository = artLocalRepositoryImpl)
    }

    @Provides
    fun provideGetSharedPrefsUseCase(
        sharedPrefsImpl: SharedPrefsImpl
    ): GetSharedPrefsUseCase {
        return GetSharedPrefsUseCase(sharedPrefs = sharedPrefsImpl)
    }

}