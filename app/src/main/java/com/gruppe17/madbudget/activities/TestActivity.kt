package com.gruppe17.madbudget.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.database.DatabaseBuilder
import kotlinx.android.synthetic.main.activity_sallingtest.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testyboi)

        getButton.setOnClickListener {
            getText.text = "Check logcat"
            val db = DatabaseBuilder.get(this)

            /*
            CoopCommunicator.getNearbyStoresMapOptimized(this, 10000, 1, 1000) {response ->
                Log.i("Stores", response.toString())
                val json = Utility.parse<CoopStoreList>(response.toString())

                //val stores = ArrayList<CoopStore>()
                //stores.add(CoopStore(1, "", "", "", 0, "", 0, CoopLocation(listOf(0.0, 0.0)), listOf(CoopOpeningHour("", "", 0.0, 0.0, "", ""))))

                for(i in json!!.stores){
                    Log.i("Testag","stores.add(CoopStore(${i.kardex}, \"${i.retailGroup}\", \"${i.name}\", \"${i.address}\", ${i.zipcode}, \"${i.city}\", ${i.storeId}, CoopLocation(listOf(${i.location.coordinates[0]}, ${i.location.coordinates[1]})), listOf(CoopOpeningHour(\"${i.openingHours[0].text}\", \"${i.openingHours[0].day}\", ${i.openingHours[0].from}, ${i.openingHours[0].to}, \"${i.openingHours[0].fromDate}\", \"${i.openingHours[0].toDate}\"))))")
                }

                //val storeList = CoopStoreList(1, 1, 1000, 1000, stores, 1, "")
            }

             */


            /*
            val ing = Ingredient()
            val ing2 = Ingredient(0, "test", 100.0, "testkg", 5, "type", true, 500.0, -5)

            GlobalScope.launch {
                //db.ingredientDao().insert(ing)
                db.ingredientDao().insert(ing2)

                val ingList = db.ingredientDao().getAll()

                for (n in ingList){
                    Log.i("ing1", ing.toString())
                    Log.i("ing2", ing2.toString())
                }

            }

             */


            /*
            CoopCommunicator.getAssortment(this, "1290") {response ->
                Log.i("Assortment", response.toString())
                val assortments = Utility.parseArray<CoopProduct>(response.toString())
                assortments!!
                Log.i("JSONTEST", assortments[0].name1)

                for(n in 0..100){
                    val i = RegexFilter.convertCoopIngredient(assortments[n])
                    Log.i("RegObject", "l.add(Ingredient(0, \"${i.ingredientName}\", ${i.amount}, \"${i.unit}\", ${i.pieces}, null, false, ${i.ingredientPrice}, 0))")
                }

            }

             */

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