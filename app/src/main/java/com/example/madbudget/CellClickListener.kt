package com.example.madbudget

import com.example.madbudget.models.Recipe

interface CellClickListener {
    fun onCellClickListener(clickedRecipe : Recipe)
}