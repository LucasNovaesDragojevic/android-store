package com.store.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

class Payment(
    val cardNumber: Int,
    val cardExpiration: String,
    val cvc: Int,
    val price: BigDecimal,
    val productId: Long
)
