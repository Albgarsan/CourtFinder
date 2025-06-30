package com.example.courtfinder.ui.newBookings

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.courtfinder.Booking
import com.example.courtfinder.MainActivity.Companion.myBookings
import com.example.courtfinder.MainActivity.Companion.profile
import com.example.courtfinder.R
import java.util.Calendar
import kotlin.text.*

class NewBookingsFragment : Fragment() {

    private val dateTimeNow = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_bookings, container, false)

        //Spinner for Sport
        val spinnerSport: Spinner = view.findViewById(R.id.spinner_sport)
        val sports = listOf("Select a sport", "Tennis", "Padel")
        val sportAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, sports)
        sportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSport.adapter = sportAdapter

        val mainLayout = view.findViewById<LinearLayout>(R.id.new_bookings_layout)

        spinnerSport.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                val selectedSport = sports[position]
                when (selectedSport) {
                    "Select a sport" -> mainLayout.setBackgroundResource(0)
                    "Tennis" -> mainLayout.setBackgroundResource(R.drawable.tennis_court_background)
                    "Padel" -> mainLayout.setBackgroundResource(R.drawable.padel_court_background)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //Spinner for Court
        val spinnerCourt: Spinner = view.findViewById(R.id.spinner_court)
        val courts = listOf("Select a court", "Court 1", "Court 2", "Court 3")
        val courtAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courts)
        courtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCourt.adapter = courtAdapter

        //Date Picker
        val dateField = view.findViewById<EditText>(R.id.edit_date)
        dateField.showSoftInputOnFocus = false

        dateField.setOnClickListener {
            showDatePicker(dateField)
        }

        //Spinner for Time
        val spinnerTime: Spinner = view.findViewById(R.id.spinner_time)
        val initialTimes = listOf("Select a time")
        val timeAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, initialTimes)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTime.adapter = timeAdapter

        //Switch for Partner
        val switchPartner = view.findViewById<Switch>(R.id.switch_partner)

        //Button to save
        view.findViewById<ImageButton>(R.id.btn_save).setOnClickListener {
            val sport = spinnerSport.selectedItem.toString()
            val court = spinnerCourt.selectedItem.toString()
            val date = dateField.text.toString()
            val time = spinnerTime.selectedItem.toString()
            val lookingForPartner = switchPartner.isChecked

            if (spinnerSport.selectedItemPosition == 0) {
                Toast.makeText(requireContext(), "Please select a sport", Toast.LENGTH_SHORT).show()
            } else if (spinnerCourt.selectedItemPosition == 0) {
                Toast.makeText(requireContext(), "Please select a court", Toast.LENGTH_SHORT).show()
            } else if (dateField.text.isEmpty()) {
                Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show()
            } else if (spinnerTime.selectedItemPosition == 0) {
                Toast.makeText(requireContext(), "Please select a time", Toast.LENGTH_SHORT).show()
            } else if (profile.name.isEmpty() || profile.surname.isEmpty() || profile.level.isEmpty()) {
                Toast.makeText(requireContext(), "Please sign in", Toast.LENGTH_SHORT).show()
            }
            else {
                val alreadyExists = myBookings.any {
                    it.date == date && it.time == time && it.court == court && it.sport == sport
                }
                if (alreadyExists) {
                    Toast.makeText(requireContext(),"You already have this booking",Toast.LENGTH_LONG).show()
                } else {
                    val booking = Booking(sport, court, date, time, lookingForPartner, profile)
                    myBookings.add(booking)
                    Toast.makeText(requireContext(), "Booking Saved", Toast.LENGTH_LONG).show()
                    spinnerSport.setSelection(0)
                    spinnerCourt.setSelection(0)
                    dateField.setText("")
                    timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerTime.adapter = timeAdapter
                    switchPartner.isChecked = false
                }
            }
        }
        return view
    }

    private val currentHour = dateTimeNow.get(Calendar.HOUR_OF_DAY)
    private val currentMinute = dateTimeNow.get(Calendar.MINUTE)
    private val currentDay = dateTimeNow.get(Calendar.DAY_OF_MONTH)
    private val currentMonth = dateTimeNow.get(Calendar.MONTH)
    private val currentYear = dateTimeNow.get(Calendar.YEAR)
    private val currentDate = String.format("%02d/%02d/%04d", currentDay, currentMonth, currentYear)

    private fun showDatePicker(dateField: EditText) {
        val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            dateField.setText(selectedDate)

            val spinnerTime: Spinner = requireView().findViewById(R.id.spinner_time)
            val hours = hoursGenerator(selectedDate)
            val timeAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, hours)
            timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTime.adapter = timeAdapter

        }, currentYear, currentMonth, currentDay)

        datePicker.datePicker.minDate = Calendar.getInstance().timeInMillis
        dateTimeNow.add(Calendar.MONTH, 1)
        datePicker.datePicker.maxDate = dateTimeNow.timeInMillis
        datePicker.show()
    }


    private fun hoursGenerator(selectedDate: String): MutableList<String> {
        val res = mutableListOf<String>()
        res.add("Select a time")

        val spinnerCourt: Spinner = requireView().findViewById(R.id.spinner_court)
        val selectedCourt = spinnerCourt.selectedItem?.toString() ?: ""
        val spinnerSport: Spinner = requireView().findViewById(R.id.spinner_sport)
        val selectedSport = spinnerSport.selectedItem?.toString() ?: ""
        var hour = 9
        var minute = 0

        while (hour < 22) {
            val time = String.format("%02d:%02d", hour, minute)
            val isAlreadyBooked = myBookings.any {
                it.date == selectedDate && it.time == time && it.court == selectedCourt && it.sport == selectedSport
            }
            if (!isAlreadyBooked) {
                if (selectedDate == currentDate) {
                    if (hour > currentHour || (hour == currentHour && minute >= currentMinute)) {
                        res.add(time)
                    }
                } else {
                    res.add(time)
                }
            }
            if (minute == 0) {
                hour++
                minute += 30
            } else {
                hour += 2
                minute = 0
            }
        }

        return res
    }
}