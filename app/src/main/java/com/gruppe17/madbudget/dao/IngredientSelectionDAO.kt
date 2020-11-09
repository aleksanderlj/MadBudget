package com.gruppe17.madbudget.dao

import com.gruppe17.madbudget.models.IngredientSelection
import com.gruppe17.madbudget.models.IngredientSelectionWithIngredients

import androidx.room.*

@Dao
interface IngredientSelectionDAO {
    @Transaction
    @Query("SELECT * FROM IngredientSelection")
    fun getAll(): List<IngredientSelectionWithIngredients>

    @Transaction
    @Query("SELECT * FROM IngredientSelection WHERE ingredientSelectionId IN (:ingredientSelectionIds)")
    fun getAllByIds(ingredientSelectionIds: IntArray): List<IngredientSelectionWithIngredients>

    @Transaction
    @Query("SELECT * FROM IngredientSelection WHERE recipe_parent_id = :recipeId")
    fun getAllByIngredientSelectionId(recipeId: Int): List<IngredientSelectionWithIngredients>

    @Transaction
    @Query("SELECT * FROM IngredientSelection WHERE recipe_parent_id = :recipeId")
    fun getAllByRecipeId(recipeId: Int): IngredientSelectionWithIngredients?

    @Transaction
    @Query("SELECT * FROM IngredientSelection WHERE ingredientSelectionId = :ingredientSelectionId")
    fun getById(ingredientSelectionId: Int): IngredientSelectionWithIngredients?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredientSelection: IngredientSelection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredientSelection: List<IngredientSelection>)

    @Update
    fun update(ingredientSelection: IngredientSelection)

    @Update
    fun updateAll(ingredientSelection: List<IngredientSelection>)

    @Delete
    fun delete(ingredientSelection: IngredientSelection)

    @Delete
    fun deleteAll(ingredientSelection: List<IngredientSelection>)

    @Query("DELETE FROM IngredientSelection WHERE recipe_parent_id = :parentId")
    fun deleteByParentId(parentId: Int)
}