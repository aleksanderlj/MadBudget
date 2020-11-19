package com.gruppe17.madbudget.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gruppe17.madbudget.database.dao.IngredientDAO
import com.gruppe17.madbudget.database.dao.IngredientSelectionDAO
import com.gruppe17.madbudget.database.dao.RecipeDAO
import com.gruppe17.madbudget.database.dao.StoreDAO
import com.gruppe17.madbudget.models.Ingredient
import com.gruppe17.madbudget.models.IngredientSelection
import com.gruppe17.madbudget.models.Recipe
import com.gruppe17.madbudget.models.Store

@Database(entities = [Ingredient::class, Recipe::class, IngredientSelection::class, Store::class], version = 10, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDAO
    abstract fun recipeDao(): RecipeDAO
    abstract fun ingredientSelectionDao(): IngredientSelectionDAO
    abstract fun storeDAO(): StoreDAO
}