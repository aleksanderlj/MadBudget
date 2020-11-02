package com.example.madbudget.dao

import androidx.room.*
import com.example.madbudget.models.Ingredient

@Dao
interface IngredientDAO {
    @Query("SELECT * FROM Ingredient")
    fun getAll(): List<Ingredient>

    @Query("SELECT * FROM Ingredient WHERE ingredientId IN (:ingredientIds)")
    fun getAllByIds(ingredientIds: IntArray): List<Ingredient>

    @Query("SELECT * FROM Ingredient WHERE ingredientId = :ingredientId")
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
}