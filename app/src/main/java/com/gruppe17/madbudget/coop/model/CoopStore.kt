package com.gruppe17.madbudget.coop.model

import com.beust.klaxon.Json

class CoopStore(
    @Json(name = "K")
    val kardex: Int,
    @Json(name = "R")
    val retailGroup: String,
    @Json(name = "N")
    val name: String,  // Useless
    @Json(name = "A")
    val address: String,
    @Json(name = "Z")
    val zipcode: Int,
    @Json(name = "C")
    val city: String,
    @Json(name = "S")
    val storeId: Int,
    @Json(name = "L")
    val location: CoopLocation,
    @Json(name = "O")
    val openingHours: List<CoopOpeningHour>
)

class CoopLocation (
    val coordinates: List<Double>
)

class CoopOpeningHour(
    @Json(name = "Text")
    val text: String,
    @Json(name = "Day")
    val day: String,
    @Json(name = "From")
    val from: Double, // Useless
    @Json(name = "To")
    val to: Double, // Useless
    @Json(name = "FromDate")
    val fromDate: String,
    @Json(name = "ToDate")
    val toDate: String
)