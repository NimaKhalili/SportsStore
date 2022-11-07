package com.example.sportsstore.data.repo.source

import com.example.sportsstore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    //server database
    fun getProducts(sort:Int): Single<List<Product>>

    //local database
    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorites(product: Product): Completable

    fun deleteFromFavorites(product: Product): Completable
}