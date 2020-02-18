package com.macdonald.angel.gnb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbarApplication)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        val topLevelDestinations = hashSetOf<Int>() //Add the main destinations
        topLevelDestinations.add(R.id.transactionsListFragment)

        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
            .build()

        setupActionBar(navController, appBarConfiguration)

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.transactionsListFragment -> {
                    toolbar?.visibility = View.VISIBLE
                    bottomNavMenu.visibility = View.VISIBLE
                }
                R.id.rateListFragment -> {
                    toolbar?.visibility = View.VISIBLE
                    bottomNavMenu.visibility = View.GONE
                }
                R.id.productListFragment -> {
                    toolbar?.visibility = View.VISIBLE
                    bottomNavMenu.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHostFragment).navigateUp(appBarConfiguration)
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavMenu)
        bottomNav?.setupWithNavController(navController)
    }

}
