package com.example.dojomovie

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dojomovie.model.Film

class FilmDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_film_detail)

        val film = intent.getParcelableExtra<Film>("FILM")

        if (film != null) {
            Toast.makeText(applicationContext, film.title, Toast.LENGTH_SHORT).show()
        }

    }
}