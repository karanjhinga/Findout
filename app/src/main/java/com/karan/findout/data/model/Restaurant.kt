package com.karan.findout.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.karan.findout.domain.entity.ItemModel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "restaurant")
data class Restaurant(
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("url") val zomatoLink: String,
    @Embedded(prefix = "loc_")
    @SerializedName("location") val location: Location,
    @SerializedName("cuisines") val cuisines: String,
    @SerializedName("timings") val timings: String,
    @SerializedName("average_cost_for_two") val costForTwo: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("highlights") val highlights: List<String>?,
    @SerializedName("thumb") val thumbnailUrl: String,
    @Embedded(prefix = "rating_")
    @SerializedName("user_rating") val userRating: Rating,
    @SerializedName("photos") val photos: List<PhotoX>?,
    @SerializedName("has_online_delivery") val hasOnlineDelivery: Int,
    @SerializedName("phone_numbers") val phoneNumbers: String,
    @SerializedName("deeplink") val deepLink: String,
    @SerializedName("establishment") val establishments: List<String>?
) : Parcelable, ItemModel {

    val cuisineList
        get() = cuisines.split(", ")

    val establishmentsArray
        get() = establishments?.toTypedArray()
}
