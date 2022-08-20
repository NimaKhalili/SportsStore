package com.example.sportsstore.data.repo

import com.example.sportsstore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

//makhzane data
//in makhzan baraye memarie mvvm ast ke in repository viewmodel azash etelaat mikhad va aslan ham kari nadare ke az koja miad faghat darkhast mide va repository behesh mide
interface ProductRepository {

    //server database
    fun getProducts(sort:Int):Single<List<Product>>

    //local database
    fun getFavoriteProducts():Single<List<Product>>

    fun addToFavorites():Completable

    fun deleteFromFavorites():Completable
}