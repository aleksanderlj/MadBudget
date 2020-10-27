package com.example.madbudget.models

import java.io.Serializable

class Ingredient(
    var ingredientName: String?,
    var amount:String?,
    var ingredientId: Int?,
    var ingredientType: String?,
    var hasBeenClicked: Boolean = false,
    var ingredientPrice: Double?
): Serializable {
    constructor() : this("","",null,"",false,null)

}