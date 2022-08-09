package com.store.repository

import androidx.lifecycle.LiveData
import com.store.database.dao.ProductDao
import com.store.model.Product

class ProductRepository(private val dao: ProductDao) {

    fun findAll(): LiveData<List<Product>> = dao.findAll()

    fun findById(id: Long): LiveData<Product> = dao.findById(id)

}
