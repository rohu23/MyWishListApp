package com.rtee.mywishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun addWish(wishEntity: Wish)

    @Delete
    abstract fun deleteWish(wishEntity: Wish)

    @Update
    abstract fun updateWish(wishEntity: Wish)

    //Loads all wishes from wish table
    @Query("Select * from `wish-table`")
    abstract fun getAllWishes(): Flow<List<Wish>>

    @Query("Select * from `wish-table` where id = :id")
    abstract fun getWishById(id: Long): Flow<Wish>


}