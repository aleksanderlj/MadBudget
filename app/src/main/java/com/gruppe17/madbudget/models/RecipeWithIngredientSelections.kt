package com.gruppe17.madbudget.models

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredientSelections (
    @Embedded
    val recipe: Recipe,
    @Relation(parentColumn = "recipe_id", entityColumn = "recipe_parent_id", entity = IngredientSelection::class)
    var ingredientSelections: List<IngredientSelection>?
)