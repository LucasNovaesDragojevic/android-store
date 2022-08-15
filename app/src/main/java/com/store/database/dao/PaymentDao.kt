package com.store.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.store.model.Payment

@Dao
interface PaymentDao {

    @Insert
    fun save(payment: Payment) : Long

    @Query("SELECT * FROM Payment")
    fun findAll(): LiveData<List<Payment>>
}