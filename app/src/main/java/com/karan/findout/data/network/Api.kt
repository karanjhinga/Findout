package com.karan.findout.data.network

import com.karan.findout.data.model.CuisineResponse
import com.karan.findout.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {

    @GET("search")
    suspend fun searchRestaurants(@QueryMap queries: Map<String, String>): SearchResponse

    @GET("cuisines")
    suspend fun getCuisines(@QueryMap queries: Map<String, String>): CuisineResponse
}