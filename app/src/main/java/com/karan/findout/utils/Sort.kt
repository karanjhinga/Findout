package com.karan.findout.utils

import androidx.annotation.StringRes
import com.karan.findout.R

enum class Sort(val id: Int, @StringRes val text: Int, val params: Map<String, String>) {
    RATING(R.id.rating, R.string.rating_high_to_low, mapOf(ApiConstants.SORT to ApiConstants.RATING, ApiConstants.ORDER to ApiConstants.DESCENDING)),
    COST_LOW_TO_HIGH(R.id.cost_low_to_high, R.string.cost_low_to_high, mapOf(ApiConstants.SORT to ApiConstants.COST, ApiConstants.ORDER to ApiConstants.ASCENDING)),
    COST_HIGH_TO_LOW(R.id.cost_high_to_low, R.string.cost_high_to_low, mapOf(ApiConstants.SORT to ApiConstants.COST, ApiConstants.ORDER to ApiConstants.DESCENDING)),
    NONE(-1, R.string.sort, emptyMap())
}