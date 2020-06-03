package com.karan.findout.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(
    @SerializedName("aggregate_rating") val rating: String,
    @SerializedName("rating_text") val ratingText: String,
    @SerializedName("rating_color") val ratingColor: String
) : Parcelable