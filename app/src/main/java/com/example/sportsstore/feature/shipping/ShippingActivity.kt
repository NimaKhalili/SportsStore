package com.example.sportsstore.feature.shipping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportsstore.R
import com.example.sportsstore.common.EXTRA_KEY_DATA
import com.example.sportsstore.data.PurchaseDetail
import com.example.sportsstore.feature.cart.CartItemAdapter
import kotlinx.android.synthetic.main.activity_shipping.*

class ShippingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
            ?: throw IllegalStateException("purchase detail cannot be null")

        val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
        viewHolder.bind(purchaseDetail.totalPrice, purchaseDetail.shipping_cost,purchaseDetail.payable_price)
    }
}