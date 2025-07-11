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
import com.example.dojomovie.util.DB

class LoginActivity : AppCompatActivity() {

    lateinit var ivLogo: ImageView
    lateinit var tvRegisterHere: TextView
    lateinit var btnLogin: Button
    lateinit var etPhoneNumber: EditText
    lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ivLogo = findViewById(R.id.ivLogo)
        tvRegisterHere = findViewById(R.id.tvRegisterHere)
        btnLogin = findViewById(R.id.btnLogin)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etPassword = findViewById(R.id.etPassword)

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
            if (!validateInput(etPhoneNumber) && !validateInput(etPassword)){
                signIn()
            } else{
                Toast.makeText(applicationContext, "Cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun validateInput(editText: EditText) : Boolean{
        return editText.text.toString().trim().isEmpty()
    }

    fun signIn() {
        val phoneNumber = etPhoneNumber.text.toString()
        val password = etPassword.text.toString()

        DB.login(phoneNumber, password)

        if (DB.LOGGED_IN_USER == null) {
            Toast.makeText(applicationContext, "Account not found", Toast.LENGTH_SHORT).show()
            return
        }

        if (DB.REGISTERED_USER != null){
            var intent = Intent(LoginActivity@this, HomeActivity::class.java)
            val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.putString("userPhoneNumber", DB.LOGGED_IN_USER!!.phoneNumber)
            editor.putInt("userId", DB.LOGGED_IN_USER!!.id)
            editor.apply()
            startActivity(intent)
            finish()
        } else{
            var intent = Intent(LoginActivity@this, OtpPage::class.java)
            startActivity(intent)
            finish()
        }

    }

}