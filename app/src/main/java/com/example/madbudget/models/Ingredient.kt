package com.example.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int,
    @ColumnInfo(name = "ingredient_name") var ingredientName: String?,
    @ColumnInfo(name = "amount")var amount: String?,
    @ColumnInfo(name = "ingredient_type") var ingredientType: String?,
    @ColumnInfo(name = "has_been_clicked") var hasBeenClicked: Boolean = false,
    @ColumnInfo(name = "recipe_parent_id") var recipeParentId: Int
): Serializable {
    constructor(): this(0, "", "", "", false, 0)
}