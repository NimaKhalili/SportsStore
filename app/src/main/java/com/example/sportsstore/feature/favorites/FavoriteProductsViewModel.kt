package com.example.sportsstore.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.example.sportsstore.common.SportsCompletableObserver
import com.example.sportsstore.common.SportsSingleObserver
import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.data.Product
import com.example.sportsstore.data.repo.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteProductsViewModel(private val productsRepository: ProductRepository) :
    SportsViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()

    init {
        productsRepository.getFavoriteProducts()
            .subscribeOn(Schedulers.io())
            .subscribe(object : SportsSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.postValue(t)
                }
            })
    }

    fun removeFromFavorites(product: Product) {
        productsRepository.deleteFromFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : SportsCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("removedFromFavorites completed")
                }

            })
    }
}