package com.example.sportsstore.feature.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsstore.R
import com.example.sportsstore.common.SportsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : SportsFragment() {
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.productsLiveData.observe(this) {
            Timber.i(it.toString())
        }

        mainViewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }
    }
}