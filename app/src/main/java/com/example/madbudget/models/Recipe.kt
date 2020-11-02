package com.example.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity
class Recipe(
    @PrimaryKey(autoGenerate = true) val recipeId: Int,
    @ColumnInfo(name = "recipe_name") var recipeName: String,
    @ColumnInfo(name = "recipe_rating") var recipeRating: Int,
    @ColumnInfo(name = "recipe_time_to_make") var recipeTimeToMake: Time,
    @ColumnInfo(name = "recipe_price") var price: Int?,
    @ColumnInfo(name = "recipe_showed_distance") var showedDistance: Double?
) {
    constructor() : this(0, "", 0, Time(0,0,0), 0, 0.0)
}