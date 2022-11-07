package com.example.sportsstore.feature.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsstore.R
import com.example.sportsstore.common.EXTRA_KEY_DATA
import com.example.sportsstore.common.SportsActivity
import com.example.sportsstore.data.Product
import com.example.sportsstore.feature.product.ProductDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite_products.*
import kotlinx.android.synthetic.main.view_cart_empty_state.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class FavoriteProductsActivity : SportsActivity(),
    FavoriteProductsAdapter.FavoriteProductEventListener {
    val viewModel: FavoriteProductsViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_products)

        helpBtn.setOnClickListener{
            Snackbar.make(it,R.string.favorites_help_message,Snackbar.LENGTH_SHORT).show()
        }

        viewModel.productsLiveData.observe(this) {
            if (it.isNotEmpty()) {
                favoriteProductsRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                favoriteProductsRv.adapter =
                    FavoriteProductsAdapter(it as MutableList<Product>, this, get())
            } else {
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text = getString(R.string.favorites_empty_state_message)
            }
        }
    }

    override fun onClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onLongClick(product: Product) {
        viewModel.removeFromFavorites(product)
    }
}