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
        val id = view.findViewById<TextView>(R.id.tvId)
        id.text = "${id.text}${order.id}"

        val pratoPrincipal = view.findViewById<TextView>(R.id.tvPratoPrincipal)
        pratoPrincipal.text = "${pratoPrincipal.text}${order.pratoPrincipal?.descricao}"

        val pratoAdicional = view.findViewById<TextView>(R.id.tvPratoAdicional)
        pratoAdicional.text = "${pratoAdicional.text}${order.pratoAdicional?.descricao}"

        val bebida = view.findViewById<TextView>(R.id.tvBebida)
        bebida.text = "${bebida.text}${order.bebida?.descricao}"

        val observacoes = view.findViewById<TextView>(R.id.tvObservacoes)
        observacoes.text = "${observacoes.text}${order.observacoes}"

        val status = view.findViewById<TextView>(R.id.tvStatus)
        status.text = "${status.text}${order.status}"

        val dt = view.findViewById<TextView>(R.id.tvDt)
        dt.text = "${dt.text}${order.dt}"
    }

}
