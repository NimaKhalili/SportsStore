package com.example.sportsstore.feature.main

import android.util.Log
import com.example.sportsstore.common.SportsSingleObserver
import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.data.CartItemCount
import com.example.sportsstore.data.TokenContainer
import com.example.sportsstore.data.repo.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository) : SportsViewModel() {
    fun getCartItemsCount(){
        if (!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemsCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object :SportsSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                        Log.i("EventBUStest", "MainViewModel: " + t.count)
                    }
                })
        }
    }
}