package com.gruppe17.madbudget.activities

import SwipeHelper
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import com.gruppe17.madbudget.models.*
import com.gruppe17.madbudget.recyclerviews.CellClickListener
import com.gruppe17.madbudget.recyclerviews.RecipeAdapter
import com.gruppe17.madbudget.rest.coop.model.CoopLocation
import com.gruppe17.madbudget.rest.coop.model.CoopOpeningHour
import com.gruppe17.madbudget.rest.coop.model.CoopStore
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.android.synthetic.main.activity_recipes.navigation
import kotlinx.android.synthetic.main.activity_testyboi.view.*
import kotlinx.android.synthetic.main.dialog_create_recipe.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipeActivity : AppCompatActivity(), CellClickListener {

    private lateinit var db: AppDatabase
    private lateinit var recipeList: ArrayList<RecipeWithIngredientSelections>
    private lateinit var context: Context
    private lateinit var newAlertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        db = DatabaseBuilder.get(this)
        context = this

        initNavigationMenu()

        val menu: Menu = navigation.menu
        menu.getItem(0).isChecked = true

        //TODO - Call database here get recipes and their ingredients!!!

        // iniDummyRecipes()

        new_recipe_button.setOnClickListener {
            initAlertDialog()
        }



        setupRecyclerView()

    }

    private fun initAlertDialog(){

        newAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.dialog_create_recipe, null))
            .setPositiveButton("OK") { dialog, which ->
                db = DatabaseBuilder.get(this)
                GlobalScope.launch {
                    val newRecipe = Recipe()
                    // TODO setError if empty?
                    newRecipe.name = newAlertDialog.et_new_recipe_name.text.toString()
                    newRecipe.timeToMake = newAlertDialog.et_new_recipe_time.text.toString()
                    val recipeId = db.recipeDao().insert(newRecipe).toInt()
                    val recipeActivity = Intent(context, CreateRecipeActivity::class.java)
                    recipeActivity.putExtra("ClickedRecipe", recipeId)

                    runOnUiThread{
                        startActivity(recipeActivity)
                    }
                }


            }
            .setNegativeButton("Annuller") { dialog, which -> }
            .show()
    }

    private fun calculatePrices() {

        var ingredientSelectionList: ArrayList<IngredientSelectionWithIngredients> = ArrayList()

        GlobalScope.launch {

            ingredientSelectionList = ArrayList(db.ingredientSelectionDao().getAll())

            var smallestPrice: Double
            var recipePrice: Double

            for (curRecipe in recipeList) {


                recipePrice = 0.0
                for (curIngSel in ingredientSelectionList) {


                    if (curRecipe.recipe.id == curIngSel.ingredientSelection.recipeParentId && curIngSel.ingredientSelection.isSelected) {
                        smallestPrice = Double.MAX_VALUE
                        for (curIng in curIngSel.ingredients) {

                            val ingPrice = Ingredient.calcIngredientPrice(
                                curIngSel.ingredientSelection,
                                curIng
                            )
                            if (ingPrice < smallestPrice)
                                smallestPrice = ingPrice
                        }
                        recipePrice += smallestPrice
                    }
                    curRecipe.recipe.price = recipePrice
                }
                db.recipeDao().update(curRecipe.recipe)
            }
        }
    }


    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            this,
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    deleteRecipe(position)
                }
            })
    }

    private fun deleteRecipe(position: Int) {

        GlobalScope.launch {
            val recipeWithIngredientSelections =
                db.recipeDao().getById(recipeList[position].recipe.id)

            for (i in recipeWithIngredientSelections?.ingredientSelections!!) {
                db.ingredientDao().deleteByParentId(i.id)
            }

            db.ingredientSelectionDao().deleteByParentId(recipeList[position].recipe.id)
            db.recipeDao().delete(recipeList[position].recipe)

            runOnUiThread {
                recipeList.removeAt(position)
                recipe_list.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCellClickListener(clickedRecipe: RecipeWithIngredientSelections) {
        val recipeActivity = Intent(this, CreateRecipeActivity::class.java)
        recipeActivity.putExtra("ClickedRecipe", clickedRecipe.recipe.id)
        startActivity(recipeActivity)
    }

    private fun searchOnChange(recipeList: List<RecipeWithIngredientSelections>) {
        search_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchedText = search_text.text.toString()
                val searchedRecipeList: ArrayList<RecipeWithIngredientSelections> = ArrayList()
                var sameString = false
                val updatedAdapter: RecipeAdapter = recipe_list.adapter as RecipeAdapter

                if (search_text.text.isEmpty()) {
                    updatedAdapter.updateResource(recipeList)
                    recipe_list.adapter = updatedAdapter
                } else {
                    for (i in 0..recipeList.size - 1) {
                        for (j in 0..searchedText.length - 1)
                            if (recipeList[i].recipe.name.length < searchedText.length)
                                sameString = false
                            else
                                sameString =
                                    searchedText[j].toLowerCase() == recipeList[i].recipe.name[j].toLowerCase()
                        if (sameString)
                            searchedRecipeList.add(recipeList[i])
                    }
                    updatedAdapter.updateResource(searchedRecipeList)
                    recipe_list.adapter = updatedAdapter
                }
            }
        })
    }


    private fun setupRecyclerView() {
        GlobalScope.launch {
            recipeList = ArrayList(db.recipeDao().getAll())
            calculatePrices()
            runOnUiThread {
                searchOnChange(recipeList)
                recipe_list.adapter = RecipeAdapter(recipeList, context as RecipeActivity)
                recipe_list.layoutManager = LinearLayoutManager(context)
                recipe_list.setHasFixedSize(true)
                //recipe_list.scheduleLayoutAnimation()
            }
        }

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(recipe_list) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                buttons = listOf(deleteButton)
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(recipe_list)
    }

    private fun initNavigationMenu(){
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_1 ->{
                    true
                }
                R.id.page_2 ->{
                    val mapsActivity = Intent(this, MapsActivity::class.java)
                    startActivity(mapsActivity)
                    true
                }
                R.id.page_3 -> {
                    Toast.makeText(this,"Ikke implementeret",Toast.LENGTH_LONG).show()
                    true
                }
                R.id.page_4 -> {
                    Toast.makeText(this,"Ikke implementeret",Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()

        val menu: Menu = navigation.menu
        menu.getItem(0).isChecked = true

        GlobalScope.launch {
            recipeList = ArrayList(db.recipeDao().getAll())
            calculatePrices()
            runOnUiThread {
                searchOnChange(recipeList)
                recipe_list.adapter = RecipeAdapter(recipeList, context as RecipeActivity)
                recipe_list.layoutManager = LinearLayoutManager(context)
                recipe_list.setHasFixedSize(true)
                //recipe_list.scheduleLayoutAnimation()
            }
        }
    }
}

/*
private fun iniDummyRecipes(){
 val recipeList: ArrayList<Recipe> = ArrayList()
 val ingredientList: ArrayList<Ingredient> = ArrayList()
 val ingredientSelectionList: ArrayList<IngredientSelection> = ArrayList()

 recipeList.add(Recipe(0, "SpaghetBolo", 5, "30m", null, null))

 GlobalScope.launch {
     val id = db.recipeDao().insert(recipeList[0])

     ingredientList.add(Ingredient(0, "ing1", 2.7, "G", null, "Fisk", false, 1.0,-1))
     ingredientList.add(Ingredient(0, "ing2", 2.7, "G", null, "Fisk", false, 1.0,-1))
     ingredientList.add(Ingredient(0, "ing3", 2.7, "G", null, "Fisk", false, 1.0,-1))


     ingredientSelectionList.add(IngredientSelection(0,"Hakkede Tomater","2","STK",true,id.toInt()))
     ingredientSelectionList.add(IngredientSelection(0,"Hakket oksekød","500","G",true,id.toInt()))
     ingredientSelectionList.add(IngredientSelection(0,"Pasta","400","G",true,id.toInt()))

     for ((counter, i) in ingredientSelectionList.withIndex()) {

         val ingId = db.ingredientSelectionDao().insert(ingredientSelectionList[counter])

         for (j in ingredientList)
                 j.ingredientSelectionParentId = ingId.toInt()

         db.ingredientDao().insertAll(ingredientList)

     }
 }
}

*/