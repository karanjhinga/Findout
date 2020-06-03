package com.karan.findout.domain.usecase

import com.karan.findout.data.model.Header
import com.karan.findout.data.model.Restaurant
import com.karan.findout.data.model.SearchResponse
import com.karan.findout.domain.Repository
import com.karan.findout.domain.entity.ItemModel
import com.karan.findout.utils.ApiConstants
import com.karan.findout.utils.PAGE_SIZE
import com.karan.findout.utils.Result
import javax.inject.Inject

class SearchRestaurantUseCase @Inject constructor(
    private val repository: Repository
) {

    // current list
    private var currentList = mutableListOf<Restaurant>()

    suspend operator fun invoke(
        params: MutableMap<String, String>,
        page: Int,
        cuisine: String?
    ): Result<Pair<Boolean, List<ItemModel>>> {

        var isLoadMore = false
        if (!cuisine.isNullOrEmpty()) {
            if (page != 1) {
                isLoadMore = true
            }

            // adding start offset to query
            params[ApiConstants.START] = (page - 1).times(PAGE_SIZE).toString()

            // adding cuisine id for selected cuisine to query
            val cuisineId = repository.getCuisineId(cuisine)
            if (cuisineId != null) {
                params[ApiConstants.CUISINE] = cuisineId.toString()
            }
        }

        return when (val response = repository.searchRestaurants(params)) {
            is Result.Error -> response
            is Result.Success -> Result.Success(response.data.toListItems(cuisine, isLoadMore))
        }
    }

    private fun SearchResponse.toListItems(
        cuisineFilter: String?,
        isLoadMore: Boolean
    ): Pair<Boolean, List<ItemModel>> {

        // set to maintain all cuisine types since a restaurant can exist in multiple cuisine group
        val set = linkedSetOf<String>()

        // clear because not requesting new page
        if (!isLoadMore) currentList.clear()

        // finding all possible cuisines
        restaurants.forEach {
            currentList.add(it.restaurant)
            if (cuisineFilter == null) {
                set.addAll(it.restaurant.cuisineList)
            } else {
                set.add(cuisineFilter)
            }
        }

        // building list to display
        val listItems = mutableListOf<ItemModel>()
        set.forEach { cuisine ->
            // add cuisine header
            listItems.add(Header(cuisine))

            // add restaurants belonging to this cuisine
            if (cuisineFilter == null) {
                currentList.filter { restaurant ->
                    restaurant.cuisineList.contains(cuisine)
                }.map {
                    listItems.add(it)
                }
            } else {
                listItems.addAll(currentList)
            }
        }

        if (set.isNullOrEmpty()){
            listItems.addAll(currentList)
        }

        // checking if we can load more results and we have a cuisine selected for pagination purposes
        val hasMoreResults = ((total - start) > PAGE_SIZE && cuisineFilter != null)
        return Pair(hasMoreResults, listItems)
    }
}
