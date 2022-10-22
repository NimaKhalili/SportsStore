package com.example.sportsstore.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.example.sportsstore.common.SportsSingleObserver
import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.common.asyncNetworkRequest
import com.example.sportsstore.data.Checkout
import com.example.sportsstore.data.repo.order.OrderRepository

class CheckoutViewModel(orderId: Int, orderRepository: OrderRepository) :
    SportsViewModel() {
    val checkOutLiveDefFoundError= MutableLiveData<Checkout>()

        init {
            orderRepository.checkOut(orderId)
                .asyncNetworkRequest()
                .subscribe(object :SportsSingleObserver<Checkout>(compositeDisposable){
                    override fun onSuccess(t: Checkout) {
                        checkOutLiveDefFoundError.value = t
                    }
                })
        }
}