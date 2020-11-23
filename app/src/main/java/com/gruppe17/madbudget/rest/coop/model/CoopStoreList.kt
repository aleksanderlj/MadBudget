package com.gruppe17.madbudget.rest.coop.model

import com.beust.klaxon.Json

class CoopStoreList(
    @Json(name = "CurrentPage")
    val CurrentPage: Int,
    @Json(name = "PageCount")
    val PageCount: Int,
    @Json(name = "PageSize")
    val PageSize: Int,
    @Json(name = "TotalPagedItemsCount")
    val TotalPagedItemsCount: Int,
    @Json(name = "Data")
    val Data: List<CoopStore>, // Stores
    @Json(name = "Status")
    val Status: Int,
    @Json(name = "Message")
    val Message: String
)