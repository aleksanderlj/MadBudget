package com.gruppe17.madbudget.rest.coop.model

import com.beust.klaxon.Json
import com.google.firebase.firestore.PropertyName

class CoopStore(
    @Json(name = "K")
    @get:PropertyName("K")
    val K: Int, // Kardex
    @Json(name = "R")
    @get:PropertyName("R")
    val R: String, // retailGroup
    @Json(name = "N")
    @get:PropertyName("N")
    val N: String,  // Name (useless)
    @Json(name = "A")
    @get:PropertyName("A")
    val A: String, //address
    @Json(name = "Z")
    @get:PropertyName("Z")
    val Z: Int, //zipcode
    @Json(name = "C")
    @get:PropertyName("C")
    val C: String, //city
    @Json(name = "S")
    @get:PropertyName("S")
    val S: Int, //storeId
    @Json(name = "L")
    @get:PropertyName("L")
    val L: CoopLocation, //location
    @Json(name = "O")
    @get:PropertyName("O")
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