package com.example.sportsstore.data.repo

import com.example.sportsstore.data.Banner
import com.example.sportsstore.data.repo.source.BannerDataSource
import com.example.sportsstore.data.repo.source.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImpl(
    val bannerRemoteDataSource: BannerDataSource): BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}