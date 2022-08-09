package com.store.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {

    @TypeConverter
    fun toBigDecimal(value: BigDecimal?): String = value?.toString() ?: ""

    @TypeConverter
    fun toString(value: String?): BigDecimal = value?.let { BigDecimal(it) } ?: BigDecimal.ZERO
    
}