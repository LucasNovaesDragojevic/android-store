package com.store.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.store.model.Product
import java.math.BigDecimal
import java.math.RoundingMode
class ProductRepository(
    private val firestore: FirebaseFirestore
) {

    fun findById(id: String): LiveData<Product> =
        MutableLiveData<Product>().apply {
            firestore.collection(PRODUCTS)
                .document(id)
                .addSnapshotListener { s, _ ->
                    s?.let { document ->
                        document.toObject<ProductToDocument>()
                            ?.toProduct(document.id)
                            ?.let { product ->
                                value = product
                            }
                    }
                }
        }



    fun findAll() =
        MutableLiveData<List<Product>>().apply {
            firestore.collection(PRODUCTS)
                .addSnapshotListener { snapshot, _ ->
                    snapshot?.let { querySnapshot ->
                        value = querySnapshot.documents.mapNotNull { document ->
                            document.toObject<ProductToDocument>()?.toProduct(document.id)
                        }
                    }
                }
        }


    fun save(product: Product) =
        MutableLiveData<Boolean>().apply {
            val productToDocument = ProductToDocument(name = product.name, price = product.price.toDouble())

            val collection = firestore.collection(PRODUCTS)

            val document = product.id?.let { id ->
                collection.document(id)
            } ?: collection.document()

            document.set(productToDocument)

            value = true
        }

    fun delete(productId: String): LiveData<Boolean> =
        MutableLiveData<Boolean>().apply {
            firestore.collection(PRODUCTS)
                .document(productId)
                .delete()
            value = true
        }




    private class ProductToDocument(
        val name: String = "",
        val price: Double = 0.0
    ) {
        fun toProduct(id: String): Product = Product(id = id, name = name, price = BigDecimal(price).setScale(2, RoundingMode.HALF_EVEN))
    }

}

private const val PRODUCTS = "products"

