package com.gruppe17.madbudget

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.gruppe17.madbudget.models.*
import com.gruppe17.madbudget.salling.jsonModels.JsonProduct
import com.gruppe17.madbudget.salling.jsonModels.JsonSuggestions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gruppe17.madbudget.ingsel.IngSelDialogAdapter
import kotlinx.android.synthetic.main.activity_create_recipes.ingredient_selection_list
import kotlinx.android.synthetic.main.activity_create_recipes_wip.*
import kotlinx.android.synthetic.main.dialog_ing_sel.*
import kotlinx.android.synthetic.main.select_ingerdient_dialog.*
import kotlinx.android.synthetic.main.show_ingredient_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateRecipeActivity : AppCompatActivity(),
    IngredientSelectionAdapter.OnIngredientSelectionClickListener,
    IngredientSelectionAdapter.OnIngredientCheckBoxClickListener {


    private var dbIngredientSelectionList: ArrayList<IngredientSelection> = ArrayList()
    private var dbIngredientList: ArrayList<Ingredient> = ArrayList()
    private var ingredientSelectionList: ArrayList<IngredientSelectionWithIngredients> = ArrayList()
    private var ingredientList: ArrayList<Ingredient> = ArrayList()
    private lateinit var multiSelectSpinnerWithSearch: MultiSpinnerSearch
    private lateinit var mAlertDialog: AlertDialog
    private lateinit var recipe: RecipeWithIngredientSelections
    private lateinit var database: AppDatabase
    private var newRecipeId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes_wip)
        val context = this
        database = DatabaseBuilder.get(this)

        ingredient_selection_list.layoutManager = LinearLayoutManager(this)
        ingredient_selection_list.setHasFixedSize(true)


        val recipeId = intent.getIntExtra("ClickedRecipe", -1)

        // check if coming from existing recipe
        if (recipeId != -1) {
            GlobalScope.launch {
                recipe = database.recipeDao().getById(recipeId)!!
                ingredientSelectionList = ArrayList(
                    database.ingredientSelectionDao().getAllByIngredientSelectionId(recipeId)
                )
                ingredient_selection_list.adapter =
                    IngredientSelectionAdapter(ingredientSelectionList, context)

                Log.i(
                    "bund",
                    ArrayList(
                        database.ingredientSelectionDao().getAllByIngredientSelectionId(recipeId)
                    ).toString()
                )
            }
        } else {
            recipe = RecipeWithIngredientSelections(Recipe(0, "test", 4, "30min", null, null), null)
            ingredient_selection_list.adapter = IngredientSelectionAdapter(ArrayList(), context)
        }

        val button: FloatingActionButton = add_ingredient_button
        button.setOnClickListener {
//            initAlertDialog()
//            initSpinner()
            initAlertDialog2()


        }
    }

    private fun saveIngredientSelection() {

        dbIngredientSelectionList.clear()

        GlobalScope.launch {
            newRecipeId = if (database.recipeDao().getById(newRecipeId.toInt()) != null) {
                database.recipeDao().getById(newRecipeId.toInt())!!.recipe.recipeId.toLong()
            } else {
                database.recipeDao().insert(recipe.recipe)
            }

            val ingredientSelection = IngredientSelection(
                0,
                mAlertDialog.spinner_ingredient_selection_name.text.toString(),
                mAlertDialog.spinner_ingredient_amount.text.toString(),
                true,
                newRecipeId.toInt()
            )

            dbIngredientSelectionList.add(ingredientSelection)
            ingredientSelectionList.add(
                IngredientSelectionWithIngredients(
                    ingredientSelection,
                    ingredientList
                )
            )

            database.ingredientSelectionDao().insertAll(dbIngredientSelectionList)

            val newRecipeWithIngredientSelections: RecipeWithIngredientSelections? =
                database.recipeDao().getById(newRecipeId.toInt())

            for (i in dbIngredientList)
                i.ingredientSelectionParentId =
                    newRecipeWithIngredientSelections!!.ingredientSelections!!.last().ingredientSelectionId

            database.ingredientDao().insertAll(dbIngredientList)

            runOnUiThread {
                ingredient_selection_list.adapter =
                    IngredientSelectionAdapter(ingredientSelectionList, applicationContext)
                /* ingredient_selection_list.adapter?.notifyDataSetChanged()*/
                Log.i("bund", ingredientSelectionList.toString())
            }
        }
    }

    private fun initAlertDialog2() {
        mAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.dialog_ing_sel, null))
            .setTitle("Tilføj ingrediens")
            .setPositiveButton("OK") { dialog, which ->
                if (ingredientList.isEmpty()) {
                    Toast.makeText(this, "Ingen ingredienser valgt", Toast.LENGTH_LONG).show()
                } else {
                }
            }
            .setNegativeButton("Annuller") { dialog, which -> }
            .show()


        /*
        mAlertDialog.setOnShowListener(DialogInterface.OnShowListener {
            mAlertDialog.window!!.setLayout(mAlertDialog.window!!.decorView.width, 2000)

            this.resources.displayMetrics.heightPixels-100

        })

         */
        mAlertDialog.window!!.setLayout(this.resources.displayMetrics.widthPixels-10, this.resources.displayMetrics.heightPixels-10)

        mAlertDialog.inglist_notselected.setHasFixedSize(true)
        mAlertDialog.inglist_notselected.layoutManager = LinearLayoutManager(this)
        mAlertDialog.inglist_notselected.adapter = IngSelDialogAdapter(Utility.getTestIngredientList())

        mAlertDialog.inglist_selected.setHasFixedSize(true)
        mAlertDialog.inglist_selected.layoutManager = LinearLayoutManager(this)
        mAlertDialog.inglist_selected.adapter = IngSelDialogAdapter(Utility.getTestIngredientList())


    }

    private fun initAlertDialog() {

        mAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.select_ingerdient_dialog, null))
            .setTitle("Tilføj Ingrediens...")
            .setPositiveButton("OK") { _, _ ->
                if (ingredientList.isEmpty())
                    Toast.makeText(this, "Ingen ingredienser valgt", Toast.LENGTH_LONG).show()
                else {
                    saveIngredientSelection()
                    mAlertDialog.dismiss()
                    ingredient_selection_list.adapter?.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()

        ingredientList.clear()

        multiSelectSpinnerWithSearch = mAlertDialog.multiItemSelectionSpinner
    }

    private fun initSpinner() {

        multiSelectSpinnerWithSearch.isSearchEnabled = true
        multiSelectSpinnerWithSearch.setClearText("Luk og Ryd")
        multiSelectSpinnerWithSearch.hintText = "Vælg Ingrediens"

        mAlertDialog.multiItemSelectionSpinner.isEnabled = false

        mAlertDialog.spinner_ingredient_selection_name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && mAlertDialog.spinner_ingredient_selection_name.text.toString()
                    .isNotEmpty()
            ) {
                mAlertDialog.multiItemSelectionSpinner.isEnabled = true
                saveIngredientList(mAlertDialog.spinner_ingredient_selection_name.text.toString())
            }
        }
    }

    private fun saveIngredientList(searchInput: String) {

        multiSelectSpinnerWithSearch.setItems(searchForIngredients(searchInput)) {
            for (i in it.indices) {
                if (it[i].isSelected) {
                    val ingredient = Ingredient()
                    ingredient.ingredientName = it[i].name
                    ingredient.hasBeenClicked = true
                    ingredient.amount = 0.0
                    ingredient.ingredientType = "test"
                    ingredient.ingredientPrice = it[i].price

                    dbIngredientList.add(ingredient)
                    ingredientList.add(ingredient)
                }
            }
        }
    }

    private fun searchForIngredients(searchInput: String): MutableList<KeyPairBoolData> {

        val listArray1: MutableList<KeyPairBoolData> = ArrayList()

        val dummyJsonBoolData: ArrayList<JsonProduct> = ArrayList()
        val dummyData: JsonSuggestions = JsonSuggestions(dummyJsonBoolData)

        dummyJsonBoolData.add(JsonProduct("test", "", "", "", "", "", 20.5))

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

        GlobalScope.launch {
            val ingredientsToShow: ArrayList<Ingredient> = ArrayList(
                database.ingredientSelectionDao()
                    .getById(recipe.ingredientSelections!![position].ingredientSelectionId)!!.ingredients
            )
            runOnUiThread {
                mAlertDialog.ingredient_list.adapter =
                    IngredientAdapter(ingredientsToShow, applicationContext)
                mAlertDialog.ingredient_list.layoutManager = LinearLayoutManager(applicationContext)
                mAlertDialog.ingredient_list.setHasFixedSize(true)
            }
        }
    }

    override fun onCheckBoxClick(position: Int) {

    }
}

