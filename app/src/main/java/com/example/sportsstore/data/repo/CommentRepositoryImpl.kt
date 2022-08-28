package com.example.sportsstore.data.repo

import com.example.sportsstore.data.repo.source.CommentDataSource
import com.example.sportsstore.data.Comment
import io.reactivex.Single

class CommentRepositoryImpl(val commentDataSource: CommentDataSource):CommentRepository {
    override fun getAll(productId: Int): Single<List<Comment>> = commentDataSource.getAll(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}