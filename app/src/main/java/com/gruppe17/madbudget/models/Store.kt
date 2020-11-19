package com.gruppe17.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Store (
    @ColumnInfo(name = "store_id")
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "store_name") var name: String,
    @ColumnInfo(name = "store_kardex") var kardex: Int,
    @ColumnInfo(name = "store_address") var address: String,
    @ColumnInfo(name = "coop_store_id") var coopStoreId: Int,
    @ColumnInfo(name = "is_selected") var isSelected: Boolean,
    var distance: Float?
)
{
        constructor() : this(0, "", 0, "", 0, true,null)
}

