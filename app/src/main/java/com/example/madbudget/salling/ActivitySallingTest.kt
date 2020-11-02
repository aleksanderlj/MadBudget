package com.example.madbudget.salling

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.example.madbudget.DatabaseBuilder
import com.example.madbudget.R
import com.example.madbudget.coop.CoopCommunicator
import com.example.madbudget.coop.model.CoopProduct
import com.example.madbudget.coop.model.CoopStoreList
import com.example.madbudget.models.Ingredient
import com.example.madbudget.salling.jsonModels.JsonDiscount
import com.example.madbudget.salling.jsonModels.JsonProduct
import com.example.madbudget.salling.jsonModels.JsonStore
import com.example.madbudget.salling.jsonModels.JsonSuggestions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sallingtest.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class ActivitySallingTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sallingtest)

        getButton.setOnClickListener {
            getText.text = "Check logcat"
            val db = DatabaseBuilder.get(this)

            /*
            GlobalScope.launch {
                db.ingredientDao().insert(Ingredient(0, "name", "amount", "type", false, 0))
                getText.text = db.ingredientDao().getAll()[0].ingredientName
            }
             */



            /*
            CoopCommunicator.getNearbyStoresMapOptimized(this, 10000, 1, 2) {response ->
                Log.i("Stores", response.toString())
                val json = Klaxon().parse<CoopStoreList>(response.toString())
                Log.i("JSONTEST", json!!.stores[0].manager)
            }

            CoopCommunicator.getProducts(this, "1290") {response ->
                Log.i("Products", response.toString())
                val json = Klaxon().parseArray<CoopProduct>(response.toString())
                Log.i("JSONTEST", json!![0].name1)
            }

            CoopCommunicator.getAssortment(this, "1290") {response ->
                Log.i("Assortment", response.toString())
                val json = Klaxon().parseArray<CoopProduct>(response.toString())
                Log.i("JSONTEST", json!![0].name1)
            }

             */

            /*
            SallingCommunicator.getNearbyStores(this, 20) { response ->
                Log.i("Stores", response.toString())
                val json = Klaxon().parseArray<JsonStore>(response.toString())
                Log.i("JSONTEST", json!![0].address.city)
            }

            SallingCommunicator.getProductSuggestions(this, "laks") { response ->
                Log.i("Suggestions", response.toString())
                val json = Klaxon().parse<JsonSuggestions>(response.toString())
                Log.i("JSONTEST", json!!.suggestions[0].title)
            }

            SallingCommunicator.getSimilarProducts(this, "31802") { response ->
                Log.i("Similar", response.toString())
                val json = Klaxon().parseArray<JsonProduct>(response.toString())
                Log.i("JSONTEST", json!![0].title)
            }

            SallingCommunicator.getNearbyDiscounts(this, 10) { response ->
                Log.i("Discounts", response.toString())
                val json = Klaxon().parseArray<JsonDiscount>(response.toString())
                Log.i("JSONTEST", json!![0].clearances!![0].offer.discount.toString())
            }

             */

        }

    }
}