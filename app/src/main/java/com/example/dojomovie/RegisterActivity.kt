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
import com.example.dojomovie.util.DB
import kotlin.math.sign

class RegisterActivity : AppCompatActivity() {

    lateinit var ivLogo: ImageView
    lateinit var tvLogInHere: TextView
    lateinit var btnRegister: Button
    lateinit var etPhoneNumber: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ivLogo = findViewById(R.id.ivLogo)
        tvLogInHere = findViewById(R.id.tvLogInHere)
        btnRegister = findViewById(R.id.btnRegister)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

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
            signUp()
        }
    }

    fun signUp(){
        val phoneNumber = etPhoneNumber.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if(password == confirmPassword){
            DB.insertNewUser(this@RegisterActivity, phoneNumber, password)
            Toast.makeText(applicationContext, "Register Successful", Toast.LENGTH_SHORT).show()
            var intent = Intent(RegisterActivity@this, OtpPage::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(applicationContext, "Password not matched", Toast.LENGTH_SHORT).show()
        }
//        finish()
    }
}