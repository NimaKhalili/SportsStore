package com.example.sportsstore.feature.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsstore.R
import com.example.sportsstore.common.SportsFragment
import com.example.sportsstore.feature.auth.AuthActivity
import com.example.sportsstore.feature.favorites.FavoriteProductsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class ProfileFragment : SportsFragment() {
    private val viewMode:ProfileViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteProductBtn.setOnClickListener{
            startActivity(Intent(requireContext(), FavoriteProductsActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if (viewMode.isSignedIn){
            authBtn.text = getString(R.string.signOut)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_out, 0)
            usernameTv.text = viewMode.userName
            authBtn.setOnClickListener{
                viewMode.signOut()
                checkAuthState()
            }
        }
        else{
            authBtn.text = getString(R.string.signIn)
            authBtn.setOnClickListener{
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }

            authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_in, 0)
            usernameTv.text = getString(R.string.guest_user)
        }
    }

}