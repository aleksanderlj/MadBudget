package com.gruppe17.madbudget.rest.coop.model

import androidx.annotation.Keep
import com.beust.klaxon.Json
import com.google.firebase.firestore.PropertyName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoopProduct (
    @Json(name = "Ean")
    @get:PropertyName("Ean")
    val Ean: String = "",
    @Json(name = "Navn")
    @get:PropertyName("Navn")
    val Navn: String = "",
    @Json(name = "Navn2")
    @get:PropertyName("Navn2")
    val Navn2: String = "",
    @Json(name = "Pris")
    @get:PropertyName("Pris")
    val Pris: Double = 0.0,
    @Json(name = "VareHierakiId")
    @get:PropertyName("VareHierakiId")
    val VareHierakiId: Int = 0
)