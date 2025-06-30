package com.example.courtfinder

data class Booking(
    val sport: String,
    val court: String,
    val date: String,
    val time: String,
    val lookingForPartner: Boolean,
    val profile: Profile
)
