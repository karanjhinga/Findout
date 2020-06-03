package com.karan.findout.domain.usecase

import com.karan.findout.domain.Repository
import javax.inject.Inject

class RemoveFromFavouriteUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(restaurantId: Int) {
        repository.removeFromFavourites(restaurantId)
    }
}
