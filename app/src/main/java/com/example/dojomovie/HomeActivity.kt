package com.example.dojomovie

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.dojomovie.adapters.FilmGalleryAdapter
import com.example.dojomovie.model.Film
import com.example.dojomovie.util.DB
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    lateinit var rvFilmList: RecyclerView
    lateinit var filmAdapter: FilmGalleryAdapter
    private lateinit var requestQueue: RequestQueue

    companion object {
        val filmList = mutableListOf<Film>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawer_layout)

        rvFilmList = findViewById(R.id.rvFilmList)

        requestQueue = Volley.newRequestQueue(this@HomeActivity)

        filmAdapter = FilmGalleryAdapter(filmList, this@HomeActivity)
        rvFilmList.layoutManager = object : LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        rvFilmList.adapter = filmAdapter

        val url = "https://api.npoint.io/66cce8acb8f366d2a508"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                filmList.clear()
                filmList.addAll(parseJson(response))
                filmAdapter.notifyDataSetChanged()
            },
            { error ->
                error.printStackTrace()
            }
        )
        requestQueue.add(request)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        toggle.drawerArrowDrawable.color = Color.parseColor("#E9BE5F")
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> {
                if(this !is HomeActivity){
                    var intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.nav_profile -> {
                var intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_history -> {
                var intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }
        }

        item.isChecked = false
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun parseJson(jsonArray: JSONArray): MutableList<Film> {
        val result = mutableListOf<Film>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            val id = obj.getString("id")
            val image = obj.getString("image")
            val price = obj.getInt("price")
            val title = obj.getString("title")

            result.add(Film(id, title, image, price))
            DB.insertNewFilm(this@HomeActivity, id, title, image, price)
        }
        return result
    }

}