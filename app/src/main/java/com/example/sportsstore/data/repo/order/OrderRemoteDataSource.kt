package com.example.sportsstore.data.repo.order

import com.example.sportsstore.data.Checkout
import com.example.sportsstore.data.SubmitOrderResult
import com.example.sportsstore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(val apiService: ApiService) : OrderDataSource {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name", firstName)
            addProperty("last_name", lastName)
            addProperty("postal_code", postalCode)
            addProperty("mobile", phoneNumber)
            addProperty("address", address)
            addProperty("payment_method", paymentMethod)
        })
    }

    override fun checkOut(orderId: Int): Single<Checkout> {
        return apiService.checkOut(orderId)
    }
}