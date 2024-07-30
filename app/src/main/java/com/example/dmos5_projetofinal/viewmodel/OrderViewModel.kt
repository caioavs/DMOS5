package com.example.dmos5_projetofinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dmos5_projetofinal.model.Order
import com.example.dmos5_projetofinal.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val orderRepository = OrderRepository()

    fun allOrders(employeeId: String): LiveData<List<Order>> {
        return orderRepository.getAllOrders(employeeId)
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            orderRepository.deleteOrder(order)
        }
    }
}
