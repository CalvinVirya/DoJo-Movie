package com.example.dojomovie

import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.dojomovie.model.Film
import com.example.dojomovie.util.DB
import kotlin.time.times

class FilmDetailActivity : AppCompatActivity() {

    lateinit var ivBack: ImageView
    lateinit var tvMovieBar: TextView
    lateinit var ivMoviePoster: ImageView
    lateinit var tvMovieTitle: TextView
    lateinit var tvMoviePrice: TextView
    lateinit var etQuantity: EditText
    lateinit var tvTotalPrice: TextView
    lateinit var btnBuy: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_film_detail)

        ivBack = findViewById(R.id.ivBack)
        tvMovieBar = findViewById(R.id.tvMovieBar)
        ivMoviePoster = findViewById(R.id.ivMoviePoster)
        tvMovieTitle = findViewById(R.id.tvMovieTitle)
        tvMoviePrice = findViewById(R.id.tvMoviePrice)
        etQuantity = findViewById(R.id.etQuantity)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnBuy = findViewById(R.id.btnBuy)

        val film = intent.getParcelableExtra<Film>("FILM")

        tvMovieBar.setText(film?.title ?: "unknown")
        tvMovieTitle.setText(film?.title ?: "unknown")
        tvMoviePrice.setText("Rp${film?.price.toString()}")
        etQuantity.setText("1")

        val assetManager = assets
        val inputStream = assetManager.open("arrowback.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivBack.setImageDrawable(drawable)
        ivBack.background = null

        val assetManagerMoviePoster = assets
        val inputStreamMoviePoster = assetManagerMoviePoster.open("logoinvers.png")
        val drawableMoviePoster = Drawable.createFromStream(inputStreamMoviePoster, null)
        ivMoviePoster.setImageDrawable(drawableMoviePoster)
        ivMoviePoster.background = null

        ivBack.setOnClickListener{
            finish()
        }

        if (film != null) {
            totalPrice(film.price, 1)
        }

        etQuantity.addTextChangedListener{
            var quantity = etQuantity.text.toString().toIntOrNull() ?: 1
            if(quantity != null){
                if (film != null) {
                    totalPrice(film.price, quantity.toInt())
                }
            }
        }

        btnBuy.setOnClickListener{
            var quantity = etQuantity.text.toString().toIntOrNull() ?: 1
            if (film != null) {
                if(DB.LOGGED_IN_USER != null){
                    DB.insertNewTransaction(this@FilmDetailActivity, DB.LOGGED_IN_USER!!.id, film.id, quantity)
                    Toast.makeText(applicationContext, "berhasil", Toast.LENGTH_SHORT).show()
                } else{
                    val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
                    val userId = sharedPreferences.getInt("userId", -1)
                    DB.insertNewTransaction(this@FilmDetailActivity, userId, film.id, quantity)
                    Toast.makeText(applicationContext, "berhasil", Toast.LENGTH_SHORT).show()
                }

                btnBuy.isEnabled = false
            }
        }
    }

    fun totalPrice(moviePrice: Int, quantity: Int) {
        val result = moviePrice * quantity
        tvTotalPrice.text = "Rp${result.toString()}"
    }
}