package com.example.sportsstore.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.sportsstore.common.EXTRA_KEY_DATA
import com.example.sportsstore.common.SportsSingleObserver
import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.common.asyncNetworkRequest
import com.example.sportsstore.data.Comment
import com.example.sportsstore.data.Product
import com.example.sportsstore.data.repo.CartRepository
import com.example.sportsstore.data.repo.CommentRepository
import io.reactivex.Completable

class ProductDetailViewModel(bundle: Bundle, commentRepository: CommentRepository, val cartRepository: CartRepository) :
    SportsViewModel() {
    val productLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comment>>()

    init {

        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : SportsSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value = t
                }

            })
    }

    fun addToCartBtn():Completable = cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()
}