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
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    val couroutineApi = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_signup, container, false)

        view.signUpConfirmBtn.setOnClickListener {
            var errorMessage : String = ""

            if (view.editFirstName.text.toString().isNullOrBlank())
                errorMessage += "First name empty. "
            if(view.editLastName.text.toString().isNullOrBlank())
                errorMessage += "Last name empty. "
            if(view.editEmail.text.toString().isNullOrBlank())
                errorMessage += "Mail empty. "
            if(view.editPassword.text.toString().isNullOrBlank())
                errorMessage += "Password empty. "
            if(view.editConfirmPassword.text.toString().isNullOrBlank() || !view.editConfirmPassword.text.toString().equals(view.editPassword.text.toString()))
                errorMessage += "Confirmation password not correct. "

            if (!errorMessage.equals("")){
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            } else {
                signUp()
            }

        }

        return view
    }

    fun signUp(){
        val signUpForm = SignUpForm(editFirstName.text.toString(), editLastName.text.toString(),
            editEmail.text.toString(), editPassword.text.toString(),
            editConfirmPassword.text.toString())

        couroutineApi.launch {
            val response = Api.INSTANCE.userService.signUp(signUpForm)
            if (response.isSuccessful){
                // Ajouter le token dans les shared pref
                val fetchedToken = response.body()?.token
                saveToken(fetchedToken!!)
                //On charge mainActivity
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Sign up error", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun saveToken(token : String){
        context?.getSharedPreferences("TEST", Context.MODE_PRIVATE)!!.edit {
            putString(SHARED_PREF_TOKEN_KEY, token)
        }
    }

}
