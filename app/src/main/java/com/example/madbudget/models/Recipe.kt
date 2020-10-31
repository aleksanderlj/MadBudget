package com.example.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO Forkort navne? "recipeName" kan bare være "name" fx.

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val recipeId: Int,
    @ColumnInfo(name = "recipe_name") var recipeName: String,
    @ColumnInfo(name = "recipe_rating") var recipeRating: Int,
    @ColumnInfo(name = "recipe_time_to_make") var recipeTimeToMake: String,
) {
    constructor() : this(0, "", 0, "")
}