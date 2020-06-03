package com.karan.findout.di.modules

import androidx.lifecycle.ViewModel
import com.karan.findout.data.RepositoryImpl
import com.karan.findout.di.ViewModelKey
import com.karan.findout.di.scopes.ActivityScoped
import com.karan.findout.domain.Repository
import com.karan.findout.ui.details.RestaurantDetailFragment
import com.karan.findout.ui.favourites.FavouritesFragment
import com.karan.findout.ui.favourites.FavouritesViewModel
import com.karan.findout.ui.home.HomeFragment
import com.karan.findout.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @Binds
    @ActivityScoped
    abstract fun bindRepository(impl: RepositoryImpl): Repository

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeFavouritesFragment(): FavouritesFragment

    @ContributesAndroidInjector
    abstract fun contributeRestaurantDetailFragment(): RestaurantDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    abstract fun bindFavouriteViewModel(vm: FavouritesViewModel): ViewModel
}
