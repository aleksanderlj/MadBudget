package com.gruppe17.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Recipe(
    @PrimaryKey(autoGenerate = true) val recipeId: Int,
    @ColumnInfo(name = "recipe_name") var recipeName: String,
    @ColumnInfo(name = "recipe_rating") var recipeRating: Int,
    @ColumnInfo(name = "recipe_time_to_make") var recipeTimeToMake: String,
    @ColumnInfo(name = "recipe_price") var price: Int?,
    @ColumnInfo(name = "recipe_showed_distance") var showedDistance: Double?
) {
    constructor() : this(0, "", 0, "", 0, 0.0)
}