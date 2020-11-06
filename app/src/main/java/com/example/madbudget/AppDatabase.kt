package com.example.madbudget

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.madbudget.dao.IngredientDAO
import com.example.madbudget.dao.IngredientSelectionDAO
import com.example.madbudget.dao.RecipeDAO
import com.example.madbudget.models.Ingredient
import com.example.madbudget.models.IngredientSelection
import com.example.madbudget.models.Recipe

@Database(entities = [Ingredient::class, Recipe::class, IngredientSelection::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDAO
    abstract fun recipeDao(): RecipeDAO
    abstract fun ingredientSelectionDao(): IngredientSelectionDAO
}