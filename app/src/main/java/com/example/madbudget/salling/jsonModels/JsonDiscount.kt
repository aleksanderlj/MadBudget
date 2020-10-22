package com.example.madbudget.salling.jsonModels

class JsonDiscount(
    val clearances: List<JsonDiscountClearance>,
    val store: JsonDiscountStore,
)

class JsonDiscountStore(
    val address
)

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
    val image: String
)