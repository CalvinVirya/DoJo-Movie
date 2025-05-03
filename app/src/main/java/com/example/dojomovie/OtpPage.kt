package com.example.dojomovie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OtpPage : AppCompatActivity() {

    lateinit var ivOTP: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp_page)

        ivOTP = findViewById(R.id.ivOTP)

        val assetManager = assets
        val inputStream = assetManager.open("otp.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivOTP.setImageDrawable(drawable)
        ivOTP.background = null

    }
}