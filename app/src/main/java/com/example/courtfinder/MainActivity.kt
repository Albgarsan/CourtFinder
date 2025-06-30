package com.example.courtfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.courtfinder.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        val myBookings = mutableListOf<Booking>()
        val otherBookings = mutableListOf<Booking>()
        var profile = Profile("", "", "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exampleProfile = Profile("John", "Doe", "Intermediate")
        val exampleBooking = Booking("Tennis", "Court 2", "25/07/2025", "9:00", true, exampleProfile)
        val exampleBooking2 = Booking("Tennis", "Court 1", "26/07/2025", "12:00", true, exampleProfile)
        val exampleBooking3 = Booking("Tennis", "Court 3", "27/07/2025", "15:00", true, exampleProfile)
        val exampleBooking4 = Booking("Padel", "Court 1", "27/07/2025", "15:00", true, exampleProfile)
        val exampleBooking5 = Booking("Padel", "Court 2", "28/07/2025", "10:00", true, exampleProfile)

        otherBookings.add(exampleBooking)
        otherBookings.add(exampleBooking2)
        otherBookings.add(exampleBooking3)
        otherBookings.add(exampleBooking4)
        otherBookings.add(exampleBooking5)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_bookings,
                R.id.navigation_new_bookings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
