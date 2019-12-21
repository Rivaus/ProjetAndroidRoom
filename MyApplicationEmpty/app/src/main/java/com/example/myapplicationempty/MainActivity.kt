package com.example.myapplicationempty

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_btn.setOnClickListener {
            val intent = Intent(this, TaskFormActivity::class.java)
            startActivity(intent); }

        logOutBtn.setOnClickListener {
            val pref = getSharedPreferences("TEST", Context.MODE_PRIVATE)
            val editor = pref?.edit()
            editor?.remove(SHARED_PREF_TOKEN_KEY)
            editor?.commit()
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
    }
}
