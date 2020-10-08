package com.example.madbudget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madbudget.models.Recipe
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipe.*

class RecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        val intent = getIntent()
        val stringRecipe = intent.getStringExtra("ClickedRecipe")
        val gson = Gson()
        val recipe = gson.fromJson(stringRecipe, Recipe::class.java)

        recipe_name.text = recipe.recipeName
        recipe_rating.text = "Rating: " + recipe.recipeRating + "/5"
        recipe_time.text = "Time: " + recipe.recipeTimeToMake
    }
}