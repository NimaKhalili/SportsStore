package com.example.sportsstore.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.sportsstore.common.SportsSingleObserver
import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.common.asyncNetworkRequest
import com.example.sportsstore.data.CartItem
import com.example.sportsstore.data.CartResponse
import com.example.sportsstore.data.PurchaseDetail
import com.example.sportsstore.data.TokenContainer
import com.example.sportsstore.data.repo.CartRepository
import io.reactivex.Completable

class CartViewModel(val cartRepository: CartRepository) : SportsViewModel() {
    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()

    private fun getCartItems() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
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
                    }
                })
        }
    }

    //chera Completable : chon view faghat ehtiaj dare bedone in amaliat remove anjam shod ya nashod
    //va dalile inke ehtiaj dare faghat bedone in hast ke :
    //agar amaliat anjam shod on iteme ro az adapter bekhaym hazf bokonim
    fun removeItemFromCart(cartItem: CartItem): Completable {
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
            }.ignoreElement()
    }

    //chera ignoreElement : chon natije cartRepository.remove az noe Single ast ama inja Completable
    //ast bara tabdile be Completable az ignoreElement estefade mikonim

    //2ta method paeen dar samte server nistand va haminja meghdareshon ro change mikonim
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