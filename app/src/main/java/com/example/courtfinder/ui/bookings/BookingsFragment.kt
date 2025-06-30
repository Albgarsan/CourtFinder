package com.example.courtfinder.ui.bookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courtfinder.MainActivity.Companion.otherBookings
import com.example.courtfinder.R
import com.example.courtfinder.JoinBookingAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class BookingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookings, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_join_bookings)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        otherBookings.sortBy { dateFormat.parse("${it.date} ${it.time}") ; it.sport }
        recyclerView.adapter = JoinBookingAdapter(otherBookings)

        return view
    }
}