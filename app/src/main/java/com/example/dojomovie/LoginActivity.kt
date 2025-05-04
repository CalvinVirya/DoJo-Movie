package com.example.dojomovie

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    lateinit var ivLogo: ImageView
    lateinit var tvRegisterHere: TextView
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ivLogo = findViewById(R.id.ivLogo)
        tvRegisterHere = findViewById(R.id.tvRegisterHere)
        btnLogin = findViewById(R.id.btnLogin)

        val assetManager = assets
        val inputStream = assetManager.open("logo.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivLogo.setImageDrawable(drawable)
        ivLogo.background = null

        tvRegisterHere.setOnClickListener{
            var intent = Intent(LoginActivity@this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            var intent = Intent(LoginActivity@this, OtpPage::class.java)
            startActivity(intent)
        }

    }
}