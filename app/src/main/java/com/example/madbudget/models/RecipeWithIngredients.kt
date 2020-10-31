package com.example.madbudget.models

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients (
    @Embedded
    val recipe: Recipe,
    @Relation(parentColumn = "recipeId", entityColumn = "recipe_parent_id", entity = Ingredient::class)
    val ingredients: List<Ingredient>
)