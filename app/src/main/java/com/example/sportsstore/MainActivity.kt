package com.example.sportsstore

import android.os.Bundle
import com.example.sportsstore.common.SportsActivity

class MainActivity : SportsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}