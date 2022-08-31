package com.example.sportsstore.feature.home

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
import com.example.sportsstore.data.SORT_LATEST
import com.example.sportsstore.data.SORT_POPULAR
import com.example.sportsstore.feature.common.ProductListAdapter
import com.example.sportsstore.feature.common.VIEW_TYPE_ROUND
import com.example.sportsstore.feature.list.ProductListActivity
import com.example.sportsstore.feature.main.BannerSliderAdapter
import com.example.sportsstore.feature.main.PopularProductListAdapter
import com.example.sportsstore.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class HomeFragment : SportsFragment(), ProductListAdapter.OnProductClickListener {
    val homeViewModel: HomeViewModel by viewModel()
    val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }
    val popularProductListAdapter: PopularProductListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
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

        homeViewModel.productsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        viewLatestProductsBtn.setOnClickListener{
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        viewPopularProductsBtn.setOnClickListener{
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_POPULAR)
            })
        }

        homeViewModel.popularProductsLiveLiveData.observe(viewLifecycleOwner) {
            popularProductListAdapter.products = it as ArrayList<Product>
        }

        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
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