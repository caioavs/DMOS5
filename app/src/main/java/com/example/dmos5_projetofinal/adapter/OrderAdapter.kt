package com.example.dmos5_projetofinal.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.dmos5_projetofinal.R
import com.example.dmos5_projetofinal.model.Order
import com.example.dmos5_projetofinal.ui.NewOrderActivity
import java.text.SimpleDateFormat
import java.util.Locale

class OrderAdapter(private val context: Context, private val orders: List<Order>, private val container: LinearLayout) {

    fun populateOrders() {
        val inflater = LayoutInflater.from(context)
        orders.forEach { order ->
            val itemView = inflater.inflate(R.layout.order_card, container, false)

            editOrder(itemView)

            setStatusLabel(itemView, order)

            bindOrder(itemView, order)
            container.addView(itemView)
        }
    }

    private fun bindOrder(view: View, order: Order) {
        val id = view.findViewById<TextView>(R.id.tvId)
        id.text = "${id.text}${order.id}"

        val pratoPrincipal = view.findViewById<TextView>(R.id.tvPratoPrincipal)
        pratoPrincipal.text = "${order.pratoPrincipal?.descricao}"

        val pratoAdicional = view.findViewById<TextView>(R.id.tvPratoAdicional)
        pratoAdicional.text = "${order.pratoAdicional?.descricao}"

        val bebida = view.findViewById<TextView>(R.id.tvBebida)
        bebida.text = "${order.bebida?.descricao}"

        val observacoes = view.findViewById<TextView>(R.id.tvObservacoes)
        observacoes.text = "${order.observacoes}"

//        val status = view.findViewById<TextView>(R.id.tvStatus)
//        status.text = order.status?.name
//            ?.replace("_", " ")
//            ?.lowercase()
//            ?.replaceFirstChar { it.uppercase() }
//
        val dt = view.findViewById<TextView>(R.id.tvDt)
        val dtFormat = SimpleDateFormat("EEEE, dd/MM/yy HH:mm", Locale("pt", "BR"))
        dt.text = "${dt.text}${dtFormat.format(order.dt)}"
    }

    private fun editOrder(itemView: View) {
        val cardView: CardView = itemView.findViewById(R.id.orderCard)
        cardView.setOnClickListener {
            val intent = Intent(context, NewOrderActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun setStatusLabel(itemView: View, order: Order) {
        val tvStatusLabel: TextView = itemView.findViewById(R.id.tvStatusLabel)
        when(order.status) {
            Order.Status.EM_ESPERA -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.brown_lighter))
            Order.Status.EM_ANDAMENTO -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.blue_light))
            Order.Status.PRONTO_PARA_RETIRADA -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.yellow))
            Order.Status.CONCLUIDO -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.green))
            else -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

}
