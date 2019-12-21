package com.example.myapplicationempty

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_authentication.*
import kotlinx.android.synthetic.main.fragment_authentication.view.*


class AuthenticationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val token = activity?.getSharedPreferences("TEST", Context.MODE_PRIVATE)?.getString(SHARED_PREF_TOKEN_KEY, "")
        Log.d("Test", token)
        if (!token.isNullOrBlank()) {
            Log.e("COUCOU", "nessage $token")
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        val view = inflater.inflate(R.layout.fragment_authentication, container, false)
        view.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.signUpAction)
        }
        view.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.loginAction)
        }
        return view
    }

    override fun onResume() {
        super.onResume()

    }

}
