package com.store.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.store.model.Payment
import com.store.repository.PaymentRepository
import com.store.repository.ProductRepository

class PaymentViewModel(
    private val paymentRepository: PaymentRepository,
    private val productRepository: ProductRepository) : ViewModel() {

    fun save(payment: Payment) = paymentRepository.save(payment)
    fun findById(id: Long) = productRepository.findById(id)

}
