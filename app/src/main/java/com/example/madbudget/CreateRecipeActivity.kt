package com.example.madbudget

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.example.madbudget.models.Ingredient
import com.example.madbudget.models.IngredientSelection
import com.example.madbudget.models.Recipe
import com.example.madbudget.salling.jsonModels.JsonProduct
import com.example.madbudget.salling.jsonModels.JsonSuggestions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_recipes.ingredient_selection_list
import kotlinx.android.synthetic.main.activity_create_recipes_wip.*
import kotlinx.android.synthetic.main.select_ingerdient_dialog.*
import kotlinx.android.synthetic.main.show_ingredient_dialog.*


class CreateRecipeActivity : AppCompatActivity(), IngredientSelectionAdapter.OnIngredientSelectionClickListener, IngredientSelectionAdapter.OnIngredientCheckBoxClickListener{


    private var ingredientSelectionList: ArrayList<IngredientSelection> = ArrayList()
    private var ingredientList: ArrayList<Ingredient> = ArrayList()
    private lateinit var multiSelectSpinnerWithSearch: MultiSpinnerSearch
    private lateinit var mAlertDialog: AlertDialog
    private lateinit var recipe: Recipe
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes_wip)

        ingredient_selection_list.adapter = IngredientSelectionAdapter(ingredientSelectionList,this, this,this)
        ingredient_selection_list.layoutManager = LinearLayoutManager(this)
        ingredient_selection_list.setHasFixedSize(true)

        database = DatabaseBuilder.get(this)

        val recipeId: Int = intent.getIntExtra("ClickedRecipe",-1)

        recipe = if (recipeId != -1)
            database.recipeDao().getById(recipeId)!!.recipe
        else
            Recipe(0,recipe_list_name.text.toString(),4,"30min",null,null)

        val button: FloatingActionButton = add_ingredient_button
        button.setOnClickListener(View.OnClickListener {
            mAlertDialog = createAlertDialog()
            multiSelectSpinnerWithSearch = mAlertDialog.multiItemSelectionSpinner
        })
    }

    private fun createAlertDialog(): AlertDialog {
        mAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null))
            .setTitle("Tilføj Ingrediens...")
            .setPositiveButton("OK") {_,_ ->
                val ingredientSelection = IngredientSelection(mAlertDialog.spinner_ingredient_selection_name.text.toString(), mAlertDialog.spinner_ingredient_amount.text.toString(), ingredientList)
                ingredientSelection.isSelected = true
                ingredientSelectionList.add(ingredientSelection)
                ingredient_selection_list.adapter?.notifyDataSetChanged()
                mAlertDialog.dismiss()

                database.recipeDao().insert(recipe)

            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(
                    this,
                    "Cancel",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()

        mAlertDialog.multiItemSelectionSpinner.isEnabled = false
        mAlertDialog.spinner_ingredient_selection_name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && mAlertDialog.spinner_ingredient_selection_name.text.toString().isNotEmpty()) {
                mAlertDialog.multiItemSelectionSpinner.isEnabled = true
                initSpinner(mAlertDialog.spinner_ingredient_selection_name.text.toString())
            }

        }
        return mAlertDialog
    }

    private fun initSpinner(searchInput: String){

        multiSelectSpinnerWithSearch.isSearchEnabled = true
        multiSelectSpinnerWithSearch.setClearText("Luk og Ryd")
        multiSelectSpinnerWithSearch.hintText = "Vælg Ingrediens"

        val tempIngredientList: ArrayList<Ingredient> = ArrayList()

        multiSelectSpinnerWithSearch.setItems(searchForIngredients(searchInput)) {
            for (i in it.indices) {
                if (it[i].isSelected) {
                    val ingredient = Ingredient()
                    ingredient.ingredientName = it[i].name
                    // ingredient.ingredientPrice = it[i].price
                    ingredient.recipeParentId = recipe.recipeId
                    ingredient.ingredientType = null
                    tempIngredientList.add(ingredient)
                }
            }
        }
        ingredientList = tempIngredientList
        database.ingredientDao().insertAll(ingredientList)
    }


    private fun searchForIngredients(searchInput: String) :MutableList<KeyPairBoolData> {

        val listArray1: MutableList<KeyPairBoolData> = ArrayList()

        val dummyJsonBoolData: ArrayList<JsonProduct> = ArrayList()
        val dummyData: JsonSuggestions = JsonSuggestions(dummyJsonBoolData)

        dummyJsonBoolData.add(JsonProduct("bund","","","","","",20.5))


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

        val mAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.show_ingredient_dialog, null))
            .setTitle("Ingredienser")
            .setPositiveButton("OK", null)
            .setNegativeButton("Fortryd") { _, _ ->
            }
            .show()

        mAlertDialog.ingredient_list.adapter = IngredientAdapter(ingredientSelectionList[position].ingredientList,this)
        mAlertDialog.ingredient_list.layoutManager = LinearLayoutManager(this)
        mAlertDialog.ingredient_list.setHasFixedSize(true)


    }

    override fun onCheckBoxClick(position: Int) {

        Log.i("hej",position.toString())

        for (i in ingredientSelectionList) {
            Log.i("bund", i.toString())
        }

    }
}

