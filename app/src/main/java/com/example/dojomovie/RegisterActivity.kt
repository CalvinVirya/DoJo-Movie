package com.example.dojomovie

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {

    lateinit var ivLogo: ImageView
    lateinit var tvLogInHere: TextView
    lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ivLogo = findViewById(R.id.ivLogo)
        tvLogInHere = findViewById(R.id.tvLogInHere)
        btnRegister = findViewById(R.id.btnRegister)

        val assetManager = assets
        val inputStream = assetManager.open("logo.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivLogo.setImageDrawable(drawable)
        ivLogo.background = null

        tvLogInHere.setOnClickListener{
            var intent = Intent(RegisterActivity@this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener{
            var intent = Intent(RegisterActivity@this, OtpPage::class.java)
            startActivity(intent)
        }

    }
}