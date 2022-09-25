package com.example.sportsstore.feature.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsstore.R
import com.example.sportsstore.common.formatPrice
import com.example.sportsstore.data.CartItem
import com.example.sportsstore.data.PurchaseDetail
import com.example.sportsstore.services.ImageLoadingService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_purchase_details.view.*

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAIL = 1

class CartItemAdapter(
    val cartItems: MutableList<CartItem>,//Mutalbe list baraye in hast ke betonim rosh taghir ijad bokonim
    val imageLoadingService: ImageLoadingService,
    val cartItemViewCallBacks: CartItemViewCallBacks
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail: PurchaseDetail? = null

    inner class CartItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindCartItem(cartItem: CartItem) {
            containerView.productTitleTv.text = cartItem.product.title
            containerView.cartItemCountTv.text = cartItem.count.toString()
            containerView.previousPriceTv.text =
                formatPrice(cartItem.product.price + cartItem.product.discount)
            containerView.priceTv.text = formatPrice(cartItem.product.price)
            imageLoadingService.load(containerView.productIv, cartItem.product.image)
            containerView.removeFromCartBtn.setOnClickListener {
                cartItemViewCallBacks.onRemoveCartItemButtonClick(cartItem)
            }

            containerView.changeCountProgressBar.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE

            containerView.cartItemCountTv.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE

            containerView.increaseBtn.setOnClickListener {
                cartItem.changeCountProgressBarIsVisible = true
                containerView.changeCountProgressBar.visibility = View.VISIBLE
                containerView.cartItemCountTv.visibility = View.INVISIBLE
                cartItemViewCallBacks.onIncreaseCartItemButtonClick(cartItem)
            }

            containerView.decreaseBtn.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarIsVisible = true
                    containerView.changeCountProgressBar.visibility = View.VISIBLE
                    containerView.cartItemCountTv.visibility = View.INVISIBLE
                    cartItemViewCallBacks.onDecreaseCartItemButtonClick(cartItem)
                }
            }

            containerView.productIv.setOnClickListener {
                cartItemViewCallBacks.onProduceImageClick(cartItem)
            }

            if (cartItem.changeCountProgressBarIsVisible) {
                containerView.changeCountProgressBar.visibility = View.VISIBLE
                containerView.increaseBtn.visibility = View.GONE
            }
        }
    }

    class PurchaseDetailViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(totalPrice: Int, shippingCost: Int, payablePrice: Int) {
            containerView.totalPriceTv.text = formatPrice(totalPrice)
            containerView.shippingCostTv.text = formatPrice(shippingCost)
            containerView.payablePriceTv.text = formatPrice(payablePrice)
        }
    }

    interface CartItemViewCallBacks {
        fun onRemoveCartItemButtonClick(cartItem: CartItem)
        fun onIncreaseCartItemButtonClick(cartItem: CartItem)
        fun onDecreaseCartItemButtonClick(cartItem: CartItem)
        fun onProduceImageClick(cartItem: CartItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CART_ITEM)
            return CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
            )
        else
            return PurchaseDetailViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_purchase_details, parent, false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder)
            holder.bindCartItem(cartItems[position])
        else if (holder is PurchaseDetailViewHolder) {
            purchaseDetail?.let {//in khat yani agar null nabod
                holder.bind(it.totalPrice, it.shipping_cost, it.payable_price)
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == cartItems.size)//in shart yani akharin position
            return VIEW_TYPE_PURCHASE_DETAIL
        else
            return VIEW_TYPE_CART_ITEM
    }

    fun removeCartItem(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increaseCount(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    fun decreaseCount(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }
}