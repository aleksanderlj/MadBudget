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
    @ColumnInfo(name = "ingredient_selection_parent_id") var ingredientSelectionParentId: Int,
    @ColumnInfo(name = "ingredient_price") var ingredientPrice: Double?
): Serializable {
    constructor(): this(0, "", "", "", false, 0,null)
}