package com.karan.findout.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumb_url") val thumbnailUrl: String
) : Parcelable
