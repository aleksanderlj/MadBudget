package com.gruppe17.madbudget.database.firestore

import android.content.Context
import com.beust.klaxon.Klaxon
import com.gruppe17.madbudget.rest.coop.CoopCommunicator
import com.gruppe17.madbudget.rest.coop.model.CoopStore
import com.gruppe17.madbudget.rest.coop.model.CoopStoreList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Coop2Firebase {
    companion object{
        fun insertStores(context: Context){
            val db = Firebase.firestore

            CoopCommunicator.getNearbyStoresMapOptimized(context, 10000, 1, 100) {response ->
                val allStores = Klaxon().parse<CoopStoreList>(response.toString())
                val stores = ArrayList<CoopStore>()
                var kvickly = false
                var brugsen = false

                // This is an intended limit because of the firebase
                for (i in allStores?.stores!!){
                    if (!kvickly && i.retailGroup == "Kvickly"){
                        stores.add(i)
                        kvickly = true
                    } else if (!brugsen && i.retailGroup == "SuperBrugsen"){
                        stores.add(i)
                        brugsen = true
                    } else if (brugsen && kvickly){
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
}