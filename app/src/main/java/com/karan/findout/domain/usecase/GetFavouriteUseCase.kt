package com.karan.findout.domain.usecase

import androidx.lifecycle.LiveData
import com.karan.findout.data.model.Restaurant
import com.karan.findout.domain.Repository
import javax.inject.Inject

class GetFavouriteUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): LiveData<List<Restaurant>> {
        return repository.favourites
    }
}