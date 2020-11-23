package com.gruppe17.madbudget.rest.coop.model

import com.beust.klaxon.Json

class CoopStore(
    @Json(name = "K")
    val K: Int, // Kardex
    @Json(name = "R")
    val R: String, // retailGroup
    @Json(name = "N")
    val N: String,  // Name (useless)
    @Json(name = "A")
    val A: String, //address
    @Json(name = "Z")
    val Z: Int, //zipcode
    @Json(name = "C")
    val C: String, //city
    @Json(name = "S")
    val S: Int, //storeId
    @Json(name = "L")
    val L: CoopLocation, //location
    @Json(name = "O")
    val O: List<CoopOpeningHour> //openingHours
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