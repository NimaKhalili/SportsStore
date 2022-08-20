package com.example.sportsstore.data.repo.source

import com.example.sportsstore.data.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners(): Single<List<Banner>>
}