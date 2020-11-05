package com.example.madbudget

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Recipe
import com.example.madbudget.models.RecipeWithIngredients
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        val intent = getIntent()
        val recipeId2 = intent.getStringExtra("ClickedRecipe")
        val recipeId = intent.getIntExtra("ClickedRecipe", 0)
        val db = DatabaseBuilder.get(this)
        GlobalScope.launch {
            val recipe: RecipeWithIngredients? = db.recipeDao().getById(recipeId)
            if (recipe != null) {
                recipe_name.text = recipe.recipe.recipeName
                recipe_rating.text = "Rating: " + recipe.recipe.recipeRating + "/5"
                recipe_time.text = "Time: " + recipe.recipe.recipeTimeToMake
                recipe_ingredient_list.adapter = RecipeIngredientsAdapter(recipe.ingredients)
                recipe_ingredient_list.layoutManager = LinearLayoutManager(baseContext)
                recipe_ingredient_list.setHasFixedSize(true)
            }
        }
    }
}