package com.karan.findout.domain

import androidx.lifecycle.LiveData
import com.karan.findout.data.model.Restaurant
import com.karan.findout.data.model.SearchResponse
import com.karan.findout.utils.Result

interface Repository {
    val favourites: LiveData<List<Restaurant>>
    suspend fun searchRestaurants(params: Map<String, String>): Result<SearchResponse>
    suspend fun getCuisineId(cuisine: String): Int?
    suspend fun addToFavourites(restaurant: Restaurant)
    suspend fun removeFromFavourites(restaurantId: Int)
    suspend fun isFavourite(restaurantId: Int): Boolean
}