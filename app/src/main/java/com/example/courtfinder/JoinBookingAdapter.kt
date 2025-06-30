package com.example.courtfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.courtfinder.MainActivity.Companion.myBookings
import com.example.courtfinder.MainActivity.Companion.profile

class JoinBookingAdapter(private val bookings: MutableList<Booking>) :
    RecyclerView.Adapter<JoinBookingAdapter.JoinBookingViewHolder>() {

    class JoinBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtSport: TextView = itemView.findViewById(R.id.text_sport)
        val txtName: TextView = itemView.findViewById(R.id.text_name)
        val txtLevel: TextView = itemView.findViewById(R.id.text_level)
        val txtCourt: TextView = itemView.findViewById(R.id.text_court)
        val txtDate: TextView = itemView.findViewById(R.id.text_date)
        val txtTime: TextView = itemView.findViewById(R.id.text_time)
        val buttonJoin: Button = itemView.findViewById(R.id.buttonJoin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinBookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.join_booking_item, parent, false)
        return JoinBookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: JoinBookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.txtSport.text = booking.sport
        holder.txtCourt.text = booking.court
        holder.txtDate.text = booking.date
        holder.txtTime.text = booking.time
        holder.txtName.text = booking.profile.name + " " + booking.profile.surname
        holder.txtLevel.text = booking.profile.level

        holder.buttonJoin.setOnClickListener {
            if (myBookings.any { it.date == booking.date && it.time == booking.time && it.court == booking.court && it.sport == booking.sport }) {
                Toast.makeText(holder.itemView.context, "You already have this booking", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (profile.name.isEmpty() || profile.surname.isEmpty() || profile.level.isEmpty()) {
                Toast.makeText(holder.itemView.context, "Please sign in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else{
                myBookings.add(booking)
                bookings.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(holder.itemView.context, "Joined Booking Successfully", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun getItemCount() = bookings.size
}