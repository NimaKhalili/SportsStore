package com.example.sportsstore.services.http

import com.example.sportsstore.data.Banner
import com.example.sportsstore.data.Product
import com.example.sportsstore.data.Comment
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("product/list")
    fun getProducts(@Query("sort") sort:String):Single<List<Product>>

    @GET("banner/slider")
    fun getBanners():Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id") productId: Int): Single<List<Comment>>
}

fun createApiServiceInstance ():ApiService{
    val retrofit=Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}