package com.example.madbudget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madbudget.models.Ingredient
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.CellClickListener
import com.example.madbudget.models.Recipe
import kotlinx.android.synthetic.main.activity_recipe.*

class RecipeActivity : AppCompatActivity(), CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipeList: ArrayList<Recipe> = ArrayList()
        val ingredientList: ArrayList<Ingredient> = ArrayList()
        ingredientList.add(Ingredient("Rice", "2.7", 5, "Fisk"))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("spaghetti bolognese", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Fisk", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))
        recipeList.add(Recipe("Hej", 42069, 5, "2h", ingredientList))

        recipe_list.adapter = RecipesAdapter(recipeList, this)
        recipe_list.layoutManager = LinearLayoutManager(this)
        recipe_list.setHasFixedSize(true)
    }

    override fun onCellClickListener(clickedRecipe: Recipe) {
        val recipeActivity = Intent(this, RecipeActivity::class.java)
        startActivity(recipeActivity)
    }
}