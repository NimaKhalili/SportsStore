package com.example.sportsstore.data.repo.source

import com.example.sportsstore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

//inja jayi ast ke data az koja miad
interface ProductDataSource {

    //server database
    fun getProducts(sort:Int): Single<List<Product>>

    //local database
    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorites(): Completable

    fun deleteFromFavorites(): Completable
}