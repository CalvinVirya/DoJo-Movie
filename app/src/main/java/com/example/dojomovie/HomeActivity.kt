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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dojomovie.adapters.FilmGalleryAdapter
import com.example.dojomovie.model.Film
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONObject

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    lateinit var rvFilmList: RecyclerView
    lateinit var filmAdapter: FilmGalleryAdapter

        companion object {
            val filmList: MutableList<Film> = run {
                val responseData = """[
              {
                "id": "MV001",
                "image": "pathToImage",
                "price": 30000,
                "title": "Kongzilla"
              },
              {
                "id": "MV002",
                "image": "pathToImage",
                "price": 45000,
                "title": "Final Fantalion"
              },
              {
                "id": "MV003",
                "image": "pathToImage",
                "price": 40000,
                "title": "Bond Jampshoot"
              }
            ]"""

                val jsonArray = JSONArray(responseData)
                val filmList = mutableListOf<Film>()

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val id = obj.getString("id")
                    val image = obj.getString("image")
                    val price = obj.getInt("price")
                    val title = obj.getString("title")

                    filmList.add(Film(id, title, image, price))
                }

                filmList
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawer_layout)

        rvFilmList = findViewById(R.id.rvFilmList)
        filmAdapter = FilmGalleryAdapter(filmList, this@HomeActivity)


        rvFilmList.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvFilmList.adapter = filmAdapter

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
}