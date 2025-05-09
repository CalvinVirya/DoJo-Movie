package com.example.dojomovie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dojomovie.R
import com.example.dojomovie.model.Transaction
import com.example.dojomovie.util.DB

class HistoryGalleryAdapter (
    var data: MutableList<Transaction>,
    var ctx: Context
): RecyclerView.Adapter<HistoryGalleryAdapter.HistoryGalleryViewHolder>(){
    class HistoryGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvHistoryTitle: TextView = itemView.findViewById(R.id.tvHistoryTitle)
        var tvHistoryPrice: TextView = itemView.findViewById(R.id.tvHistoryPrice)
        var tvHistoryQuantity: TextView = itemView.findViewById(R.id.tvHistoryQuantity)

        lateinit var transaction: Transaction

        fun bind(transaction: Transaction){
            this.transaction = transaction
        }

        fun setValue(){
            var film = DB.getFilmDetail(transaction.filmId)
            if (film != null) {
                tvHistoryTitle.text = film.title
                tvHistoryPrice.text = "Rp${film.price.toString()}"
                tvHistoryQuantity.text = "x${transaction.quantity.toString()}"
            } else{
                Toast.makeText(itemView.context, "film not found", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryGalleryViewHolder {
        var itemView = LayoutInflater.from(ctx).inflate(R.layout.list_item_transaction, parent, false)
        return HistoryGalleryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HistoryGalleryViewHolder, position: Int) {
        var transaction = data[position]
        holder.bind(transaction)
        holder.setValue()
        
    }
}