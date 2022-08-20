package com.example.sportsstore.services

import com.example.sportsstore.view.SportsImageView
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.IllegalStateException

class FrescoImageLoadingService: ImageLoadingService {
    override fun load(imageView: SportsImageView, imageUrl: String) {
        if(imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw IllegalStateException("ImageView must be an instance of SimpleDraweeView")
    }
}