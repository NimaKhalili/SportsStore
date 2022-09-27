package com.example.sportsstore.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.sportsstore.R
import com.example.sportsstore.common.SportsSingleObserver
import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.common.asyncNetworkRequest
import com.example.sportsstore.data.*
import com.example.sportsstore.data.repo.CartRepository
import io.reactivex.Completable
import timber.log.Timber

class CartViewModel(val cartRepository: CartRepository) : SportsViewModel() {
    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    private fun getCartItems() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            emptyStateLiveData.value = EmptyState(false)
            cartRepository.get()
                .asyncNetworkRequest()
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : SportsSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemsLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.total_price, t.shipping_cost, t.payable_price)
                        }
                        else
                            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyState)
                    }
                })
        } else
            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyStateLoginRequired, true)
    }

    fun removeItemFromCart(cartItem: CartItem): Completable {
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                Timber.i("Cart Items Counts After Remove -> ${cartItemsLiveData.value?.size}")
                calculateAndPublishPurchaseDetail()
                cartItemsLiveData.value?.let {
                    if (it.isEmpty())
                        emptyStateLiveData.postValue(EmptyState(true, R.string.cartEmptyState, false))
                }
            }.ignoreElement()
    }

    fun increaseCartItemCount(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
            }.ignoreElement()

    fun decreaseCartItemCount(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
            }
            .ignoreElement()

    fun refresh() {
        getCartItems()
    }

    private fun calculateAndPublishPurchaseDetail() {
        cartItemsLiveData.value?.let { cartItems ->
            purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                purchaseDetail.totalPrice = totalPrice
                purchaseDetail.payable_price = payablePrice
                purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }
    }
}