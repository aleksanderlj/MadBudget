package com.gruppe17.madbudget.recyclerviews

import com.gruppe17.madbudget.models.RecipeWithIngredientSelections

interface CellClickListener {
    fun onCellClickListener(clickedRecipe : RecipeWithIngredientSelections)
}