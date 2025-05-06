package com.example.dojomovie

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.SmsManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.dojomovie.util.DB

class OtpPage : AppCompatActivity() {

    lateinit var ivOTP: ImageView
    lateinit var tvResend: TextView
    lateinit var tvCountdown: TextView
    lateinit var smsManager: SmsManager
    lateinit var btnVerify: Button
    lateinit var pvOtp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp_page)

        ivOTP = findViewById(R.id.ivOTP)
        tvResend = findViewById(R.id.tvResend)
        tvCountdown = findViewById(R.id.tvCountdown)
        btnVerify = findViewById(R.id.btnVerify)
        pvOtp = findViewById(R.id.pvOtp)

        smsManager = SmsManager.getDefault()

        var phoneRegist = DB.REGISTERED_USER?.phoneNumber
        var passwordRegist = DB.REGISTERED_USER?.password
        var phoneLogin = DB.LOGGED_IN_USER?.phoneNumber
        var otp = generateOTP()

        //TODO: JANGAN LUPA DIHAPUS YA BANG NANTI KETAHUAN RAHASIA KITA XIXIXI
        pvOtp.setText(otp)

        if (phoneRegist != null) {
            checkSMSPermission(phoneRegist, otp)
        } else if(phoneLogin != null){
            checkSMSPermission(phoneLogin, otp)
        }

        val assetManager = assets
        val inputStream = assetManager.open("otp.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivOTP.setImageDrawable(drawable)
        ivOTP.background = null

        //start countdown buat resend OTP
        startCountdown()

        tvResend.setOnClickListener{
            if (phoneRegist != null) {
                otp = generateOTP()
                checkSMSPermission(phoneRegist, otp)
            } else if(phoneLogin != null){
                otp = generateOTP()
                checkSMSPermission(phoneLogin, otp)
            }
            startCountdown()
        }

        btnVerify.setOnClickListener{
            if (otp == pvOtp.text.toString()){
                if (DB.REGISTERED_USER != null){
                    if (phoneRegist != null && passwordRegist != null) {
                        DB.insertNewUser(this@OtpPage, phoneRegist, passwordRegist)
                    }

                    var intent = Intent(OtpPage@this, LoginActivity::class.java)
                    startActivity(intent)

                    finish()
                } else if (DB.LOGGED_IN_USER != null){
                    val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("userPhoneNumber", phoneLogin)
                    editor.apply()


                    var intent = Intent(OtpPage@this, HomeActivity::class.java)
                    startActivity(intent)

                    finish()
                }
            } else{
                Toast.makeText(applicationContext, "OTP Invalid", Toast.LENGTH_SHORT).show()
            }
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
        val message = "Kode OTP Anda adalah $OTP. Jangan berikan kepada siapapun."
        smsManager.sendTextMessage(phonenumber, null, message, null, null)
        Toast.makeText(applicationContext, "OTP has been sent", Toast.LENGTH_SHORT).show()
    }

}