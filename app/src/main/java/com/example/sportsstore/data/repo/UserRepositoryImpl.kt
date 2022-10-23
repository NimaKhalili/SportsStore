package com.example.sportsstore.data.repo

import com.example.sportsstore.data.TokenContainer
import com.example.sportsstore.data.TokenResponse
import com.example.sportsstore.data.repo.source.UserDataSource
import com.example.sportsstore.data.repo.source.UserLocalDataSource
import com.example.sportsstore.data.repo.source.UserRemoteDataSource
import io.reactivex.Completable

class UserRepositoryImpl(
    val userLocalDataSource: UserDataSource,
    val userRemoteDataSource: UserDataSource
) : UserRepository {
    override fun login(username: String, password: String): Completable {
        return userRemoteDataSource.login(username, password).doOnSuccess {
            onSuccessfulLogin(username, it)
        }.ignoreElement()
    }

    override fun signUp(username: String, password: String): Completable {
        return userRemoteDataSource.signUp(username, password).flatMap {
            userRemoteDataSource.login(username, password)
        }.doOnSuccess {
            onSuccessfulLogin(username, it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String = userLocalDataSource.getUserName()
    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null, null)
    }

    fun onSuccessfulLogin(username: String, tokenResponse: TokenResponse){
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveUserName(username)
    }
}