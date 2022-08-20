package com.example.sportsstore.services

import com.example.sportsstore.view.SportsImageView

interface ImageLoadingService{
    fun load(imageView:SportsImageView, imageUrl: String)
}