package com.example.artapp.di

import android.content.Context
import com.example.artapp.details.DetailsViewModel
import com.example.artapp.favorite.FavouriteViewModel
import com.example.artapp.home.HomeViewModel
import com.example.domain.useCase.GetRemoteArtsUseCase
import com.example.domain.useCase.LocalArtsUseCase
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideHomeViewModelFactory(
        getRemoteArtsUseCase: GetRemoteArtsUseCase,
        localArtsUseCase: LocalArtsUseCase
    ): HomeViewModel.HomeViewModelFactory {
        return HomeViewModel.HomeViewModelFactory(
            getRemoteArtsUseCase = getRemoteArtsUseCase,
            localArtsUseCase = localArtsUseCase
        )
    }

    @Provides
    fun provideFavoriteViewModelFactory(
        localArtsUseCase: LocalArtsUseCase
    ): FavouriteViewModel.FavouriteViewModelFactory {
        return FavouriteViewModel.FavouriteViewModelFactory(
            localArtsUseCase = localArtsUseCase
        )
    }

    @Provides
    fun provideDetailsViewModelFactory(
        localArtsUseCase: LocalArtsUseCase
    ): DetailsViewModel.DetailsViewModelFactory {
        return DetailsViewModel.DetailsViewModelFactory(
            localArtsUseCase = localArtsUseCase
        )
    }
}