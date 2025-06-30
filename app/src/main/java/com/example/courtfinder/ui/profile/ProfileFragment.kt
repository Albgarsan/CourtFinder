package com.example.courtfinder.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courtfinder.MainActivity.Companion.myBookings
import com.example.courtfinder.R
import com.example.courtfinder.BookingAdapter
import com.example.courtfinder.MainActivity.Companion.profile
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //Edit text for name
        val editName = view.findViewById<EditText>(R.id.edit_name)

        //Edit text for surname
        val editSurname = view.findViewById<EditText>(R.id.edit_surname)

        //Spinner for level
        val spinnerLevel: Spinner = view.findViewById(R.id.spinnerLevel)
        val levels = listOf("Select a level", "Beginner", "Intermediate", "Advanced")
        val levelAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, levels)
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLevel.adapter = levelAdapter

        //Save button
        val saveButton = view.findViewById<Button>(R.id.buttonSave)
        saveButton.setOnClickListener {
            if(editName.text.toString().isEmpty() || editSurname.text.toString().isEmpty() || spinnerLevel.selectedItem.toString() == "Select a level") {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else{
                val name = editName.text.toString()
                val surname = editSurname.text.toString()
                val selectedLevel = spinnerLevel.selectedItem.toString()
                profile.name = name
                profile.surname = surname
                profile.level = selectedLevel
                Toast.makeText(requireContext(), "Profile Saved", Toast.LENGTH_SHORT).show()
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_bookings)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        myBookings.sortBy { dateFormat.parse("${it.date} ${it.time}") ; it.sport }
        recyclerView.adapter = BookingAdapter(myBookings)

        return view
    }
}
