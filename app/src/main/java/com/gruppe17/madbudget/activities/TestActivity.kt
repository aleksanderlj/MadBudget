package com.gruppe17.madbudget.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gruppe17.madbudget.R
import com.gruppe17.madbudget.Utility
import com.gruppe17.madbudget.database.DatabaseBuilder
import com.gruppe17.madbudget.database.firestore.Coop2Firebase
import com.gruppe17.madbudget.models.Store
import com.gruppe17.madbudget.rest.coop.CoopCommunicator
import com.gruppe17.madbudget.rest.coop.RegexFilter
import com.gruppe17.madbudget.rest.coop.model.CoopLocation
import com.gruppe17.madbudget.rest.coop.model.CoopOpeningHour
import com.gruppe17.madbudget.rest.coop.model.CoopProduct
import com.gruppe17.madbudget.rest.coop.model.CoopStore
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_testyboi.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testyboi)

        getButton.setOnClickListener {
            getText.text = "Start"
            val db = DatabaseBuilder.get(this)


            val testStore = CoopStore(1885, "Dagli'Brugsen", "Dagli'Brugsen Skyttegården", "Vigerslevvej 125", 2500, "Valby", 688, CoopLocation(listOf(12.4834814, 55.6618347)), listOf(
                CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00")))

            val ts = Store(0,testStore.N,testStore.K,testStore.A,testStore.S,false , 5.0f)
            Coop2Firebase.insertStoreAssortment(this, ts)

            /*
            val input = "[{" +
                    "  \"Ean\": \"2019160000001\"," +
                    "  \"Navn\": \"FLÆSKESTEG 1,2-2,4 KG\"," +
                    "  \"Navn2\": \"MED SVÆR\"," +
                    "  \"Pris\": 49.90," +
                    "  \"VareHierakiId\": 3021" +
                    "}, {" +
                    "  \"Ean\": \"2036830000000\"," +
                    "  \"Navn\": \"MÖRT GRYDESTEG\"," +
                    "  \"Navn2\": \"800-1600G\"," +
                    "  \"Pris\": 79.90," +
                    "  \"VareHierakiId\": 3021" +
                    "}]"



            val assortments = Utility.parseArray<CoopProduct>(input)!!

            val fs = Firebase.firestore
            val col = fs.collection("test")
            col.document(assortments[0].Ean).set(assortments[0])


            col.document(assortments[0].Ean).get().addOnSuccessListener { document ->
                if(document == null){
                    Log.i("FSTESTSuccess", "No such doc")
                } else {
                    val obj = document.toObject(CoopProduct::class.java)
                    Log.i("FSTESTSuccess", obj.toString())
                    Log.i("FSTESTSuccess", document.data.toString())
                }
            }.addOnFailureListener{ response ->
                Log.i("FSTESTFailure", response.toString())
            }.addOnCompleteListener{ task ->
                Log.i("FSTESTComplete", "Complete")
                val obj = task.result.toObject(CoopProduct::class.java)
                Log.i("FSTESTComplete", obj.toString())
                Log.i("FSTESTComplete", task.result.data.toString())
            }

            Log.i("FSTEST", "DONE")


             */

            /*
            CoopCommunicator.getAssortment(this, "1290") { response ->
                Log.i("Assortment", response.toString())

                val assortments = Utility.parseArray<CoopProduct>(response.toString())

                assortments!!

                for(i in assortments){
                    RegexFilter.convertCoopIngredient(i)
                }
                Log.i("jsontest", assortments[0].Navn)

            }

             */

            /*
            val input = "[{" +
                    "  \"Ean\": \"2019160000001\"," +
                    "  \"Navn\": \"FLÆSKESTEG 1,2-2,4 KG\"," +
                    "  \"Navn2\": \"MED SVÆR\"," +
                    "  \"Pris\": 49.90," +
                    "  \"VareHierakiId\": 3021" +
                    "}, {" +
                    "  \"Ean\": \"2036830000000\"," +
                    "  \"Navn\": \"MÖRT GRYDESTEG\"," +
                    "  \"Navn2\": \"800-1600G\"," +
                    "  \"Pris\": 79.90," +
                    "  \"VareHierakiId\": 3021" +
                    "}]"
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val listCoopProductType = Types.newParameterizedType(List::class.java, CoopProduct::class.java)
            val coopProductAdapter: JsonAdapter<List<CoopProduct>> = moshi.adapter(listCoopProductType)

            val assortments = coopProductAdapter.fromJson(input)

            assortments!!
            Log.i("JSONTEST", assortments[0].Navn)

             */

            /*
            CoopCommunicator.getAssortment(this, "1290") { response ->
                Log.i("Assortment", response.toString())

                val moshi = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()

                val listCoopProductType = Types.newParameterizedType(List::class.java, CoopProduct::class.java)
                val coopProductAdapter: JsonAdapter<List<CoopProduct>> = moshi.adapter(listCoopProductType)

                val assortments = coopProductAdapter.fromJson(response.toString())

                assortments!!
                Log.i("JSONTEST", assortments[0].Navn)

            }

             */


            /*
            CoopCommunicator.getAssortment(this, "1290") { response ->
                Log.i("Assortment", response.toString())
                val assortments = Utility.parseArray<CoopProduct>(response.toString())
                assortments!!
                Log.i("JSONTEST", assortments[0].name1)

                for(n in 0..100){
                    val i = RegexFilter.convertCoopIngredient(assortments[n])
                    Log.i("RegObject", "l.add(Ingredient(0, \"${i.name}\", ${i.amount}, \"${i.unit}\", ${i.pieces}, null, false, ${i.price}, 0))")
                }

            }

             */

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