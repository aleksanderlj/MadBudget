package com.gruppe17.madbudget.activities

import SwipeHelper
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.Utility
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import com.gruppe17.madbudget.models.*
import com.gruppe17.madbudget.recyclerviews.CreateIngredientSelectionDialogAdapter
import com.gruppe17.madbudget.recyclerviews.ViewIngredientSelectionAdapter
import com.gruppe17.madbudget.recyclerviews.IngredientSelectionAdapter
import kotlinx.android.synthetic.main.activity_create_recipes_wip.*
import kotlinx.android.synthetic.main.dialog_ing_sel.*
import kotlinx.android.synthetic.main.item_spinner.view.*
import kotlinx.android.synthetic.main.show_ingredient_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateRecipeActivity : AppCompatActivity(),
    IngredientSelectionAdapter.OnIngredientSelectionClickListener,
    IngredientSelectionAdapter.OnIngredientCheckBoxClickListener,
    CreateIngredientSelectionDialogAdapter.OnDialogIngredientClickListener
{

    private var ingredientSelectionList: ArrayList<IngredientSelectionWithIngredients> = ArrayList()
    private var mAlertDialog: AlertDialog? = null
    //private lateinit var recipe: RecipeWithIngredientSelections
    private lateinit var db: AppDatabase
    private var dialogIngNotSelected = ArrayList<Ingredient>()
    private var dialogIngSelected = ArrayList<Ingredient>()
    private var recipeId = -1
    var hasChanged = false
    var recipeBak: RecipeWithIngredientSelections? = null
    private var ingSelListBak: ArrayList<IngredientSelectionWithIngredients>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipes_wip)

        db = DatabaseBuilder.get(this)

        recipeId = intent.getIntExtra("ClickedRecipe",-1)

        // check if coming from existing recipe
        if (recipeId != -1) {
            GlobalScope.launch {
                ingredientSelectionList = db.ingredientSelectionDao().getAllByRecipeId(recipeId) as ArrayList<IngredientSelectionWithIngredients>

                ingSelListBak = db.ingredientSelectionDao().getAllByRecipeId(recipeId) as ArrayList<IngredientSelectionWithIngredients>
                recipeBak = db.recipeDao().getById(recipeId)

                runOnUiThread{
                    recipe_title.setText(recipeBak!!.recipe.name)
                    recipe_list_time.setText(recipeBak!!.recipe.timeToMake)
                    recipe_list_price.setText(recipeBak!!.recipe.price.toString())

                    recipe_list_price.addTextChangedListener(ChangeWatcher())
                    recipe_list_time.addTextChangedListener(ChangeWatcher())
                    recipe_title.addTextChangedListener(ChangeWatcher())
                    setupRecyclerView()
                }
            }
        } else { // FUCK IT Anders was right
            ingredientSelectionList = ArrayList()
            recipeBak = null

            recipe_list_price.addTextChangedListener(ChangeWatcher())
            recipe_list_time.addTextChangedListener(ChangeWatcher())
            recipe_title.addTextChangedListener(ChangeWatcher())
            runOnUiThread{ setupRecyclerView() }
        }

        toolbar.title = "Opskrift"

        add_ingredient_button.setOnClickListener {
            hasChanged = true
            initAlertDialog2()
        }

    }

    private fun saveIngredientSelection() {

        db = DatabaseBuilder.get(this)
        GlobalScope.launch {
            if(recipeId == -1){
                recipeId = db.recipeDao().insert(Recipe()).toInt()
            }

            val ingSel = IngredientSelection(
                0,
                mAlertDialog!!.ingsel_name.text.toString(),
                mAlertDialog!!.ingsel_amount.text.toString().toDoubleOrNull(),
                mAlertDialog!!.unit_spinner.selectedItem.toString(),
                true,
                recipeId
            )
            val ingSelId = db.ingredientSelectionDao().insert(ingSel)
            val ingArray = ArrayList<Ingredient>(dialogIngSelected)
            for(i in ingArray){
                i.ingredientSelectionParentId = ingSelId.toInt()
            }
            db.ingredientDao().insertAll(ingArray)
            ingredientSelectionList.clear()
            ingredientSelectionList.addAll(db.ingredientSelectionDao().getAllByRecipeId(recipeId))
            runOnUiThread {
                ingredient_selection_list.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun initAlertDialog2() {
        var newAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.dialog_ing_sel, null))
            .setTitle("Tilføj ingrediensgruppe")
            .setPositiveButton("OK") { dialog, which ->
                if (dialogIngSelected.isEmpty()) {
                    Toast.makeText(this, "Ingen ingredienser valgt", Toast.LENGTH_LONG).show()
                } else {
                    saveIngredientSelection()
                }
            }
            .setNegativeButton("Annuller") { dialog, which -> }
            .create()

        mAlertDialog = newAlertDialog

        newAlertDialog.show()

        newAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).isEnabled = false

        newAlertDialog.window!!.setLayout(this.resources.displayMetrics.widthPixels-10, this.resources.displayMetrics.heightPixels-10)
        newAlertDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)


        dialogIngNotSelected = Utility.getTestIngredientList()
        dialogIngSelected = ArrayList<Ingredient>()

        newAlertDialog.inglist_notselected.setHasFixedSize(true)
        newAlertDialog.inglist_notselected.layoutManager = LinearLayoutManager(this)
        newAlertDialog.inglist_notselected.adapter = CreateIngredientSelectionDialogAdapter(dialogIngNotSelected, this, true)

        newAlertDialog.inglist_selected.setHasFixedSize(true)
        newAlertDialog.inglist_selected.layoutManager = LinearLayoutManager(this)
        newAlertDialog.inglist_selected.adapter = CreateIngredientSelectionDialogAdapter(dialogIngSelected, this, false)



        newAlertDialog.ingsel_search.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var adapter = newAlertDialog.inglist_notselected.adapter as CreateIngredientSelectionDialogAdapter
                adapter.filter.filter(s.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })

        val spinnerList = ArrayList<String>()
        spinnerList.add("G")
        spinnerList.add("ML")
        spinnerList.add("STK")
        var spinnerAdapter = object : ArrayAdapter<String>(this,
            R.layout.item_spinner,
            R.id.item_spinner_text, spinnerList){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = super.getView(position, convertView, parent)
                view.item_spinner_text.text = spinnerList[position]
                return view
            }
        }

        newAlertDialog.unit_spinner.adapter = spinnerAdapter

        newAlertDialog.ingsel_name.addTextChangedListener(ChangeWatcher())
        newAlertDialog.ingsel_amount.addTextChangedListener(ChangeWatcher())
        newAlertDialog.unit_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                hasChanged = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


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

        mAlertDialog.ingredient_list.adapter = ViewIngredientSelectionAdapter(ingredientsToShow, applicationContext)
        mAlertDialog.ingredient_list.layoutManager = LinearLayoutManager(applicationContext)
        mAlertDialog.ingredient_list.setHasFixedSize(true)
    }

    override fun onCheckBoxClick(position: Int) {

        ingredientSelectionList[position].ingredientSelection.isSelected = !ingredientSelectionList[position].ingredientSelection.isSelected

        GlobalScope.launch {
            db.ingredientSelectionDao().update(ingredientSelectionList[position].ingredientSelection)
            runOnUiThread { ingredient_selection_list.adapter?.notifyDataSetChanged() }
        }
    }

    private fun setupRecyclerView(){
        ingredient_selection_list.layoutManager = LinearLayoutManager(this)
        ingredient_selection_list.setHasFixedSize(true)
        ingredient_selection_list.adapter = IngredientSelectionAdapter(ingredientSelectionList, applicationContext, this@CreateRecipeActivity, this)

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
                    Toast.makeText(applicationContext,"Deleted item ${ingredientSelectionList[position].ingredientSelection.name}",Toast.LENGTH_SHORT).show()
                    deleteIngredientSelection(position)
                }
            })
    }

    private fun deleteIngredientSelection(position: Int){
        GlobalScope.launch {

            val ingredientSelectionWithIngredients = db.ingredientSelectionDao().getAllByRecipeId(recipeId)

            db.ingredientDao().deleteByParentId(ingredientSelectionWithIngredients[position].ingredientSelection.id)
            db.ingredientSelectionDao().delete(ingredientSelectionWithIngredients[position].ingredientSelection)

            runOnUiThread{
                ingredientSelectionList.removeAt(position)
                ingredient_selection_list.adapter?.notifyDataSetChanged()
                Log.i("bund",ingredientSelectionList.toString())
            }
        }
    }

    override fun onDialogIngredientSelect(pos: Int) {
        hasChanged = true
        val item = dialogIngNotSelected.removeAt(pos)
        dialogIngSelected.add(0, item)

        val notSelectedAdapter = mAlertDialog!!.inglist_notselected.adapter!! as CreateIngredientSelectionDialogAdapter
        val selectedAdapter = mAlertDialog!!.inglist_selected.adapter!! as CreateIngredientSelectionDialogAdapter

        notSelectedAdapter.notifyItemRemoved(item)
        selectedAdapter.notifyItemAdded(item)
    }

    override fun onDialogIngredientDeselect(pos: Int) {
        hasChanged = true
        val item = dialogIngSelected.removeAt(pos)
        dialogIngNotSelected.add(0, item)

        val selectedAdapter = mAlertDialog!!.inglist_selected.adapter!! as CreateIngredientSelectionDialogAdapter
        val notSelectedAdapter = mAlertDialog!!.inglist_notselected.adapter!! as CreateIngredientSelectionDialogAdapter

        selectedAdapter.notifyItemRemoved(item)
        notSelectedAdapter.notifyItemAdded(item)
    }

    override fun onBackPressed() {
        if(hasChanged) {
            mAlertDialog = AlertDialog.Builder(this)
                .setTitle("Gem ændringer?")
                .setPositiveButton("Ja") { dialog, which ->

                    db = DatabaseBuilder.get(this)
                    GlobalScope.launch {
                        if(recipeId == -1){
                            recipeId = db.recipeDao().insert(Recipe()).toInt()
                        }

                        var newRec = Recipe(
                            recipeId,
                            recipe_title.text.toString(),
                            recipe_list_time.text.toString(),
                            recipe_list_price.text.toString().toDoubleOrNull(),
                            0.0 // TODO
                        )

                        db.recipeDao().insert(newRec)

                        runOnUiThread { super.onBackPressed() }
                    }

                }
                .setNegativeButton("Nej") { dialog, which ->
                    //recipeBak TODO
                    db = DatabaseBuilder.get(this)
                    GlobalScope.launch {
                        if(recipeBak == null){
                            Utility.deleteRecipe(recipeId, db)
                        } else {
                            Utility.deleteRecipe(recipeId, db)
                            db.recipeDao().insert(recipeBak!!.recipe)
                            db.ingredientSelectionDao().insertAll(recipeBak!!.ingredientSelections!!)
                            for(n in ingSelListBak!!){
                                db.ingredientDao().insertAll(n.ingredients)
                            }
                        }

                        runOnUiThread { super.onBackPressed() }
                    }
                }
                .setNeutralButton("Annuller") { dialog, which -> }
                .show()

            val window = mAlertDialog!!.window
            val attr = window!!.attributes

            attr.gravity = Gravity.BOTTOM
            window.attributes = attr
        } else {
            super.onBackPressed()
        }
    }

    /*
    override fun onDestroy() {
        if(ingredientSelectionList.isEmpty()){
            val i = Intent()
            i.putExtra("RecipeID", recipeId)
            JobIntentService.enqueueWork(this, DBDeleteService::class.java, 1, i)
        }
        super.onDestroy()
    }

     */

    /*
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
     */
    inner class ChangeWatcher: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            hasChanged = true

            if(mAlertDialog != null){
                mAlertDialog!!.getButton(DialogInterface.BUTTON_POSITIVE).isEnabled = !mAlertDialog!!.ingsel_amount.text.isBlank() && !mAlertDialog!!.ingsel_name.text.isBlank()
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

}



