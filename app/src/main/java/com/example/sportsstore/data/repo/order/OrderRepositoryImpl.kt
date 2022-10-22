package com.example.sportsstore.data.repo.order

import com.example.sportsstore.data.Checkout
import com.example.sportsstore.data.SubmitOrderResult
import io.reactivex.Single

class OrderRepositoryImpl(val orderDataSource: OrderDataSource):OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderDataSource.submit(firstName,lastName, postalCode, phoneNumber, address, paymentMethod)
    }

    override fun checkOut(orderId: Int): Single<Checkout> {
        return orderDataSource.checkOut(orderId)
    }
}