package com.example.sportsstore.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.sportsstore.R
import com.example.sportsstore.common.SportsSingleObserver
import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.common.asyncNetworkRequest
import com.example.sportsstore.data.Product
import com.example.sportsstore.data.repo.ProductRepository

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    SportsViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )

    init {
        getProducts()
        selectedSortTitleLiveData.value = sortTitles[sort]
    }

    fun getProducts() {
        progressBarLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false   }
            .subscribe(object :SportsSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }

            })
    }

    fun onSelectedSortChangedByUser(sort:Int){
        this.sort = sort
        this.selectedSortTitleLiveData.value = sortTitles[sort]
        getProducts()
    }
}