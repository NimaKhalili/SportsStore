package com.example.sportsstore.feature.product.comment

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.example.sportsstore.common.SportsSingleObserver
import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.common.asyncNetworkRequest
import com.example.sportsstore.data.Comment
import com.example.sportsstore.data.repo.CommentRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentListViewModel(productId: Int, commentRepository: CommentRepository):SportsViewModel() {
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value = true
        commentRepository.getAll(productId)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object :SportsSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }

            })
    }
}