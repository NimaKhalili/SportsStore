package com.example.sportsstore.data.repo.source

import com.example.sportsstore.services.http.ApiService
import com.example.sportsstore.data.Comment
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService):CommentDataSource {
    override fun getAll(productId: Int): Single<List<Comment>> = apiService.getComments(productId)
    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}