package com.example.washforme.featureBooking.domain.models

import com.google.gson.annotations.SerializedName

data class PickUpSlots (
    var available: Boolean?,
    @SerializedName("available_quota")
    var availableQuota: Int?,
    var date: String?,
    @SerializedName("end_time")
    var endTime: String?,
    @SerializedName("filled_quota")
    var filledQuota: Int?,
    var id: Int?,
    @SerializedName("schedule_completed")
    var scheduleCompleted: Boolean?,
    @SerializedName("start_time")
    var startTime: String?
)