package com.example.madbudget.models

class Recipe(
    var recipeName: String,
    var recipeId: String,
    var recipeRating: Int,
    var recipeTimeToMake: String,
    var ingredientList: List<Ingredient>
) {

}