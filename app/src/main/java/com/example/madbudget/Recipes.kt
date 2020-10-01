package com.example.madbudget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madbudget.models.Ingredient
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Recipe

class Recipes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        val recipeList: ArrayList<Recipe> = ArrayList()
        val ingredientList: ArrayList<Ingredient> = ArrayList()
        ingredientList.add( Ingredient("Rice", 2.7, 5, "Fisk"))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
    }
}