package com.gruppe17.madbudget.rest.coop.model

import com.beust.klaxon.Json

class CoopStoreList(
    @Json(name = "CurrentPage")
    val currentPage: Int,
    @Json(name = "PageCount")
    val pageCount: Int,
    @Json(name = "PageSize")
    val pageSize: Int,
    @Json(name = "TotalPagedItemsCount")
    val totalPagedItemsCount: Int,
    @Json(name = "Data")
    val stores: List<CoopStore>,
    @Json(name = "Status")
    val status: Int,
    @Json(name = "Message")
    val message: String
)