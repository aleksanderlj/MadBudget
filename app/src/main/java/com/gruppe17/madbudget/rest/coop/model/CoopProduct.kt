package com.gruppe17.madbudget.rest.coop.model

import androidx.annotation.Keep
import com.beust.klaxon.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoopProduct (
    @Json(name = "Ean")
    val Ean: String,
    @Json(name = "Navn")
    val Navn: String,
    @Json(name = "Navn2")
    val Navn2: String,
    @Json(name = "Pris")
    val Pris: Double,
    @Json(name = "VareHierakiId")
    val VareHierakiId: Int
)