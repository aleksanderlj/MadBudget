package com.gruppe17.madbudget.database

import android.content.Intent
import androidx.core.app.JobIntentService

class DBDeleteService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val db = DatabaseBuilder.get(this)
        val id = intent.getIntExtra("RecipeID", -1)
        db.recipeDao().deleteById(id)
    }
}