package com.example.madbudget.salling.jsonModels

class JsonStore(
    val address: JsonStoreAddress,
    val brand: String,
    val coordinates: List<Double>,
    val distance_km: Double,
    val name: String,
    val id: String,
    val type: String
)