package com.example.madbudget.models

import java.sql.Time

class Recipe(
    var recipeName: String,
    var recipeId: Int,
    var recipeRating: Int,
    var recipeTimeToMake: Time,
    var ingredientList: ArrayList<Ingredient>,
    var price: Int?,
    var showedDistance: Double?
) {
}