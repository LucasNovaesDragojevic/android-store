package com.store.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.store.database.dao.PaymentDao
import com.store.model.Payment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PaymentRepository(private val dao: PaymentDao) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun save(payment: Payment): LiveData<Resource<Long>> {
        return MutableLiveData<Resource<Long>>().also { liveDate ->
            scope.launch {
                val idPayment = dao.save(payment)
                liveDate.postValue(Resource(idPayment))
            }
        }
    }

}
