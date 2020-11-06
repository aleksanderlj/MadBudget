package com.example.madbudget

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.madbudget.models.Ingredient
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Recipe
import com.example.madbudget.models.RecipeWithIngredientSelections
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Recipes : AppCompatActivity(), CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        val db = DatabaseBuilder.get(this)
        val context = this
        //TODO - Call database here get recipes and their ingredients!!!
        iniDummyRecipes()
        GlobalScope.launch {
            val recipeList = db.recipeDao().getAll()
            runOnUiThread {
                calculatePrices(recipeList)
                searchOnChange(recipeList)
                recipe_list.adapter = RecipesAdapter(recipeList, context)
                recipe_list.layoutManager = LinearLayoutManager(context)
                recipe_list.setHasFixedSize(true)
            }
        }
    }

    //TODO - Fix when you can you dingus!
    fun calculatePrices(recipeList: List<RecipeWithIngredientSelections>){
        for (i in 0..recipeList.size - 1){
            if (recipeList[i].recipe.price != null) {
                //TODO - Calculate that shit!
            }
        }
    }

    override fun onCellClickListener(clickedRecipe: RecipeWithIngredientSelections) {
        val recipeActivity = Intent(this, CreateRecipeActivity::class.java)
        recipeActivity.putExtra("ClickedRecipe", clickedRecipe.recipe.recipeId)
        startActivity(recipeActivity)
    }

    private fun searchOnChange(recipeList: List<RecipeWithIngredientSelections>){
        search_text.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchedText = search_text.text.toString()
                val searchedRecipeList: ArrayList<RecipeWithIngredientSelections> = ArrayList()
                var sameString = false
                val updatedAdapter: RecipesAdapter = recipe_list.adapter as RecipesAdapter

                if (search_text.text.isEmpty()){
                    updatedAdapter.updateResource(recipeList)
                    recipe_list.adapter = updatedAdapter
                }
                else {
                    for (i in 0..recipeList.size - 1){
                        for (j in 0..searchedText.length - 1)
                            if (recipeList[i].recipe.recipeName.length < searchedText.length)
                                sameString = false
                            else
                                sameString = searchedText[j].toLowerCase() == recipeList[i].recipe.recipeName[j].toLowerCase()
                        if (sameString)
                            searchedRecipeList.add(recipeList[i])
                    }
                    updatedAdapter.updateResource(searchedRecipeList)
                    recipe_list.adapter = updatedAdapter
                }
            }
        })
    }

    private fun iniDummyRecipes(){
        val recipeList: ArrayList<Recipe> = ArrayList()
        val ingredientList: ArrayList<Ingredient> = ArrayList()
        recipeList.add(Recipe(0, "Hej", 5, "1h 30m", 60, 2.0))
        recipeList.add(Recipe(0, "Spaghetti Bolognese", 5, "1h 30m", 60, 1.2))
        recipeList.add(Recipe(0, "Fisk", 5, "1h 30m", 60, 0.3))
        recipeList.add(Recipe(0, "Hej", 5, "1h 30m", 60, 4.5))
        recipeList.add(Recipe(0, "Hej", 5, "1h 30m", 200, 3.2))
        recipeList.add(Recipe(0, "Heje", 5, "1h 30m", 60, 5.1))

        val db = DatabaseBuilder.get(this)
        GlobalScope.launch {
            db.recipeDao().insertAll(recipeList)
            val test = db.recipeDao().getAll()[0].recipe.recipeId

            ingredientList.add(Ingredient(0, "Rice", "2.7", "Fisk", false, test))
            ingredientList.add(Ingredient(0, "Rice", "2.7", "Fisk", false, test))
            ingredientList.add(Ingredient(0, "Rice", "2.7", "Fisk", false, test))
            db.ingredientDao().insertAll(ingredientList)
        }
    }
}