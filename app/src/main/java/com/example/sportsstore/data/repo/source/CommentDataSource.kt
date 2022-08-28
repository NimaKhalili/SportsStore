package com.example.sportsstore.data.repo.source

import com.example.sportsstore.data.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun getAll(productId: Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}