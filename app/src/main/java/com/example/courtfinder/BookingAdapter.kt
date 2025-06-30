package com.example.courtfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.courtfinder.MainActivity.Companion.otherBookings
import com.example.courtfinder.MainActivity.Companion.profile

class BookingAdapter(private val bookings: MutableList<Booking>) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtSport: TextView = itemView.findViewById(R.id.txtSport)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtLevel: TextView = itemView.findViewById(R.id.txtLevel)
        val txtCourt: TextView = itemView.findViewById(R.id.txtCourt)
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
        val txtPartner: TextView = itemView.findViewById(R.id.txtPartner)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.booking_item, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.txtSport.text = booking.sport
        holder.txtName.text = booking.profile.name + " " + booking.profile.surname
        holder.txtLevel.text = booking.profile.level
        holder.txtCourt.text = booking.court
        holder.txtDate.text = booking.date
        holder.txtTime.text = booking.time
        if (booking.profile.name == profile.name && booking.profile.surname == profile.surname) {
            holder.txtPartner.text = if (booking.lookingForPartner) "Looking for a partner" else "Not looking for a partner"
        }
        holder.buttonDelete.setOnClickListener {
            if (booking.profile.name != profile.name && booking.profile.surname != profile.surname) {
                otherBookings.add(booking)
            }
            bookings.removeAt(position)
            notifyItemRemoved(position)
            Toast.makeText(holder.itemView.context, "Booking Deleted", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount() = bookings.size
}
