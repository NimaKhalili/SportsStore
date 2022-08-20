package com.example.sportsstore

import android.app.Application
import com.example.sportsstore.data.repo.BannerRepository
import com.example.sportsstore.data.repo.BannerRepositoryImpl
import com.example.sportsstore.data.repo.ProductRepository
import com.example.sportsstore.data.repo.ProductRepositoryImpl
import com.example.sportsstore.data.repo.source.BannerDataSource
import com.example.sportsstore.data.repo.source.BannerRemoteDataSource
import com.example.sportsstore.data.repo.source.ProductLocalDataSource
import com.example.sportsstore.data.repo.source.ProductRemoteDataSource
import com.example.sportsstore.feature.main.MainViewModel
import com.example.sportsstore.feature.main.PopularProductListAdapter
import com.example.sportsstore.feature.main.ProductListAdapter
import com.example.sportsstore.services.FrescoImageLoadingService
import com.example.sportsstore.services.ImageLoadingService
import com.example.sportsstore.services.http.ApiService
import com.example.sportsstore.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)
        val myModules= module {
            single<ApiService> { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), ProductLocalDataSource()) }
            factory { ProductListAdapter(get()) }
            factory { PopularProductListAdapter(get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            viewModel { MainViewModel(get(), get()) }
        }

        startKoin{
            androidContext(this@App)
            modules(myModules)
        }
    }
}