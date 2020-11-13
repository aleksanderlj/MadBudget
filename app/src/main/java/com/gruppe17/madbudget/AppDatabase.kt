package com.gruppe17.madbudget

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gruppe17.madbudget.dao.IngredientDAO
import com.gruppe17.madbudget.dao.IngredientSelectionDAO
import com.gruppe17.madbudget.dao.RecipeDAO
import com.gruppe17.madbudget.models.Ingredient
import com.gruppe17.madbudget.models.IngredientSelection
import com.gruppe17.madbudget.models.Recipe

@Database(entities = [Ingredient::class, Recipe::class, IngredientSelection::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDAO
    abstract fun recipeDao(): RecipeDAO
    abstract fun ingredientSelectionDao(): IngredientSelectionDAO
}