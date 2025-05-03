package com.example.dojomovie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    lateinit var ivLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ivLogo = findViewById(R.id.ivLogo)

        val assetManager = assets
        val inputStream = assetManager.open("logo.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivLogo.setImageDrawable(drawable)
        ivLogo.background = null
    }
}