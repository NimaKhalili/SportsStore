package com.example.sportsstore

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.sportsstore.data.db.AppDatabase
import com.example.sportsstore.data.repo.*
import com.example.sportsstore.data.repo.order.OrderRemoteDataSource
import com.example.sportsstore.data.repo.order.OrderRepository
import com.example.sportsstore.data.repo.order.OrderRepositoryImpl
import com.example.sportsstore.data.repo.source.*
import com.example.sportsstore.feature.auth.AuthViewModel
import com.example.sportsstore.feature.cart.CartViewModel
import com.example.sportsstore.feature.checkout.CheckoutViewModel
import com.example.sportsstore.feature.common.ProductListAdapter
import com.example.sportsstore.feature.favorites.FavoriteProductsViewModel
import com.example.sportsstore.feature.home.HomeViewModel
import com.example.sportsstore.feature.list.ProductListViewModel
import com.example.sportsstore.feature.main.MainViewModel
import com.example.sportsstore.feature.main.PopularProductListAdapter
import com.example.sportsstore.feature.product.ProductDetailViewModel
import com.example.sportsstore.feature.product.comment.CommentListViewModel
import com.example.sportsstore.feature.profile.ProfileViewModel
import com.example.sportsstore.feature.shipping.ShippingViewModel
import com.example.sportsstore.services.FrescoImageLoadingService
import com.example.sportsstore.services.ImageLoadingService
import com.example.sportsstore.services.http.ApiService
import com.example.sportsstore.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
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
            single { Room.databaseBuilder(this@App, AppDatabase::class.java, "db_app").build() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), get<AppDatabase>().productDap()) }
            single<SharedPreferences> {
                this@App.getSharedPreferences(
                    "app_settings",
                    MODE_PRIVATE
                )
            }
            single { UserLocalDataSource(get()) }
            single<UserRepository> {
                UserRepositoryImpl(
                    UserLocalDataSource(get()),
                    UserRemoteDataSource(get())
                )
            }
            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }

            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory { PopularProductListAdapter(get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId: Int) -> CheckoutViewModel(orderId, get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteProductsViewModel(get()) }
        }

        startKoin{
            androidContext(this@App)
            modules(myModules)
        }

        val userRepository:UserRepository = get()
        userRepository.loadToken()
    }
}