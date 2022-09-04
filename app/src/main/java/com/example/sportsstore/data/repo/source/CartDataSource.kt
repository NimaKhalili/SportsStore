package com.example.sportsstore.data.repo.source

import com.example.sportsstore.data.AddToCartResponse
import com.example.sportsstore.data.CartItemCount
import com.example.sportsstore.data.CartResponse
import com.example.sportsstore.data.MessageResponse
import io.reactivex.Single

interface CartDataSource {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItemId: Int): Single<MessageResponse>
    fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>
    fun getCartItemsCount(): Single<CartItemCount>
}