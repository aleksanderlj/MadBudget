package com.example.madbudget

import com.example.madbudget.models.RecipeWithIngredientSelections

interface CellClickListener {
    fun onCellClickListener(clickedRecipe : RecipeWithIngredientSelections)
}