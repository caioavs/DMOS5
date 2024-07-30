package com.example.dmos5_projetofinal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dmos5_projetofinal.model.Order
import com.google.firebase.firestore.FirebaseFirestore

class OrderRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getAllOrders(employeeId: String): LiveData<List<Order>> {
        val ordersLiveData = MutableLiveData<List<Order>>()

        db.collection("orders")
            .whereEqualTo("employeeId", employeeId)
            .get()
            .addOnSuccessListener { result ->
                val orders = result.map { document ->
                    document.toObject(Order::class.java)
                }
                ordersLiveData.value = orders
            }
            .addOnFailureListener { exception ->
                ordersLiveData.value = emptyList()
            }

        return ordersLiveData
    }

    suspend fun deleteOrder(order: Order) {
        db.collection("orders").document(order.id).delete()
    }
}
