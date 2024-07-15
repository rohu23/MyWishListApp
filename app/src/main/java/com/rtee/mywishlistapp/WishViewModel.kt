package com.rtee.mywishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtee.mywishlistapp.data.Wish
import com.rtee.mywishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(private val wishRepository: WishRepository): ViewModel (){

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChanged(newString: String) {
        wishTitleState = newString
    }

    fun onWishDescriptionChanged(newString: String) {
        wishDescriptionState = newString
    }

    fun updateWishTitle(title: String){
        wishTitleState = title
    }

    lateinit var getAllWishes: Flow<List<Wish>>

    init{
        viewModelScope.launch{
            getAllWishes = wishRepository.getWishes()
        }
    }

    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.addWish(wish)
        }
    }
    fun getWishById(id: Long): Flow<Wish> {
        return wishRepository.getWishById(id)
    }
    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.updateWish(wish)
        }
    }
    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.deleteWish(wish)
        }
    }
}