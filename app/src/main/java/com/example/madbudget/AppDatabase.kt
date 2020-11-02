package com.example.madbudget

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.madbudget.dao.IngredientDAO
import com.example.madbudget.dao.RecipeDAO
import com.example.madbudget.models.Ingredient
import com.example.madbudget.models.Recipe

@Database(entities = [Ingredient::class, Recipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDAO
    abstract fun recipeDao(): RecipeDAO
}