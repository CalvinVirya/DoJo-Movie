package com.example.dojomovie.adapters

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dojomovie.FilmDetailActivity
import com.example.dojomovie.R
import com.example.dojomovie.model.Film

class FilmGalleryAdapter(
    var data: MutableList<Film>,
    var ctx: Context
): RecyclerView.Adapter<FilmGalleryAdapter.FilmGalleryViewHolder>() {
    class FilmGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var llItemFilm: LinearLayout = itemView.findViewById(R.id.llItemFilm)
        var ivFilmImage: ImageView = itemView.findViewById(R.id.ivFilmImage)
        var tvFilmTitle: TextView = itemView.findViewById(R.id.tvFilmTitle)
        var tvFilmPrice: TextView = itemView.findViewById(R.id.tvFilmPrice)

        lateinit var film: Film

        fun bind(film: Film){
            this.film = film
        }

        fun setValue(){
            tvFilmTitle.text = film.title
            tvFilmPrice.text = film.price.toString()

//            TODO("Bikin glide buat gambar sama siapin link gambar")

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmGalleryAdapter.FilmGalleryViewHolder {
        var itemView = LayoutInflater.from(ctx).inflate(R.layout.list_item_film, parent, false)

        return FilmGalleryViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: FilmGalleryAdapter.FilmGalleryViewHolder, position: Int) {
        var film = data[position]
        holder.bind(film)
        holder.setValue()

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val itemCount = data.size
        holder.itemView.layoutParams.width = screenWidth / itemCount

        holder.llItemFilm.setOnClickListener{
            var intent = Intent(ctx, FilmDetailActivity::class.java).apply {
                putExtra("FILM", film)
            }
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}