package com.store.model

import java.math.BigDecimal

class Product(
    val id: String? = null,
    val name: String = "",
    val price: BigDecimal = BigDecimal.ZERO
)