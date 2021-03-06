package com.gruppe17.madbudget.database.dao

import androidx.room.*
import com.gruppe17.madbudget.models.Ingredient

@Dao
interface IngredientDAO {
    @Query("SELECT * FROM Ingredient")
    fun getAll(): List<Ingredient>

    @Query("SELECT * FROM Ingredient WHERE ingredient_id IN (:ingredientIds)")
    fun getAllByIds(ingredientIds: IntArray): List<Ingredient>

    @Query("SELECT * FROM Ingredient WHERE ingredient_selection_parent_id = :recipeId")
    fun getAllByIngredientSelectionId(recipeId: Int): List<Ingredient>

    @Query("SELECT * FROM Ingredient WHERE ingredient_id = :ingredientId")
    fun getById(ingredientId: Int): Ingredient?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: Ingredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredients: List<Ingredient>)

    @Update
    fun update(ingredient: Ingredient)

    @Update
    fun updateAll(ingredients: List<Ingredient>)

    @Delete
    fun delete(ingredient: Ingredient)

    @Delete
    fun deleteAll(ingredients: List<Ingredient>)

    @Query("DELETE FROM Ingredient WHERE ingredient_selection_parent_id = :parentId")
    fun deleteByParentId(parentId: Int)
}