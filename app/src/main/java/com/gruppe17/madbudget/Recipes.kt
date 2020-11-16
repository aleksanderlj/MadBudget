package com.gruppe17.madbudget

import SwipeHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.gruppe17.madbudget.models.Ingredient
import androidx.recyclerview.widget.LinearLayoutManager
import com.gruppe17.madbudget.models.Recipe
import com.gruppe17.madbudget.models.RecipeWithIngredientSelections
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Recipes : AppCompatActivity(), CellClickListener {

    private lateinit var db: AppDatabase
    private lateinit var recipeList: ArrayList<RecipeWithIngredientSelections>
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        db = DatabaseBuilder.get(this)
        context = this

        //TODO - Call database here get recipes and their ingredients!!!
        //iniDummyRecipes()

        new_recipe_button.setOnClickListener {
            val recipeActivity = Intent(context, CreateRecipeActivity::class.java)
            recipeActivity.putExtra("ClickedRecipe", -1)
            startActivity(recipeActivity)
            /*
            var newRecipeId: Int
            db = DatabaseBuilder.get(this)
            GlobalScope.launch {
                newRecipeId = db.recipeDao().insert(Recipe()).toInt()
                runOnUiThread{
                    val recipeActivity = Intent(context, CreateRecipeActivity::class.java)
                    recipeActivity.putExtra("ClickedRecipe", newRecipeId)
                    startActivity(recipeActivity)
                }
            }

             */
        }

        setupRecyclerView()

    }

    //TODO - Fix when you can you dingus!
    fun calculatePrices(recipeList: List<RecipeWithIngredientSelections>){
        for (i in 0..recipeList.size - 1){
            if (recipeList[i].recipe.price != null) {
                //TODO - Calculate that shit!
            }
        }
    }

    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            this,
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() { deleteRecipe(position) }
            })
    }

    private fun deleteRecipe(position: Int){

        GlobalScope.launch {
            val recipeWithIngredientSelections =
                db.recipeDao().getById(recipeList[position].recipe.recipeId)

            for (i in recipeWithIngredientSelections?.ingredientSelections!!) {
                db.ingredientDao().deleteByParentId(i.ingredientSelectionId)
            }

            db.ingredientSelectionDao().deleteByParentId(recipeList[position].recipe.recipeId)
            db.recipeDao().delete(recipeList[position].recipe)

            runOnUiThread {
                recipeList.removeAt(position)
                recipe_list.adapter?.notifyDataSetChanged()
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
        recipeList.add(Recipe(0, "Hej", "1h 30m", 60.0, 2.0))
        recipeList.add(Recipe(0, "Spaghetti Bolognese", "1h 30m", 60.0, 1.2))
        recipeList.add(Recipe(0, "Fisk", "1h 30m", 60.0, 0.3))
        recipeList.add(Recipe(0, "Hej", "1h 30m", 60.0, 4.5))
        recipeList.add(Recipe(0, "Hej", "1h 30m", 200.0, 3.2))
        recipeList.add(Recipe(0, "Heje", "1h 30m", 60.0, 5.1))

        val db = DatabaseBuilder.get(this)
        GlobalScope.launch {
            db.recipeDao().insertAll(recipeList)
            val test = db.recipeDao().getAll()[0].recipe.recipeId

            ingredientList.add(Ingredient(0, "Rice", 2.7, "G", null, "Fisk", false, 1.0,-1))
            ingredientList.add(Ingredient(0, "Rice", 2.7, "G", null, "Fisk", false, 1.0,-1))
            ingredientList.add(Ingredient(0, "Rice", 2.7, "G", null, "Fisk", false, 1.0,-1))
            db.ingredientDao().insertAll(ingredientList)
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            recipeList = ArrayList(db.recipeDao().getAll())
            runOnUiThread {
                calculatePrices(recipeList)
                searchOnChange(recipeList)
                recipe_list.adapter = RecipesAdapter(recipeList, context as Recipes)
                recipe_list.layoutManager = LinearLayoutManager(context)
                recipe_list.setHasFixedSize(true)
            }
        }
    }

    private fun setupRecyclerView(){
        GlobalScope.launch {
            recipeList = ArrayList(db.recipeDao().getAll())
            runOnUiThread {
                calculatePrices(recipeList)
                searchOnChange(recipeList)
                recipe_list.adapter = RecipesAdapter(recipeList, context as Recipes)
                recipe_list.layoutManager = LinearLayoutManager(context)
                recipe_list.setHasFixedSize(true)
            }
        }

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(recipe_list){
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                buttons = listOf(deleteButton)
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(recipe_list)
    }
}