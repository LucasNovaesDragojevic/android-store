package com.store.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.store.repository.ProductRepository

class ProductDetailsViewModel (
    private val productId: String,
    private val repository: ProductRepository
) : ViewModel() {

    fun delete() = repository.delete(productId)
    val productFound = repository.findById(productId)

}
