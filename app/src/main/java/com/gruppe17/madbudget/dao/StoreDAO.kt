package com.gruppe17.madbudget.dao

import androidx.room.*

import com.gruppe17.madbudget.models.Store

@Dao
interface StoreDAO {

    @Transaction
    @Query("SELECT * FROM Store")
    fun getAllStores(): List<Store>

    @Transaction
    @Query("SELECT * FROM Store where isSelected = 1")
    fun getAllSelectedStores(): List<Store>

    @Transaction
    @Query("SELECT * FROM Store WHERE storeId = :storeId")
    fun getStoreById(storeId: Int): Store

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(store: Store): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(stores: List<Store>): List<Long>

    @Update
    fun update(store: Store)

    @Update
    fun updateAll(stores: List<Store>)

    @Delete
    fun delete(store: Store)

    @Delete
    fun deleteAll(stores: List<Store>)

    @Transaction
    @Query("DELETE FROM Store")
    fun deleteAllExisting()

}