package com.example.madbudget.models

class IngredientSelection (
    var ingredientSelectionName: String?,
    var ingredientSelectionAmount: String?,
    var ingredientList: ArrayList<Ingredient>,
    var isSelected: Boolean = true

    )
{

    override fun toString(): String {
        return ingredientSelectionName+" "+ingredientSelectionAmount+" "+isSelected.toString()
    }

}
