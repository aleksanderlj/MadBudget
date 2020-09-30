package com.example.madbudget.models

class Ingredient(
    var ingredientName: String,
    var amount: Double?,
    var ingredientId: Int?,
    var ingredientType: String
) {
    constructor() : this("",null,null,"")

}