package com.example.dojomovie

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.SmsManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dojomovie.model.User
import com.example.dojomovie.util.DB
import org.w3c.dom.Text

class OtpPage : AppCompatActivity() {

    lateinit var ivOTP: ImageView
    lateinit var tvResend: TextView
    lateinit var tvCountdown: TextView
    lateinit var smsManager: SmsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp_page)

        ivOTP = findViewById(R.id.ivOTP)
        tvResend = findViewById(R.id.tvResend)
        tvCountdown = findViewById(R.id.tvCountdown)

        smsManager = SmsManager.getDefault()

        var phone = DB.REGISTERED_USER?.phoneNumber
        var otp = generateOTP()

        Toast.makeText(applicationContext, phone, Toast.LENGTH_SHORT).show()

        if (phone != null) {
            checkSMSPermission(phone, otp)
        }

        val assetManager = assets
        val inputStream = assetManager.open("otp.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivOTP.setImageDrawable(drawable)
        ivOTP.background = null

//        start countdown buat resend OTP
        startCountdown()

        tvResend.setOnClickListener{
//            Toast.makeText(applicationContext, "resend tapped", Toast.LENGTH_SHORT).show()
            if (phone != null) {
                checkSMSPermission(phone, generateOTP())
            }
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

    fun generateOTP(length: Int = 6): String {
        val digits = "0123456789"
        return (1..length)
            .map { digits.random() }
            .joinToString("")
    }


    fun checkSMSPermission(phonenumber: String, OTP: String){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.SEND_SMS),
                100
            )
        }else{
            sendOTP(phonenumber, OTP)
        }

    }

    fun sendOTP(phonenumber: String, OTP: String){
        smsManager.sendTextMessage(phonenumber, null, OTP, null, null)
    }

}