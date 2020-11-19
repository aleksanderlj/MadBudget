package com.gruppe17.madbudget.rest.coop.model

import androidx.annotation.Keep
import com.beust.klaxon.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoopProduct (
    @Json(name = "Ean")
    val ean: String,
    @Json(name = "Navn")
    val name1: String,
    @Json(name = "Navn2")
    val name2: String,
    @Json(name = "Pris")
    val price: Double,
    @Json(name = "VareHierakiId")
    val productHierarchyId: Int
)