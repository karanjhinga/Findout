package com.karan.findout.data

import androidx.lifecycle.LiveData
import com.karan.findout.data.db.AppDatabase
import com.karan.findout.data.model.Cuisine
import com.karan.findout.data.model.Restaurant
import com.karan.findout.data.model.SearchResponse
import com.karan.findout.data.network.Api
import com.karan.findout.domain.Repository
import com.karan.findout.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val db: AppDatabase
) : SafeApiRequest(), Repository {

    override val favourites: LiveData<List<Restaurant>>
        get() = db.restaurantDao().getFavourites()

    private var cuisines: List<Cuisine>? = null

    override suspend fun searchRestaurants(params: Map<String, String>): Result<SearchResponse> {
        return try {
            val response = api.searchRestaurants(params)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCuisineId(cuisine: String): Int? {
        if (cuisines.isNullOrEmpty()) {
            try {
                val params = mutableMapOf(
                    ApiConstants.LATITUDE to LAT,
                    ApiConstants.LONGITUDE to LNG
                )
                cuisines = api.getCuisines(params).cuisines.map { it.cuisine }
            } catch (e: Exception) {
                return null
            }
        }
        return cuisines?.find { it.cuisineName == cuisine }?.cuisineId
    }

    override suspend fun addToFavourites(restaurant: Restaurant) = withContext(Dispatchers.IO) {
        db.restaurantDao().addToFavourites(restaurant)
    }

    override suspend fun removeFromFavourites(restaurantId: Int) = withContext(Dispatchers.IO) {
        db.restaurantDao().removeFromFavourites(restaurantId)
    }

    override suspend fun isFavourite(restaurantId: Int): Boolean = withContext(Dispatchers.IO) {
        db.restaurantDao().isFavourite(restaurantId) != null
    }
}
