package com.example.madbudget

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.madbudget.models.Ingredient
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madbudget.models.Recipe
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipes.*
import java.sql.Time

class Recipes : AppCompatActivity(), CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        //TODO - Call database here get recipes and their ingredients!!!
        val recipeList = iniDummyRecipes()
        calculatePrices(recipeList)
        searchOnChange(recipeList)
        recipe_list.adapter = RecipesAdapter(recipeList, this)
        recipe_list.layoutManager = LinearLayoutManager(this)
        recipe_list.setHasFixedSize(true)
    }

    //TODO - Fix when you can you dingus!
    fun calculatePrices(recipeList: ArrayList<Recipe>){
        for (i in 0..recipeList.size - 1){
            if (recipeList[i].price != null) {
                //TODO - Calculate that shit!
            }
        }
    }

    override fun onCellClickListener(clickedRecipe: Recipe) {
        val recipeActivity = Intent(this, RecipeActivity::class.java)
        val gson = Gson()
        val stringRecipe = gson.toJson(clickedRecipe)
        recipeActivity.putExtra("ClickedRecipe", stringRecipe)
        startActivity(recipeActivity)
    }

    private fun searchOnChange(recipeList: ArrayList<Recipe>){
        search_text.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchedText = search_text.text.toString()
                val searchedRecipeList: ArrayList<Recipe> = ArrayList()
                var sameString = false
                val updatedAdapter: RecipesAdapter = recipe_list.adapter as RecipesAdapter

                if (search_text.text.isEmpty()){
                    updatedAdapter.updateResource(recipeList)
                    recipe_list.adapter = updatedAdapter
                }
                else {
                    for (i in 0..recipeList.size - 1){
                        for (j in 0..searchedText.length - 1)
                            if (recipeList[i].recipeName.length < searchedText.length)
                                sameString = false
                            else
                                sameString = searchedText[j].toLowerCase() == recipeList[i].recipeName[j].toLowerCase()
                        if (sameString)
                            searchedRecipeList.add(recipeList[i])
                    }
                    updatedAdapter.updateResource(searchedRecipeList)
                    recipe_list.adapter = updatedAdapter
                }
            }
        })
    }

    private fun iniDummyRecipes() : ArrayList<Recipe>{
        val recipeList: ArrayList<Recipe> = ArrayList()
        val ingredientList: ArrayList<Ingredient> = ArrayList()
        ingredientList.add(Ingredient("Rice", "2.7", 5, "Fisk"))
        ingredientList.add(Ingredient("Rice", "2.7", 5, "Fisk"))
        ingredientList.add(Ingredient("Rice", "2.7", 5, "Fisk"))
        recipeList.add(Recipe("Hej", 42069, 5, Time(1,30, 0), ingredientList, 60, 2.0))
        recipeList.add(Recipe("Spaghetti Bolognese", 42069, 5, Time(1,30, 0), ingredientList, 60, 1.2))
        recipeList.add(Recipe("Fisk", 42069, 5, Time(1,30, 0), ingredientList, 60, 0.3))
        recipeList.add(Recipe("Hej", 42069, 5, Time(1,30, 0), ingredientList, 60, 4.5))
        recipeList.add(Recipe("Hej", 42069, 5, Time(1,30, 0), ingredientList, 200, 3.2))
        recipeList.add(Recipe("Heje", 42069, 5, Time(1,30, 0), ingredientList, 60, 5.1))
        return recipeList
    }
}