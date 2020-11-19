package com.gruppe17.madbudget.database.dao

import androidx.room.*
import com.gruppe17.madbudget.models.Recipe
import com.gruppe17.madbudget.models.RecipeWithIngredientSelections

@Dao
interface RecipeDAO {
    @Transaction
    @Query("SELECT * FROM Recipe")
    fun getAll(): List<RecipeWithIngredientSelections>

    @Transaction
    @Query("SELECT * FROM Recipe WHERE recipe_id IN (:recipeIds)")
    fun getAllByIds(recipeIds: IntArray): List<RecipeWithIngredientSelections>

    @Transaction
    @Query("SELECT * FROM Recipe WHERE recipe_id = :recipeId")
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

    @Query("DELETE FROM Recipe WHERE recipe_id = :id")
    fun deleteById(id: Int)
}