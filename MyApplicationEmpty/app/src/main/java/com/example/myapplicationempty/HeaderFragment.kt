package com.example.myapplicationempty

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplicationempty.network.Api
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.fragment_header.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


class HeaderFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_header, container, false)
        return view
    }

    private val coroutineScope = MainScope()

    override fun onResume() {
        super.onResume()

        var imageUrl : String?

        coroutineScope.launch {
            val userinfo = Api.INSTANCE.userService.getInfo().body()
            intro.text = userinfo?.firstName + " " + userinfo?.lastName
            imageUrl = userinfo?.avatar

            if (imageUrl != null){
                Glide.with(this@HeaderFragment).load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image_avatar)
            } else {
                Glide.with(this@HeaderFragment).load(avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image_avatar)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }


}
