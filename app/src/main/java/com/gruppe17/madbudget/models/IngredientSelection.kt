package com.gruppe17.madbudget.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientSelection (
    @ColumnInfo(name = "ingredient_selection_id")
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "ingredient_selection_name") var name: String?,
    @ColumnInfo(name = "ingredient_selection_amount") var amount: Double?,
    @ColumnInfo(name = "ingredient_selection_unit") var unit: String?,
    @ColumnInfo(name = "ingredient_selection_is_selected") var isSelected: Boolean = true,
    @ColumnInfo(name = "recipe_parent_id") var recipeParentId: Int
    )
{
    constructor() : this(0, "", 0.0, "", false, 0)

    override fun toString(): String {
        return name+" "+amount+" " + unit + " " +isSelected.toString()
    }
}
