package com.store.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.store.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun findAll(): LiveData<List<Product>>

    @Insert
    fun save(vararg product: Product)

    @Query("SELECT * FROM Product WHERE id = :id")
    fun findById(id: Long): LiveData<Product>

}