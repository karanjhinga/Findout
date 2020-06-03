package com.karan.findout.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results_found") val total: Int,
    @SerializedName("results_start") val start: Int,
    @SerializedName("restaurants") val restaurants: List<RestaurantX>
)