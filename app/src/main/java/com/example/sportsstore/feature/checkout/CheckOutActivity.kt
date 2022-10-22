package com.example.sportsstore.feature.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportsstore.R
import com.example.sportsstore.common.EXTRA_KEY_ID
import com.example.sportsstore.common.formatPrice
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.item_purchase_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : AppCompatActivity() {
    val viewModel: CheckoutViewModel by viewModel {
        val uri: Uri? = intent.data
        if (uri!= null)
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        else
            parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        viewModel.checkOutLiveDefFoundError.observe(this){
            orderPriceTv.text = formatPrice(it.payable_price)
            orderStatusTv.text = it.payment_status
            purchaseStatusTv.text = if(it.purchase_success) "خرید با موفقیت انجام شد" else "خرید ناموفق بود"
        }
    }
}