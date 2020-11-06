package com.example.madbudget.models

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredientSelections (
    @Embedded
    val recipe: Recipe,
    @Relation(parentColumn = "recipeId", entityColumn = "recipe_parent_id", entity = IngredientSelection::class)
    var ingredientSelections: List<IngredientSelection>?
)