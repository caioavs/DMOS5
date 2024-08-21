package com.example.dmos5_projetofinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dmos5_projetofinal.R
import com.example.dmos5_projetofinal.model.Order

class OrderAdapter(private val context: Context, private val orders: List<Order>, private val container: LinearLayout) {

    fun populateOrders() {
        val inflater = LayoutInflater.from(context)
        orders.forEach { order ->
            val itemView = inflater.inflate(R.layout.order_card, container, false)
            bindOrder(itemView, order)
            container.addView(itemView)
        }
    }

    private fun bindOrder(view: View, order: Order) {
        view.findViewById<TextView>(R.id.tvId).text = "#${order.id}"
        view.findViewById<TextView>(R.id.tvPratoPrincipal).text = "Prato principal: ${order.pratoPrincipal?.descricao ?: "N/A"}"
        view.findViewById<TextView>(R.id.tvPratoAdicional).text = "Prato adicional: ${order.pratoAdicional?.descricao ?: "N/A"}"
        view.findViewById<TextView>(R.id.tvBebida).text = "Bebida: ${order.bebida?.descricao ?: "N/A"}"
        view.findViewById<TextView>(R.id.tvObservacoes).text = "Observações: ${order.observacoes}"
        view.findViewById<TextView>(R.id.tvStatus).text = "Status: ${order.status}"
        view.findViewById<TextView>(R.id.tvDt).text = "${order.dt}"
    }

}
