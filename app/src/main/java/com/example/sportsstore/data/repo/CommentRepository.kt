package com.example.sportsstore.data.repo

import com.example.sportsstore.data.Comment
import io.reactivex.Single

interface CommentRepository {

    fun getAll(productId: Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}