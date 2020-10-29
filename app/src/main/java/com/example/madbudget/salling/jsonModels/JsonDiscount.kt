package com.example.madbudget.salling.jsonModels

class JsonDiscount(
    val clearances: List<JsonDiscountClearance>,
    val store: JsonStore,
)
/*
class JsonDiscountStore(
    val address: JsonStoreAddress,
    val brand: String,
    val coordinates: List<Double>,
    val hours,
    val name: String,
    val id: String,
    val distance_km: Double,
    val type: String
)
 */

class JsonDiscountClearance(
    val offer: JsonDiscountOffer,
    val product: JsonDiscountProduct
)

class JsonDiscountOffer(
    val currency: String,
    val discount: Double,
    val ean: String,
    val endTime: String,
    val lastUpdate: String,
    val newPrice: Double,
    val originalPrice: Double,
    val percentDiscount: Double,
    val startTime: String,
    val stock: Int,
    val stockUnit: String
)

class JsonDiscountProduct(
    val description: String,
    val ean: String,
    val image: String? = null
)