package com.example.sportsstore.feature.profile

import com.example.sportsstore.common.SportsViewModel
import com.example.sportsstore.data.TokenContainer
import com.example.sportsstore.data.repo.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : SportsViewModel() {
    val userName: String
        get() = userRepository.getUserName()

    val isSignedIn: Boolean
        get() = TokenContainer.token != null

    fun signOut()=userRepository.signOut()
}