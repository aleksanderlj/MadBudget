package com.gruppe17.madbudget.coop.model

import com.beust.klaxon.Json

class CoopProduct (
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