package com.gruppe17.madbudget.database.firestore

import android.content.Context
import com.beust.klaxon.Klaxon
import com.gruppe17.madbudget.rest.coop.CoopCommunicator
import com.gruppe17.madbudget.rest.coop.model.CoopStore
import com.gruppe17.madbudget.rest.coop.model.CoopStoreList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gruppe17.madbudget.Utility
import com.gruppe17.madbudget.models.Store
import com.gruppe17.madbudget.rest.coop.model.CoopProduct
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

object Coop2Firebase {
    fun insertStoreAssortment(context: Context, store: Store) {
        val fs = Firebase.firestore

        val storeDoc = fs.collection("Assortments").document(store.kardex.toString())

        val curTime = Calendar.getInstance().timeInMillis
        storeDoc.get().addOnSuccessListener { document ->
            var docTime = document["updated"] as Long?
            if (!document.exists()){
                val time = hashMapOf(
                    "updated" to curTime
                )
                storeDoc.set(time)
                docTime = 0
            }

            val timeDifference = curTime - docTime!!

            if (TimeUnit.MILLISECONDS.toDays(timeDifference) > 7){
                CoopCommunicator.getAssortment(context, store.kardex.toString()) { response ->
                    val assortment = Utility.parseArray<CoopProduct>(response)

                    val collection = storeDoc.collection("Products")

                    for (i in assortment!!) {
                        collection.document(i.Ean).set(i)
                    }

                }
            }
        }

    }

    fun insertAllStores(context: Context) {
        val db = Firebase.firestore

        CoopCommunicator.getNearbyStoresMapOptimized(context, 10000, 1, 100) { response ->
            val allStores = Klaxon().parse<CoopStoreList>(response.toString())
            val stores = ArrayList<CoopStore>()
            var kvickly = false
            var brugsen = false

            // This is an intended limit because of the firebase
            for (i in allStores?.Data!!) {
                if (!kvickly && i.R == "Kvickly") {
                    stores.add(i)
                    kvickly = true
                } else if (!brugsen && i.R == "SuperBrugsen") {
                    stores.add(i)
                    brugsen = true
                } else if (brugsen && kvickly) {
                    break
                }
            }


            // Place all stores in firebase and the assortment of two chosen stores. Have a static variable with their id's


        }

        CoopCommunicator.getAllStoresMapOptimized(context, 1, 50) { response ->
            val stores = Klaxon().parse<CoopStoreList>(response.toString())
            db.collection("stores").document()
        }

    }

}