package com.gruppe17.madbudget

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.JobIntentService
import com.gruppe17.madbudget.models.Recipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DBDeleteService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val db = DatabaseBuilder.get(this)
        val id = intent.getIntExtra("RecipeID", -1)
        db.recipeDao().deleteById(id)
    }
}