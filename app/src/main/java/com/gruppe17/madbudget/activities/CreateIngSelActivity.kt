package com.gruppe17.madbudget.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.RevealAnimation
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.database.DatabaseBuilder
import com.gruppe17.madbudget.models.Ingredient
import com.gruppe17.madbudget.models.IngredientSelection
import com.gruppe17.madbudget.recyclerviews.CreateIngredientSelectionDialogAdapter
import kotlinx.android.synthetic.main.activity_create_ingsel.*
import kotlinx.android.synthetic.main.dialog_ing_sel_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateIngSelActivity : AppCompatActivity(),
    CreateIngredientSelectionDialogAdapter.OnDialogIngredientClickListener {

    private var dialogIngNotSelected = ArrayList<Ingredient>()
    private var dialogIngSelected = ArrayList<Ingredient>()
    private var mAlertDialog: AlertDialog? = null
    private lateinit var db: AppDatabase
    private var recipeId = -1
    private var ingSelId = -1
    private lateinit var ingSelSearchAdapter: CreateIngredientSelectionDialogAdapter
    private lateinit var ingListAdapter: CreateIngredientSelectionDialogAdapter
    private lateinit var mRevealAnimation: RevealAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ingsel)
        mRevealAnimation = RevealAnimation(constraintLayout, intent, this)
        initNavigationMenu()


        val fs = Firebase.firestore
        val collection = fs.collection("Assortments").document("1885").collection("Products")

        recipeId = intent.getIntExtra("ClickedRecipe", -1)

        //dialogIngNotSelected = Utility.getTestIngredientList()
        dialogIngSelected = ArrayList<Ingredient>()


        inglist_selected.setHasFixedSize(true)
        inglist_selected.layoutManager = LinearLayoutManager(this)
        ingListAdapter = CreateIngredientSelectionDialogAdapter(dialogIngSelected, false, this)
        inglist_selected.adapter = ingListAdapter

        secondsearch_bar.setOnClickListener{
            val i = Intent(this, SearchIngredientActivity::class.java)
            startActivityForResult(i, 1)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }

        /*
        ingSelSearchAdapter =
            CreateIngredientSelectionDialogAdapter(dialogIngNotSelected, true)

        collection.limit(10).get().addOnSuccessListener { response ->
            for(doc in response){
                val i = RegexFilter.convertCoopIngredient(doc.toObject(CoopProduct::class.java))
                dialogIngNotSelected.add(i)
            }
            ingSelSearchAdapter.notifyDataSetChanged(dialogIngNotSelected)
        }

        val spinnerList = ArrayList<String>()
        spinnerList.add("G")
        spinnerList.add("ML")
        spinnerList.add("STK")
        var spinnerAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.item_unit_spinner,
            R.id.item_spinner_text, spinnerList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = super.getView(position, convertView, parent)
                view.item_spinner_text.text = spinnerList[position]
                return view
            }
        }

        unit_spinner.adapter = spinnerAdapter

         */


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){

            ingSelId = data!!.getIntExtra("IngSelId", -1)
            db = DatabaseBuilder.get(this)

            GlobalScope.launch {
                dialogIngSelected.clear()
                dialogIngSelected.addAll(db.ingredientDao().getAllByIngredientSelectionId(ingSelId))
                runOnUiThread {
                    ingListAdapter.notifyDataSetChanged()
                }
            }

        }
    }

    /*
    private fun initSearchDialog() {
        var newAlertDialog = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.dialog_ing_sel_search, null))
            .setTitle("Tilføj ingrediensgruppe")
            .setPositiveButton("OK") { dialog, which ->

            }
            .create()

        mAlertDialog = newAlertDialog

        newAlertDialog.show()

        newAlertDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)


        newAlertDialog.inglist_notselected.setHasFixedSize(true)
        newAlertDialog.inglist_notselected.layoutManager = LinearLayoutManager(this)
        newAlertDialog.inglist_notselected.adapter = ingSelSearchAdapter


        newAlertDialog.ingsel_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var adapter =
                    newAlertDialog.inglist_notselected.adapter as CreateIngredientSelectionDialogAdapter
                adapter.filter.filter(s.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })


    }

     */


    override fun onBackPressed() {
        if(search_bar.text.toString().trim().isEmpty() && dialogIngSelected.isEmpty()){
            mRevealAnimation.unRevealActivity()
        } else {

            val backPressDialog = AlertDialog.Builder(this)
                .setTitle("Gem ingrediensgruppe?")
                .setPositiveButton("Gem") { dialog, which ->
                    if (!search_bar.text.toString().trim().isEmpty() &&
                        !dialogIngSelected.isEmpty()
                    ) {

                        if(ingSelId == -1){
                            db = DatabaseBuilder.get(this)
                            GlobalScope.launch {
                                val ingSel = IngredientSelection(
                                    0,
                                    search_bar.text.toString(),
                                    null,
                                    null,
                                    true,
                                    recipeId
                                )
                                val ingSelId = db.ingredientSelectionDao().insert(ingSel)
                                val removables = ArrayList<Ingredient>()
                                for (i in dialogIngSelected) {
                                    i.ingredientSelectionParentId = ingSelId.toInt()
                                    if(i.selectedAmount <= 0) {
                                        removables.add(i)
                                    }
                                }
                                dialogIngSelected.removeAll(removables)
                                db.ingredientDao().insertAll(dialogIngSelected)
                                db.ingredientDao().deleteAll(removables)
                                runOnUiThread {
                                    mRevealAnimation.unRevealActivity()
                                }
                            }
                        } else {
                            db = DatabaseBuilder.get(this)
                            GlobalScope.launch {
                                val ingSel = IngredientSelection(
                                    ingSelId,
                                    search_bar.text.toString(),
                                    null,
                                    null,
                                    true,
                                    recipeId
                                )
                                db.ingredientSelectionDao().update(ingSel)

                                val removables = ArrayList<Ingredient>()
                                for (i in dialogIngSelected) {
                                    i.ingredientSelectionParentId = ingSelId
                                    if(i.selectedAmount <= 0) {
                                        removables.add(i)
                                    }
                                }

                                dialogIngSelected.removeAll(removables)

                                db.ingredientDao().updateAll(dialogIngSelected)
                                db.ingredientDao().deleteAll(removables)
                                runOnUiThread {
                                    mRevealAnimation.unRevealActivity()
                                }
                            }
                        }


                    } else {

                        if (search_bar.text.toString().trim().isEmpty()) {
                            val vibrate = AnimationUtils.loadAnimation(this, R.anim.vibrate)
                            search_bar.setError("Udfyld navn")
                            search_bar.startAnimation(vibrate)
                        }
                        if (dialogIngSelected.isEmpty()) {
                            val vibrate = AnimationUtils.loadAnimation(this, R.anim.vibrate)
                            tv_info2.setError("Vælg mindst én vare")
                            tv_info2.requestFocus()
                            tv_info2.startAnimation(vibrate)
                        }

                    }

                }
                .setNegativeButton("Kassér") { dialog, which ->
                    mRevealAnimation.unRevealActivity()
                }
                .setNeutralButton("Annuller") { dialog, which -> }
                .show()

            val window = backPressDialog!!.window
            val attr = window!!.attributes

            attr.gravity = Gravity.BOTTOM
            window.attributes = attr
        }
    }

    override fun onDialogIngredientSelect(pos: Int) {
        val item = dialogIngNotSelected.removeAt(pos)
        dialogIngSelected.add(0, item)

        val notSelectedAdapter =
            mAlertDialog!!.inglist_notselected.adapter!! as CreateIngredientSelectionDialogAdapter
        val selectedAdapter =
            inglist_selected.adapter!! as CreateIngredientSelectionDialogAdapter

        notSelectedAdapter.notifyItemRemoved(item)
        selectedAdapter.notifyItemAdded(item)
    }

    override fun onDialogIngredientDeselect(pos: Int) {
        val item = dialogIngSelected.removeAt(pos)
        dialogIngNotSelected.add(0, item)

        val selectedAdapter =
            inglist_selected.adapter!! as CreateIngredientSelectionDialogAdapter
        val notSelectedAdapter =
            mAlertDialog!!.inglist_notselected.adapter!! as CreateIngredientSelectionDialogAdapter

        selectedAdapter.notifyItemRemoved(item)
        notSelectedAdapter.notifyItemAdded(item)
    }

    private fun initNavigationMenu(){
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_1 -> {
                    true
                }
                R.id.page_2 -> {
                    val mapsActivity = Intent(this, MapsActivity::class.java)
                    startActivity(mapsActivity)
                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                    true
                }
                R.id.page_3 -> {
                    Toast.makeText(this, "Ikke implementeret", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.page_4 -> {
                    Toast.makeText(this, "Ikke implementeret", Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigation.menu.getItem(0).isChecked = true
    }
}