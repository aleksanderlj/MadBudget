package com.example.madbudget

import com.example.madbudget.models.RecipeWithIngredients

interface CellClickListener {
    fun onCellClickListener(clickedRecipe : RecipeWithIngredients)
}