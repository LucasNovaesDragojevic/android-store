package com.store.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.store.model.Payment

@Dao
interface PaymentDao {

    @Insert
    fun save(payment: Payment) : Long
}