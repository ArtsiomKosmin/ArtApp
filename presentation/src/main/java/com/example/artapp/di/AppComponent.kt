package com.example.artapp.di

import com.example.artapp.details.DetailsFragment
import com.example.artapp.favorite.FavouriteFragment
import com.example.artapp.home.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun injectHome(homeFragment: HomeFragment)
    fun injectFavorite(favouriteFragment: FavouriteFragment)
    fun injectDetails(detailsFragment: DetailsFragment)

}