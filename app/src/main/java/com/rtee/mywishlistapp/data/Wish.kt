package com.rtee.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "wish_title")
    val title: String = "",
    @ColumnInfo(name = "wish_description")
    val description: String = ""
)

object DummyWish{
    val wishList = listOf(
        Wish(title = "Google Watch 2", description = "Awesome watch"),
        Wish(title = "Apple Watch", description = "Awesome watch"),
        Wish(title = "Samsung Watch", description = "Awesome watch"))
}