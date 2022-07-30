package com.example.sportsstore

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class SportsFragment : Fragment(), SportsView {
    override fun setProgressIndicator(mustShow: Boolean) {
        TODO("Not yet implemented")
    }
}

abstract class SportsActivity : AppCompatActivity(), SportsView {
    override fun setProgressIndicator(mustShow: Boolean) {
        TODO("Not yet implemented")
    }
}

interface SportsView {
    fun setProgressIndicator(mustShow: Boolean)
}

abstract class SportsViewModel
