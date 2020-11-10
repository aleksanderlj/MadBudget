package com.gruppe17.madbudget

import SwipeHelper
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
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
import com.gruppe17.madbudget.ingsel.IngSelDialogAdapter
import kotlinx.android.synthetic.main.activity_create_recipes.ingredient_selection_list
import kotlinx.android.synthetic.main.activity_create_recipes_wip.*
import kotlinx.android.synthetic.main.dialog_ing_sel.*
import kotlinx.android.synthetic.main.item_spinner.view.*
import kotlinx.android.synthetic.main.select_ingerdient_dialog.*
import kotlinx.android.synthetic.main.show_ingredient_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateRecipeActivity : AppCompatActivity(),
    IngredientSelectionAdapter.OnIngredientSelectionClickListener,
    IngredientSelectionAdapter.OnIngredientCheckBoxClickListener,
    IngSelDialogAdapter.OnDialogIngredientClickListener
{

    private var ingredientSelectionList: ArrayList<IngredientSelectionWithIngredients> = ArrayList()
    private var ingredientList: ArrayList<Ingredient> = ArrayList()
    private lateinit var multiSelectSpinnerWithSearch: MultiSpinnerSearch
    private lateinit var mAlertDialog: AlertDialog
    private lateinit var recipe: RecipeWithIngredientSelections
    private lateinit var database: AppDatabase
    private var newRecipeId: Long = 0
    private var isNewRecipe: Boolean = true
    private var dialogIngNotSelected = ArrayList<Ingredient>()
    private var dialogIngSelected = ArrayList<Ingredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes_wip)

        database = DatabaseBuilder.get(this)

        val recipeId = intent.getIntExtra("ClickedRecipe",-1)

        // check if coming from existing recipe
        if (recipeId != -1) {
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
        button.setOnClickListener {
            //initAlertDialog()
            //initSpinner()
            initAlertDialog2()


        }
    }

    private fun saveIngredientSelection() {

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

           // ingredientList = Utility.getTestIngredientList() as ArrayList<Ingredient>
            for (i in ingredientList)
                i.ingredientSelectionParentId = newRecipeWithIngredientSelections!!.ingredientSelections!!.last().ingredientSelectionId

            Log.i("bund",ingredientList.toString())

            database.ingredientDao().insertAll(ingredientList)

            ingredientList.clear()

        }
    }

    private fun initAlertDialog2() {
        mAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.dialog_ing_sel, null))
            .setTitle("Tilføj ingrediensgruppe")
            .setPositiveButton("OK") { dialog, which ->
                if (ingredientList.isEmpty()) {
                    Toast.makeText(this, "Ingen ingredienser valgt", Toast.LENGTH_LONG).show()
                } else {
                }
            }
            .setNegativeButton("Annuller") { dialog, which -> }
            .create()

        mAlertDialog.show()

        mAlertDialog.window!!.setLayout(this.resources.displayMetrics.widthPixels-10, this.resources.displayMetrics.heightPixels-10)
        mAlertDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)


        dialogIngNotSelected = Utility.getTestIngredientList()
        dialogIngSelected = ArrayList<Ingredient>()

        mAlertDialog.inglist_notselected.setHasFixedSize(true)
        mAlertDialog.inglist_notselected.layoutManager = LinearLayoutManager(this)
        mAlertDialog.inglist_notselected.adapter = IngSelDialogAdapter(dialogIngNotSelected, this, true)

        mAlertDialog.inglist_selected.setHasFixedSize(true)
        mAlertDialog.inglist_selected.layoutManager = LinearLayoutManager(this)
        mAlertDialog.inglist_selected.adapter = IngSelDialogAdapter(dialogIngSelected, this, false)



        mAlertDialog.ingsel_search.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var adapter = mAlertDialog.inglist_notselected.adapter as IngSelDialogAdapter
                adapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        val spinnerList = ArrayList<String>()
        spinnerList.add("G")
        spinnerList.add("ML")
        spinnerList.add("STK")
        var spinnerAdapter = object : ArrayAdapter<String>(this, R.layout.item_spinner, R.id.item_spinner_text, spinnerList){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = super.getView(position, convertView, parent)
                view.item_spinner_text.text = spinnerList[position]
                return view
            }
        }

        mAlertDialog.unit_spinner.adapter = spinnerAdapter

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

    override fun onDialogIngredientSelect(pos: Int) {
        val item = dialogIngNotSelected.removeAt(pos)
        dialogIngSelected.add(0, item)

        val notSelectedAdapter = mAlertDialog.inglist_notselected.adapter!! as IngSelDialogAdapter
        val selectedAdapter = mAlertDialog.inglist_selected.adapter!! as IngSelDialogAdapter

        notSelectedAdapter.notifyItemRemoved(item)
        selectedAdapter.notifyItemAdded(item)
    }

    override fun onDialogIngredientDeselect(pos: Int) {
        val item = dialogIngSelected.removeAt(pos)
        dialogIngNotSelected.add(0, item)

        val selectedAdapter = mAlertDialog.inglist_selected.adapter!! as IngSelDialogAdapter
        val notSelectedAdapter = mAlertDialog.inglist_notselected.adapter!! as IngSelDialogAdapter

        selectedAdapter.notifyItemRemoved(item)
        notSelectedAdapter.notifyItemAdded(item)
    }
}

