package com.gruppe17.madbudget.activities

import SwipeHelper
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.Utility
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import com.gruppe17.madbudget.models.*
import com.gruppe17.madbudget.recyclerviews.ViewIngredientSelectionAdapter
import com.gruppe17.madbudget.recyclerviews.IngredientSelectionAdapter
import kotlinx.android.synthetic.main.activity_create_recipe.*
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.android.synthetic.main.activity_recipes.navigation
import kotlinx.android.synthetic.main.dialog_view_ingredient.*
import kotlinx.android.synthetic.main.item_unit_spinner.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateRecipeActivity : AppCompatActivity(),
    IngredientSelectionAdapter.OnIngredientSelectionClickListener,
    IngredientSelectionAdapter.OnIngredientCheckBoxClickListener
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
        setContentView(R.layout.activity_create_recipe)

        supportPostponeEnterTransition()
        val sharedId = intent.getIntExtra("sharedId", -1)
        if(sharedId != -1){
            recipe_title.transitionName = "recipe$sharedId"
        }
        supportStartPostponedEnterTransition()


        db = DatabaseBuilder.get(this)

        val menu: Menu = navigation.menu
        menu.getItem(0).isChecked = true

        initNavigationMenu()

        recipeId = intent.getIntExtra("ClickedRecipe",-1)

        // check if coming from existing recipe
        if (recipeId != -1) {
            GlobalScope.launch {
                ingredientSelectionList = db.ingredientSelectionDao().getAllByRecipeId(recipeId) as ArrayList<IngredientSelectionWithIngredients>

                ingSelListBak = db.ingredientSelectionDao().getAllByRecipeId(recipeId) as ArrayList<IngredientSelectionWithIngredients>
                recipeBak = db.recipeDao().getById(recipeId)

                runOnUiThread{


                    recipe_title.setText(recipeBak!!.recipe.name)
                    recipe_list_time.text = recipeBak!!.recipe.timeToMake
                    recipe_list_price.setText("%.2f".format(recipeBak!!.recipe.price))
                    updateSelectedCounter()

                    recipe_list_price.addTextChangedListener(ChangeWatcher())
                    recipe_list_time.addTextChangedListener(ChangeWatcher())
                    recipe_title.addTextChangedListener(ChangeWatcher())

                    setupRecyclerView()
                }
            }
        } else { // FUCK IT Anders was right
            ingredientSelectionList = ArrayList()
            recipeBak = null

            recipe_title.setText(intent.getStringExtra("recipeName"))
            recipe_list_time.text = intent.getStringExtra("recipeTime")
            updateSelectedCounter()

            recipe_list_price.addTextChangedListener(ChangeWatcher())
            recipe_list_time.addTextChangedListener(ChangeWatcher())
            recipe_title.addTextChangedListener(ChangeWatcher())
            runOnUiThread{ setupRecyclerView() }
        }

        add_ingredient_button.setOnClickListener {
            hasChanged = true
            /*
            val i = Intent(this, CreateIngSelActivity::class.java)
            i.putExtra("ClickedRecipe", recipeId)
            startActivity(i)
             */
            startRevealActivity(it)
        }

    }

    override fun onIngredientClick(position: Int) {

        val ingredientsToShow: ArrayList<Ingredient> = ArrayList(ingredientSelectionList[position].ingredients)

        val mAlertDialog = AlertDialog.Builder(this@CreateRecipeActivity)
            .setView(LayoutInflater.from(this@CreateRecipeActivity).inflate(R.layout.dialog_view_ingredient, null))
            .setTitle("Ingredienser")
            .setPositiveButton("OK", null)
            .setNegativeButton("Fortryd") { _, _ ->
            }
            .show()

        mAlertDialog.ingredient_list.adapter = ViewIngredientSelectionAdapter(ingredientsToShow, ingredientSelectionList[position], applicationContext)
        mAlertDialog.ingredient_list.layoutManager = LinearLayoutManager(applicationContext)
        mAlertDialog.ingredient_list.setHasFixedSize(true)
    }

    override fun onCheckBoxClick(position: Int) {

        ingredientSelectionList[position].ingredientSelection.isSelected = !ingredientSelectionList[position].ingredientSelection.isSelected

        GlobalScope.launch {
            db.ingredientSelectionDao().update(ingredientSelectionList[position].ingredientSelection)
            runOnUiThread {
                ingredient_selection_list.adapter?.notifyDataSetChanged()
                calculatePrice()
                updateSelectedCounter()
            }
        }
    }

    fun updateSelectedCounter(){
        var counter = 0

        for (i in ingredientSelectionList){
            if(i.ingredientSelection.isSelected){
                counter++
            }
        }

        numb_of_ingredients.text = counter.toString()
    }

    private fun calculatePrice(){

        var recipePrice: Double = 0.0

        for (i in ingredientSelectionList){
            var smallestPrice = Double.MAX_VALUE
            if (i.ingredientSelection.isSelected){
                for (j in i.ingredients){
                    val ingPrice = j.calculatePrice()
                    if (ingPrice != null) {
                        if (ingPrice < smallestPrice)
                            smallestPrice = ingPrice
                    }
                }
                recipePrice += smallestPrice
            }
        }
        recipe_list_price.setText("%.2f".format(recipePrice))
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
                calculatePrice()
            }
        }
    }

    override fun onBackPressed() {
        if(hasChanged) {
            val backPressDialog = AlertDialog.Builder(this)
                .setTitle("Gem ændringer?")
                .setPositiveButton("Ja") { dialog, which ->

                    db = DatabaseBuilder.get(this)
                    GlobalScope.launch {
                        if(recipeId == -1){
                            recipeId = db.recipeDao().insert(Recipe()).toInt()
                        }

                        val newRec = Recipe(
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

            val window = backPressDialog!!.window
            val attr = window!!.attributes

            attr.gravity = Gravity.BOTTOM
            window.attributes = attr
        } else {
            super.onBackPressed()
        }
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
                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
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
        }
    }

    inner class ChangeWatcher: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            hasChanged = true
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    override fun onResume() {
        super.onResume()
        updateSelectedCounter()
        db = DatabaseBuilder.get(this)
        val menu: Menu = navigation.menu
        menu.getItem(0).isChecked = true
        GlobalScope.launch {
            Log.i("bund",recipeId.toString());
            ingredientSelectionList.clear()
            ingredientSelectionList.addAll(db.ingredientSelectionDao().getAllByRecipeId(recipeId) as ArrayList<IngredientSelectionWithIngredients>)
            runOnUiThread{
                Log.i("bund",ingredientSelectionList.toString())
                calculatePrice()
                ingredient_selection_list.adapter?.notifyDataSetChanged()
            }
        }
    }


    fun startRevealActivity(v : View){
        val x = (v.x + v.width / 2)
        val y = (v.y + v.height / 2)

        val i = Intent(this, CreateIngSelActivity::class.java)
        i.putExtra("X", x.toInt())
        i.putExtra("Y", y.toInt())
        i.putExtra("ClickedRecipe", recipeId)

        startActivity(i)

        overridePendingTransition(0, 0)

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}



