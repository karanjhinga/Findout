package com.karan.findout.domain.usecase

import com.karan.findout.data.model.Restaurant
import com.karan.findout.domain.Repository
import javax.inject.Inject

class AddToFavouriteUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(restaurant: Restaurant) {
        repository.addToFavourites(restaurant)
    }
}