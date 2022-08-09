package com.store.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["productId"]
    )],
    indices = [Index("productId")]
)
class Payment(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardNumber: Int,
    val cardExpiration: String,
    val cvc: Int,
    val price: BigDecimal,
    val productId: Long
)
