package com.example.foodrunner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var coordinator_layout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var navigation_view: NavigationView
    lateinit var frame: FrameLayout
    lateinit var drawer_layout: DrawerLayout

    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar = findViewById(R.id.toolbar)
        drawer_layout = findViewById(R.id.drawer_layout)
        navigation_view = findViewById(R.id.navigation_view)
        frame = findViewById(R.id.frame)

        openDashBoard()

        navigation_view.setNavigationItemSelectedListener {

            if(previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.dashboard -> {
                    openDashBoard()
                    drawer_layout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, Profile())

                        .commit()

                    drawer_layout.closeDrawers()
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, Favourites())

                        .commit()

                    drawer_layout.closeDrawers()
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, About())

                        .commit()

                    drawer_layout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }

        setUpToolBar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@HomeActivity,
            drawer_layout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    fun setUpToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home) {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun openDashBoard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, Dashboard())
            .addToBackStack("Dashboard")
            .commit()
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        navigation_view.checkedItem?.isChecked = false

        when(frag){
            !is Dashboard-> openDashBoard()

            else-> finishAffinity()
        }
    }
}
