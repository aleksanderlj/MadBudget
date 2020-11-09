package com.gruppe17.madbudget.salling

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gruppe17.madbudget.DatabaseBuilder
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.RegexFilter
import com.gruppe17.madbudget.Utility
import com.gruppe17.madbudget.coop.CoopCommunicator
import com.gruppe17.madbudget.coop.model.CoopProduct
import kotlinx.android.synthetic.main.activity_sallingtest.*

class ActivitySallingTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sallingtest)

        getButton.setOnClickListener {
            getText.text = "Check logcat"
            val db = DatabaseBuilder.get(this)

            CoopCommunicator.getAssortment(this, "1290") {response ->
                Log.i("Assortment", response.toString())
                val assortments = Utility.parseArray<CoopProduct>(response.toString())
                assortments!!
                Log.i("JSONTEST", assortments[0].name1)

                val ing = RegexFilter.convertCoopIngredient(assortments[0])

                Log.i("RegexTest", "Name=${ing.ingredientName}, Amount=${ing.amount}, Unit=${ing.unit}, Pieces=${ing.pieces}, Price=${ing.ingredientPrice},")

            }

            /*
            GlobalScope.launch {
                db.ingredientDao().insert(Ingredient(0, "name", "amount", "type", false, 0,0.0))
                getText.text = db.ingredientDao().getAll()[0].ingredientName
            }
             */


            /*
            CoopCommunicator.getNearbyStoresMapOptimized(this, 1, 1, 2) {response ->
                Log.i("Stores", response.toString())
                val json = Utility.parse<CoopStoreList>(response.toString())
                Log.i("JSONTEST", json!!.stores[0].manager)
            }

            CoopCommunicator.getProducts(this, "1290") {response ->
                Log.i("Products", response.toString())
                val json = Utility.parseArray<CoopProduct>(response.toString())
                Log.i("JSONTEST", json!![0].name1)
            }

            CoopCommunicator.getAssortment(this, "1290") {response ->
                Log.i("Assortment", response.toString())
                val json = Utility.parseArray<CoopProduct>(response.toString())
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