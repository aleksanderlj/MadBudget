package com.gruppe17.madbudget

import SwipeHelper
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.gruppe17.madbudget.models.*
import com.gruppe17.madbudget.salling.jsonModels.JsonProduct
import com.gruppe17.madbudget.salling.jsonModels.JsonSuggestions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_recipes.ingredient_selection_list
import kotlinx.android.synthetic.main.activity_create_recipes_wip.*
import kotlinx.android.synthetic.main.select_ingerdient_dialog.*
import kotlinx.android.synthetic.main.show_ingredient_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateRecipeActivity : AppCompatActivity(), IngredientSelectionAdapter.OnIngredientSelectionClickListener, IngredientSelectionAdapter.OnIngredientCheckBoxClickListener{

    private var ingredientSelectionList: ArrayList<IngredientSelectionWithIngredients> = ArrayList()
    private var ingredientList: ArrayList<Ingredient> = ArrayList()
    private lateinit var multiSelectSpinnerWithSearch: MultiSpinnerSearch
    private lateinit var mAlertDialog: AlertDialog
    private lateinit var recipe: RecipeWithIngredientSelections
    private lateinit var database: AppDatabase
    private var newRecipeId: Long = 0
    private var isNewRecipe: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes_wip)

        database = DatabaseBuilder.get(this)

        val recipeId = intent.getIntExtra("ClickedRecipe",-1)

        // check if coming from existing recipe
        if (recipeId != -1){
            GlobalScope.launch {
                isNewRecipe = false
                recipe = database.recipeDao().getById(recipeId)!!
                newRecipeId = recipe.recipe.recipeId.toLong()
                ingredientSelectionList = ArrayList(database.ingredientSelectionDao().getAllByRecipeId(recipeId))

                runOnUiThread{ setupRecyclerView() }
            }
        } else {
            isNewRecipe = true
            recipe = RecipeWithIngredientSelections(Recipe(0,"test",4,"30min",null,null), null)
            setupRecyclerView()
        }

        val button: FloatingActionButton = add_ingredient_button
        button.setOnClickListener(View.OnClickListener {
            initAlertDialog()
            initSpinner()
        })
    }

    private fun saveIngredientSelection() {

        Log.i("bund",ingredientList.toString())

        GlobalScope.launch {

            if (isNewRecipe){
                newRecipeId = database.recipeDao().insert(recipe.recipe)
                isNewRecipe = false
            }

            val ingredientSelection = IngredientSelection(0,
                mAlertDialog.spinner_ingredient_selection_name.text.toString(),
                mAlertDialog.spinner_ingredient_amount.text.toString(),
                true,
                newRecipeId.toInt()
            )

            ingredientSelectionList.add(IngredientSelectionWithIngredients( ingredientSelection, ingredientList))

            database.ingredientSelectionDao().insert(ingredientSelection)

            val newRecipeWithIngredientSelections: RecipeWithIngredientSelections? = database.recipeDao().getById(newRecipeId.toInt())

            for (i in ingredientList)
                i.ingredientSelectionParentId = newRecipeWithIngredientSelections!!.ingredientSelections!!.last().ingredientSelectionId

            database.ingredientDao().insertAll(ingredientList)

        }
    }

    private fun initAlertDialog(){

        mAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null))
            .setTitle("Tilføj Ingrediens...")
            .setPositiveButton("OK") { _, _ ->
                if ( ingredientList.isEmpty())
                    Toast.makeText(this,"Ingen ingredienser valgt",Toast.LENGTH_LONG).show()
                else{
                    saveIngredientSelection()
                    mAlertDialog.dismiss()
                    ingredientList.clear()
                    ingredient_selection_list.adapter?.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()

        multiSelectSpinnerWithSearch = mAlertDialog.multiItemSelectionSpinner
    }

    private fun initSpinner(){

        multiSelectSpinnerWithSearch.isSearchEnabled = true
        multiSelectSpinnerWithSearch.setClearText("Luk og Ryd")
        multiSelectSpinnerWithSearch.hintText = "Vælg Ingrediens"

        mAlertDialog.multiItemSelectionSpinner.isEnabled = false

        mAlertDialog.spinner_ingredient_selection_name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && mAlertDialog.spinner_ingredient_selection_name.text.toString().isNotEmpty()) {
                mAlertDialog.multiItemSelectionSpinner.isEnabled = true
                saveIngredientList(mAlertDialog.spinner_ingredient_selection_name.text.toString())
            }
        }
    }

    private fun saveIngredientList(searchInput: String){

        multiSelectSpinnerWithSearch.setItems(searchForIngredients(searchInput)) {
            for (i in it.indices) {
                if (it[i].isSelected) {
                    val ingredient = Ingredient()
                    ingredient.ingredientName = it[i].name
                    ingredient.hasBeenClicked = true
                    ingredient.amount = 0.0
                    ingredient.ingredientType = "test"
                    ingredient.ingredientPrice = it[i].price

                    ingredientList.add(ingredient)
                }
            }
        }
    }

    private fun searchForIngredients(searchInput: String) :MutableList<KeyPairBoolData> {

        val listArray1: MutableList<KeyPairBoolData> = ArrayList()

        val dummyJsonBoolData: ArrayList<JsonProduct> = ArrayList()
        val dummyData: JsonSuggestions = JsonSuggestions(dummyJsonBoolData)

        dummyJsonBoolData.add(JsonProduct("test1","","","","","",20.5))
        dummyJsonBoolData.add(JsonProduct("test2","","","","","",20.5))
        dummyJsonBoolData.add(JsonProduct("test3","","","","","",20.5))

        for ((counter, i) in dummyData.suggestions.withIndex()) {
            val h = KeyPairBoolData()
            h.id = counter + 1.toLong()
            h.name = i.title
            h.price = i.price
            listArray1.add(h)
        }

       /* SallingCommunicator.getProductSuggestions(this,searchInput) { response ->
            val json = Klaxon().parse<JsonSuggestions>(response.toString())!!
            for ((counter, i) in json.suggestions.withIndex()) {
                val h = KeyPairBoolData()
                h.id = counter + 1.toLong()
                h.name = i.title
                h.price = i.price
                listArray1.add(h)
            }
        }*/

        return listArray1
    }

    override fun onIngredientClick(position: Int) {

        val ingredientsToShow: ArrayList<Ingredient> = ArrayList(ingredientSelectionList[position].ingredients)

        val mAlertDialog = AlertDialog.Builder(this@CreateRecipeActivity)
            .setView(LayoutInflater.from(this@CreateRecipeActivity).inflate(R.layout.show_ingredient_dialog, null))
            .setTitle("Ingredienser")
            .setPositiveButton("OK", null)
            .setNegativeButton("Fortryd") { _, _ ->
            }
            .show()

        mAlertDialog.ingredient_list.adapter = IngredientAdapter(ingredientsToShow, applicationContext)
        mAlertDialog.ingredient_list.layoutManager = LinearLayoutManager(applicationContext)
        mAlertDialog.ingredient_list.setHasFixedSize(true)
    }

    override fun onCheckBoxClick(position: Int) {}

    private fun setupRecyclerView(){
        ingredient_selection_list.layoutManager = LinearLayoutManager(this)
        ingredient_selection_list.setHasFixedSize(true)
        ingredient_selection_list.adapter = IngredientSelectionAdapter(ingredientSelectionList, applicationContext, this@CreateRecipeActivity)

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(ingredient_selection_list){
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                buttons = listOf(deleteButton)
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(ingredient_selection_list)
    }

    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            this,
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    Toast.makeText(applicationContext,"Deleted item ${ingredientSelectionList[position].ingredientSelection.ingredientSelectionName}",Toast.LENGTH_SHORT).show()
                    deleteIngredientSelection(position)
                }
            })
    }

    private fun deleteIngredientSelection(position: Int){
        GlobalScope.launch {

            val ingredientSelectionWithIngredients = database.ingredientSelectionDao().getAllByRecipeId(recipe.recipe.recipeId)

            database.ingredientDao().deleteByParentId(ingredientSelectionWithIngredients[position].ingredientSelection.ingredientSelectionId)
            database.ingredientSelectionDao().delete(ingredientSelectionWithIngredients[position].ingredientSelection)

            runOnUiThread{
                ingredientSelectionList.removeAt(position)
                ingredient_selection_list.adapter?.notifyDataSetChanged()
                Log.i("bund",ingredientSelectionList.toString())
            }
        }
    }
}

