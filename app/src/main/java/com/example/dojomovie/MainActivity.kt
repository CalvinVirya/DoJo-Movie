package com.example.dojomovie

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var btnExplore: Button
    lateinit var ivIntro: ImageView
    lateinit var btnNewDojo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnExplore = findViewById(R.id.btnExplore)
        btnNewDojo = findViewById(R.id.btnNewDojo)
        ivIntro = findViewById(R.id.ivIntro)

        val assetManager = assets
        val inputStream = assetManager.open("intro.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivIntro.setImageDrawable(drawable)
        ivIntro.background = null

        btnExplore.setOnClickListener {
            var intent = Intent(MainActivity@this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnNewDojo.setOnClickListener({
            var intent = Intent(MainActivity@this, RegisterActivity::class.java)
            startActivity(intent)
        })
    }

}