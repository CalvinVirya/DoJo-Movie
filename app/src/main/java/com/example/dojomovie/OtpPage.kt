package com.example.dojomovie

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class OtpPage : AppCompatActivity() {

    lateinit var ivOTP: ImageView
    lateinit var tvResend: TextView
    lateinit var tvCountdown: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp_page)

        ivOTP = findViewById(R.id.ivOTP)
        tvResend = findViewById(R.id.tvResend)
        tvCountdown = findViewById(R.id.tvCountdown)

        val assetManager = assets
        val inputStream = assetManager.open("otp.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivOTP.setImageDrawable(drawable)
        ivOTP.background = null

//        start countdown buat resend OTP
        startCountdown()

        tvResend.setOnClickListener{
            Toast.makeText(applicationContext, "resend tapped", Toast.LENGTH_SHORT).show()
            startCountdown()
        }
    }

    fun startCountdown(){
        tvResend.isEnabled = false
        tvResend.setTextColor(Color.parseColor("#595B56"))
        object : CountDownTimer(30000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvCountdown.text = "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                tvResend.isEnabled = true
                tvResend.setTextColor(Color.WHITE)
            }
        }.start()
    }
}