package com.gruppe17.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Recipe(
    @ColumnInfo(name = "recipe_id")
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "recipe_name") var name: String,
    @ColumnInfo(name = "recipe_time_to_make") var timeToMake: String,
    @ColumnInfo(name = "recipe_price") var price: Double?,
    @ColumnInfo(name = "recipe_showed_distance") var showedDistance: Double?
) {
    constructor() : this(0, "", "", 0.0, 0.0)
}