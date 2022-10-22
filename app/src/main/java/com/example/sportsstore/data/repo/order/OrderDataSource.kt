package com.example.sportsstore.data.repo.order

import com.example.sportsstore.data.Checkout
import com.example.sportsstore.data.SubmitOrderResult
import io.reactivex.Single

interface OrderDataSource {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>

    fun checkOut(orderId: Int): Single<Checkout>
}