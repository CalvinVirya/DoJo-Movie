package com.example.dojomovie

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dojomovie.util.DB

class ProfileActivity : AppCompatActivity() {

    lateinit var btnLogout: Button
    lateinit var ivBack: ImageView
    lateinit var ivProfile: ImageView
    lateinit var tvPhone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        btnLogout = findViewById(R.id.btnLogout)
        ivBack = findViewById(R.id.ivBack)
        ivProfile = findViewById(R.id.ivProfile)
        tvPhone = findViewById(R.id.tvPhone)

        val assetManager = assets
        val inputStream = assetManager.open("arrowback.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivBack.setImageDrawable(drawable)
        ivBack.background = null

        val assetManagerProfile = assets
        val inputStreamProfile = assetManager.open("user.png")
        val drawableProfile = Drawable.createFromStream(inputStreamProfile, null)
        ivProfile.setImageDrawable(drawableProfile)
        ivProfile.background = null

        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val userPhoneNumber = sharedPreferences.getString("userPhoneNumber", "unknown")

        val loggedUser = DB.LOGGED_IN_USER?.phoneNumber ?: "unknown"
        tvPhone.setText("+62" + userPhoneNumber)

        btnLogout.setOnClickListener{
            confirmationDialog(this)
        }

        ivBack.setOnClickListener{
            finish()
        }
    }

    fun confirmationDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure want to Logout?")

        builder.setPositiveButton("OK"){_,_ ->
            val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            DB.signOut()

            var intent = Intent(ProfileActivity@this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            finish()
        }

        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }
}