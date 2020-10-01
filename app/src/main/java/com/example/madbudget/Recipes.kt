package com.example.madbudget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madbudget.models.Ingredient
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Recipe
import kotlinx.android.synthetic.main.activity_recipes.*

class Recipes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        val recipeList: ArrayList<Recipe> = ArrayList()
        val ingredientList: ArrayList<Ingredient> = ArrayList()
        ingredientList.add( Ingredient("Rice", 2.7, 5, "Fisk"))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("jdsakflksdaflksa", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Fisk", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))
        recipeList.add(Recipe("Hej", "42069", 10, "2h", ingredientList))

        recipe_list.adapter = RecipesAdapter(recipeList)
        recipe_list.layoutManager = LinearLayoutManager(this)
        recipe_list.setHasFixedSize(true)

    }
}