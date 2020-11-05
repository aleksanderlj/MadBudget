package com.example.madbudget.models

import androidx.room.Embedded
import androidx.room.Relation

data class IngredientSelectionWithIngredients(
    @Embedded
    val ingredientSelection: IngredientSelection,
    @Relation(parentColumn = "ingredientSelectionId", entityColumn = "ingredient_selection_parent_id", entity = Ingredient::class)
    val ingredients: List<Ingredient>
)