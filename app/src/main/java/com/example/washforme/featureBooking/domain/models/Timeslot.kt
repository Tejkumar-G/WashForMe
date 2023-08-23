package com.example.washforme.featureBooking.domain.models

data class Timeslot(
    var delivery_available_quota: Int,
    var end_datetime: String,
    var id: Long,
    var pickup_available_quota: Int,
    var shop: Int,
    var start_datetime: String
)