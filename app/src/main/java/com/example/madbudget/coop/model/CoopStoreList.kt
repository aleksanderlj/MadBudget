package com.example.madbudget.coop.model

import com.beust.klaxon.Json
import com.example.madbudget.salling.jsonModels.JsonStoreAddress

class CoopStoreList(
    @Json(name = "CurrentPage")
    val currentPage: Int,
    @Json(name = "NextPage")
    val nextPage: Int,
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