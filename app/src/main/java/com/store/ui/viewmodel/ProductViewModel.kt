package com.store.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.store.model.Product
import com.store.repository.ProductRepository

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    fun findAll(): LiveData<List<Product>> = repository.findAll()

}
