package com.gruppe17.madbudget.rest.salling.model

class JsonStore(
    val address: JsonStoreAddress,
    val brand: String,
    val coordinates: List<Double>,
    val distance_km: Double,
    val name: String,
    val id: String,
    val type: String
)

class JsonStoreAddress(
    val city: String,
    val country: String,
    val extra: String? = null,
    val street: String,
    val zip: String
)