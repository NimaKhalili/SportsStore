package com.example.sportsstore

import android.app.Application
import com.example.sportsstore.data.repo.ProductRepository
import com.example.sportsstore.data.repo.ProductRepositoryImpl
import com.example.sportsstore.data.repo.source.ProductLocalDataSource
import com.example.sportsstore.data.repo.source.ProductRemoteDataSource
import com.example.sportsstore.feature.main.MainViewModel
import com.example.sportsstore.services.http.ApiService
import com.example.sportsstore.services.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        val myModules= module {
            single<ApiService> { createApiServiceInstance() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), ProductLocalDataSource()) }
            viewModel { MainViewModel(get()) }
        }

        startKoin{
            androidContext(this@App)
            modules(myModules)
        }
    }
}