package com.example.dojomovie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dojomovie.adapters.HistoryGalleryAdapter
import com.example.dojomovie.model.Transaction
import com.example.dojomovie.util.DB

class HistoryActivity : AppCompatActivity() {

    lateinit var ivBack: ImageView
    lateinit var rvHistoryList: RecyclerView
    lateinit var historyAdapter: HistoryGalleryAdapter

    companion object{
        val transactionList = mutableListOf<Transaction>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        ivBack = findViewById(R.id.ivBack)
        rvHistoryList = findViewById(R.id.rvHistoryList)

        if(DB.LOGGED_IN_USER != null){
            val loadedTransactions = DB.getTransactionHistory(this, DB.LOGGED_IN_USER!!.id)
            transactionList.clear()
            transactionList.addAll(loadedTransactions)
        } else{
            val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
            val userId = sharedPreferences.getInt("userId", -1)
            val loadedTransactions = DB.getTransactionHistory(this, userId)
            transactionList.clear()
            transactionList.addAll(loadedTransactions)
        }
        

        historyAdapter = HistoryGalleryAdapter(transactionList, this@HistoryActivity)
        rvHistoryList.layoutManager = LinearLayoutManager(this@HistoryActivity)
        rvHistoryList.adapter = historyAdapter

        val assetManager = assets
        val inputStream = assetManager.open("arrowback.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        ivBack.setImageDrawable(drawable)
        ivBack.background = null

        ivBack.setOnClickListener{
            finish()
        }
    }
}