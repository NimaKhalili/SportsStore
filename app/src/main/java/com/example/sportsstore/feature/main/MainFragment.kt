package com.example.sportsstore.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsstore.R
import com.example.sportsstore.common.EXTRA_KEY_DATA
import com.example.sportsstore.common.SportsFragment
import com.example.sportsstore.common.convertDpToPixel
import com.example.sportsstore.data.Product
import com.example.sportsstore.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : SportsFragment(), ProductListAdapter.OnProductClickListener {
    val mainViewModel: MainViewModel by viewModel()
    val productListAdapter: ProductListAdapter by inject()
    val popularProductListAdapter: PopularProductListAdapter by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latestProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        latestProductsRv.adapter = productListAdapter
        productListAdapter.onProductClickListener = this

        popularProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        popularProductsRv.adapter = popularProductListAdapter

        mainViewModel.productsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.popularProductsLiveLiveData.observe(viewLifecycleOwner) {
            popularProductListAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        mainViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            bannerSliderViewPager.adapter = bannerSliderAdapter

            val viewPagerHeight = (((bannerSliderViewPager.width - convertDpToPixel(
                32f,
                requireContext()
            )) * 173) / 328).toInt()
            val layoutParams = bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParams
            sliderIndicator.setViewPager2(bannerSliderViewPager)
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}