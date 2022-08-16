package com.store.repository

import androidx.lifecycle.LiveData
import com.store.model.Payment

class PaymentRepository() {


    fun save(payment: Payment): LiveData<Resource<Long>> {
        TODO("not implemented payment insertion")
    }

    fun findAll() { TODO("not implemented search of payments") }
}
