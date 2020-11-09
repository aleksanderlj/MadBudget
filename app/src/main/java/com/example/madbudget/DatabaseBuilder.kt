package com.example.madbudget

import android.content.Context
import androidx.room.Room

class DatabaseBuilder {
    companion object{
        private var db: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            if(db == null){
                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "MadBudgetDatabase"
                ).fallbackToDestructiveMigration().
                build()
            }
            return db as AppDatabase
        }
    }
}