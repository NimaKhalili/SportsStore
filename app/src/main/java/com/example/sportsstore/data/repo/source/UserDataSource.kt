package com.example.sportsstore.data.repo.source

import com.example.sportsstore.data.MessageResponse
import com.example.sportsstore.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(username:String, password:String): Single<TokenResponse>
    fun signUp(username:String, password:String):Single<MessageResponse>
    fun loadToken()
    fun saveToken(token:String, refreshToken:String)
}