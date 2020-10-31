package com.example.madbudget.dao

import androidx.room.*
import com.example.madbudget.models.Ingredient
import com.example.madbudget.models.Recipe
import com.example.madbudget.models.RecipeWithIngredients

@Dao
interface RecipeDAO {
    @Query("SELECT * FROM Recipe")
    fun getAll(): List<RecipeWithIngredients>

    @Query("SELECT * FROM Recipe WHERE recipeId IN (:recipeIds)")
    fun getAllByIds(recipeIds: IntArray): List<RecipeWithIngredients>

    @Query("SELECT * FROM Recipe WHERE recipeId = :recipeId")
    fun getById(recipeId: Int): RecipeWithIngredients?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<Recipe>)

    @Update
    fun update(recipe: Recipe)

    @Update
    fun updateAll(recipes: List<Recipe>)

    @Delete
    fun delete(recipe: Recipe)

    @Delete
    fun deleteAll(recipes: List<Recipe>)
}