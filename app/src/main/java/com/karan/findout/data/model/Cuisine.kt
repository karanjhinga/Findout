package com.karan.findout.data.model

import com.google.gson.annotations.SerializedName

data class Cuisine(
    @SerializedName("cuisine_id") val cuisineId: Int,
    @SerializedName("cuisine_name") val cuisineName: String
)