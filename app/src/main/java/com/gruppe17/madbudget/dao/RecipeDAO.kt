package com.gruppe17.madbudget.dao

import androidx.room.*
import com.gruppe17.madbudget.models.Recipe
import com.gruppe17.madbudget.models.RecipeWithIngredientSelections

@Dao
interface RecipeDAO {
    @Query("SELECT * FROM Recipe")
    fun getAll(): List<RecipeWithIngredientSelections>

    @Query("SELECT * FROM Recipe WHERE recipeId IN (:recipeIds)")
    fun getAllByIds(recipeIds: IntArray): List<RecipeWithIngredientSelections>

    @Query("SELECT * FROM Recipe WHERE recipeId = :recipeId")
    fun getById(recipeId: Int): RecipeWithIngredientSelections?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe) : Long

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