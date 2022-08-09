package com.store.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.store.repository.ProductRepository

class ProductDetailsViewModel (
    productId: Long,
    repository: ProductRepository
) : ViewModel() {

    val productFound = repository.findById(productId)

}
