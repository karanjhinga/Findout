package com.karan.findout.data.model

import com.google.gson.annotations.SerializedName

data class CuisineResponse(
    @SerializedName("cuisines") val cuisines: List<CuisineX>
)
