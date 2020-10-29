package com.example.madbudget.models

class Recipe(
    var recipeName: String,
    var recipeId: Int,
    var recipeRating: Int,
    var recipeTimeToMake: String,
    var ingredientList: ArrayList<Ingredient>,
    var price: Int?
) {
}