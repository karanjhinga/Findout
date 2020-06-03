package com.karan.findout.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.karan.findout.data.model.Restaurant

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant")
    fun getFavourites(): LiveData<List<Restaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavourites(restaurant: Restaurant)

    @Query("DELETE FROM restaurant WHERE id = :restaurantId")
    fun removeFromFavourites(restaurantId: Int)

    @Query("SELECT id from restaurant WHERE id = :restaurantId LIMIT 1")
    fun isFavourite(restaurantId: Int): Int?
}