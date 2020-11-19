package com.gruppe17.madbudget.database.dao

import com.gruppe17.madbudget.models.IngredientSelection
import com.gruppe17.madbudget.models.IngredientSelectionWithIngredients

import androidx.room.*

@Dao
interface IngredientSelectionDAO {
    @Transaction
    @Query("SELECT * FROM IngredientSelection")
    fun getAll(): List<IngredientSelectionWithIngredients>

    @Transaction
    @Query("SELECT * FROM IngredientSelection WHERE ingredient_selection_id IN (:ingredientSelectionIds)")
    fun getAllByIds(ingredientSelectionIds: IntArray): List<IngredientSelectionWithIngredients>

    @Transaction
    @Query("SELECT * FROM IngredientSelection WHERE recipe_parent_id = :recipeId")
    fun getAllByRecipeId(recipeId: Int): List<IngredientSelectionWithIngredients>

    @Transaction
    @Query("SELECT * FROM IngredientSelection WHERE ingredient_selection_id = :ingredientSelectionId")
    fun getById(ingredientSelectionId: Int): IngredientSelectionWithIngredients?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredientSelection: IngredientSelection): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredientSelection: List<IngredientSelection>): List<Long>

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