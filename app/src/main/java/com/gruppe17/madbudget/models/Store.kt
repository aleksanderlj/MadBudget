package com.gruppe17.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Store (
    @PrimaryKey(autoGenerate = true) val storeId: Int,
    @ColumnInfo(name = "store_name") var storeName: String,
    @ColumnInfo(name = "store_kardex") var storeKardex: Int,
    @ColumnInfo(name = "store_address") var storeAddress: String,
    @ColumnInfo(name = "coop_store_id") var coopStoreId: Int,
    @ColumnInfo(name = "isSelected") var isSelected: Boolean,
    var distance: Float?
)
{
        constructor() : this(0, "", 0, "", 0, true,null)
}

