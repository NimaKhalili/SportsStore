package com.example.sportsstore.data.repo.source

import com.example.sportsstore.data.Banner
import com.example.sportsstore.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService): BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}