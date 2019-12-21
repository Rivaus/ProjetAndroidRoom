package com.example.myapplicationempty

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import com.example.myapplicationempty.network.Api
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    val coroutine = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.LoginConfirmBtn.setOnClickListener {
            if (editEmail.text.toString().compareTo("") != 0 && editPassword.text.toString().compareTo("") != 0){
                val loginForm = LoginForm(editEmail.text.toString(), editPassword.text.toString())
                coroutine.launch {
                    val response = Api.INSTANCE.userService.login(loginForm)
                    if (response.isSuccessful){
                        // Ajouter le token dans les shared pref
                        val fetchedToken = response.body()?.token
                        saveToken(fetchedToken!!)
                        //On charge mainActivity
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "Login error", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

        return view
    }

    fun saveToken(token : String){
        context?.getSharedPreferences("TEST", Context.MODE_PRIVATE)!!.edit {
            putString(SHARED_PREF_TOKEN_KEY, token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutine.cancel()
    }

}
