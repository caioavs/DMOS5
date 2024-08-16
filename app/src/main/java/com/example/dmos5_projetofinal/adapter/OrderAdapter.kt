package com.example.dmos5_projetofinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dmos5_projetofinal.R
import com.example.dmos5_projetofinal.model.Order

class OrderAdapter(private val context: Context, private val orders: List<Order>, private val container: LinearLayout) {

    fun populateOrders() {
        val inflater = LayoutInflater.from(context)
        orders.forEach { order ->
            val itemView = inflater.inflate(R.layout.item_order, container, false)
            bindOrder(itemView, order)
            container.addView(itemView)
        }
    }

    private fun bindOrder(view: View, order: Order) {
        view.findViewById<TextView>(R.id.order_id).text = "Pedido ID: ${order.id}"
        view.findViewById<TextView>(R.id.main_dish).text = "Prato Principal: ${order.pratoPrincipal?.descricao ?: "N/A"}"
        view.findViewById<TextView>(R.id.additional_dish).text = "Prato Adicional: ${order.pratoAdicional?.descricao ?: "N/A"}"
        view.findViewById<TextView>(R.id.drink).text = "Bebida: ${order.bebida?.descricao ?: "N/A"}"
        view.findViewById<TextView>(R.id.observations).text = "Observações: ${order.observacoes}"
        view.findViewById<TextView>(R.id.status).text = "Status: ${order.status}"
        view.findViewById<TextView>(R.id.creation_date).text = "Data: ${order.dataCriacao.toString()}"
    }
}
