package com.gruppe17.madbudget.rest.coop.model

import com.beust.klaxon.Json
import com.google.firebase.firestore.PropertyName

class CoopStoreList(
    @Json(name = "CurrentPage")
    @get:PropertyName("CurrentPage")
    val CurrentPage: Int,
    @Json(name = "PageCount")
    @get:PropertyName("PageCount")
    val PageCount: Int,
    @Json(name = "PageSize")
    @get:PropertyName("PageSize")
    val PageSize: Int,
    @Json(name = "TotalPagedItemsCount")
    @get:PropertyName("TotalPagedItemsCount")
    val TotalPagedItemsCount: Int,
    @Json(name = "Data")
    @get:PropertyName("Data")
    val Data: List<CoopStore>, // Stores
    @Json(name = "Status")
    @get:PropertyName("Status")
    val Status: Int,
    @Json(name = "Message")
    @get:PropertyName("Message")
    val Message: String
)