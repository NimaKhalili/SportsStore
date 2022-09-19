package com.example.sportsstore.feature.auth

import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.data.repo.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository):SportsViewModel() {

    fun login(email: String, password: String): Completable{
        progressBarLiveData.value = true
        return userRepository.login(email, password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }

    fun signUp(email: String, password: String): Completable{
        progressBarLiveData.value = true
        return userRepository.signUp(email, password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }

}