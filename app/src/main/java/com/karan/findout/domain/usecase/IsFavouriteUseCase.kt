package com.karan.findout.domain.usecase

import com.karan.findout.domain.Repository
import javax.inject.Inject

class IsFavouriteUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(restaurantId: Int): Boolean {
        return repository.isFavourite(restaurantId)
    }
}