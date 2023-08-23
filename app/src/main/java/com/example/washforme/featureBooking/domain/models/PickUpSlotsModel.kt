package com.example.washforme.featureBooking.domain.models

data class PickUpSlotsModel(
    var date: String,
    var timeslots: List<Timeslot>
)