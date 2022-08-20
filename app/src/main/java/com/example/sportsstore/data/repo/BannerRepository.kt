package com.example.sportsstore.data.repo

import com.example.sportsstore.data.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanners(): Single<List<Banner>>
}