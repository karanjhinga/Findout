package com.karan.findout.ui.favourites

import androidx.lifecycle.ViewModel
import com.karan.findout.domain.usecase.GetFavouriteUseCase
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    getFavouriteUseCase: GetFavouriteUseCase
) : ViewModel() {

    val favourites = getFavouriteUseCase()
}