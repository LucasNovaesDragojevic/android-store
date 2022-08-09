package com.store.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.store.database.converter.BigDecimalConverter
import com.store.database.dao.PaymentDao
import com.store.database.dao.ProductDao
import com.store.model.Payment
import com.store.model.Product

@Database(
    version = 2,
    entities = [Product::class, Payment::class],
    exportSchema = false
)
@TypeConverters(BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun paymentDao(): PaymentDao
}