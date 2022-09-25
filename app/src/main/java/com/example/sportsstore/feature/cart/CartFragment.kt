package com.example.sportsstore.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsstore.R
import com.example.sportsstore.common.EXTRA_KEY_DATA
import com.example.sportsstore.common.SportsCompletableObserver
import com.example.sportsstore.common.SportsFragment
import com.example.sportsstore.data.CartItem
import com.example.sportsstore.feature.product.ProductDetailActivity
import com.example.sportsstore.services.ImageLoadingService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment : SportsFragment(), CartItemAdapter.CartItemViewCallBacks {
    val viewModel:CartViewModel by viewModel()
    var cartItemAdapter:CartItemAdapter?=null
    val imageLoadingService:ImageLoadingService by inject()
    val compositeDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        viewModel.cartItemsLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())
            cartItemsRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            cartItemAdapter = CartItemAdapter(it as MutableList<CartItem>, imageLoadingService, this)
            cartItemsRv.adapter = cartItemAdapter
        }

        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){
            cartItemAdapter?.let { adapter ->
                adapter.purchaseDetail = it
                adapter.notifyItemChanged(adapter.cartItems.size)//chon mikhaym akharin item ro biaym notify konimesh ta bedonim purchaseDetail taghir karde ast
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onRemoveCartItemButtonClick(cartItem: CartItem) {
        viewModel.removeItemFromCart(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :SportsCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.removeCartItem(cartItem)
                }
            })
    }

    override fun onIncreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.increaseCartItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :SportsCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.increaseCount(cartItem)
                }
            })
    }

    override fun onDecreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.decreaseCartItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :SportsCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.decreaseCount(cartItem)
                }
            })
    }

    override fun onProduceImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, cartItem.product)
        })
    }
}