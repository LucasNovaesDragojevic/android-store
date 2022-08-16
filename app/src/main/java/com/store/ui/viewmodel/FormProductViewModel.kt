package com.store.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.store.model.Product
import com.store.repository.ProductRepository

class FormProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    fun save(product: Product): LiveData<Boolean> = repository.save(product)
    fun findById(id: String): LiveData<Product> = repository.findById(id)

}
