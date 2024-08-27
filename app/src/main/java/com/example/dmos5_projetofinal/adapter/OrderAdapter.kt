package com.example.dmos5_projetofinal.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
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
            setSpinnerStatus(itemView, order)
            setStatusLabel(itemView, order)
            setButtons(itemView, order)
            setVisibility(itemView, order)

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

        val dt = view.findViewById<TextView>(R.id.tvDt)
        val dtFormat = SimpleDateFormat("EEEE, dd/MM/yy HH:mm", Locale("pt", "BR"))
        dt.text = "${dt.text}${dtFormat.format(order.dt)}"

        val status = view.findViewById<TextView>(R.id.tvStatus)
        status.text = "${status.text}${order.status}"
        status.text = order.status?.name
            ?.replace("_", " ")
            ?.lowercase()
            ?.replaceFirstChar { it.uppercase() }
    }

    private fun editOrder(itemView: View) {
        val imageView: ImageView = itemView.findViewById(R.id.ImgEditOrder)
        imageView.setOnClickListener {
            val intent = Intent(itemView.context, NewOrderActivity::class.java)
            itemView.context.startActivity(intent)
        }
    }

    private fun setSpinnerStatus(itemView: View, order: Order) {
        val spinner: Spinner = itemView.findViewById(R.id.spStatus)

        val filteredStatusValues = Order.Status.values()
            .filter { it != Order.Status.CONCLUÍDO && it != Order.Status.CANCELADO }
            .map { it.name.replace("_", " ").lowercase().replaceFirstChar { char -> char.uppercase() } }

        val adapter = ArrayAdapter(context, R.layout.spinner_item, filteredStatusValues)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        spinner.adapter = adapter

        order.status?.let { status ->
            val statusString = status.name.replace("_", " ").lowercase().replaceFirstChar { char -> char.uppercase() }
            val position = filteredStatusValues.indexOf(statusString)
            if(position >= 0) {
                spinner.setSelection(position)
            }
        }
    }

    private fun setStatusLabel(itemView: View, order: Order) {
        val tvStatusLabel: TextView = itemView.findViewById(R.id.tvStatusLabel)
        when(order.status) {
            Order.Status.EM_ESPERA -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.brown_lighter))
            Order.Status.EM_ANDAMENTO -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.blue_light))
            Order.Status.PRONTO_PARA_RETIRADA -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.yellow))
            Order.Status.CONCLUÍDO -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.green))
            else -> tvStatusLabel.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

    private fun setButtons(itemView: View, order: Order) {
        val cancelOrderButton: Button = itemView.findViewById(R.id.btnCancelOrder)
        cancelOrderButton.setOnClickListener {  }

        val concludeOrderButton: Button = itemView.findViewById(R.id.btnConcludeOrder)
        concludeOrderButton.setOnClickListener {  }
    }

    private fun setVisibility(itemView: View?, order: Order) {
        itemView?.let { view ->
            val status = view.findViewById<TextView>(R.id.tvStatus)
            if(order.status == Order.Status.CONCLUÍDO || order.status == Order.Status.CANCELADO) {
                val cardView = view.findViewById<androidx.cardview.widget.CardView>(R.id.cardView)
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.brown_strong_light))

                val imageView: ImageView = view.findViewById(R.id.ImgEditOrder)
                val spinner: Spinner = view.findViewById(R.id.spStatus)
                val cancelOrderButton: Button = view.findViewById(R.id.btnCancelOrder)
                val concludeOrderButton: Button = view.findViewById(R.id.btnConcludeOrder)

                imageView.visibility = View.GONE
                spinner.visibility = View.GONE
                cancelOrderButton.visibility = View.GONE
                concludeOrderButton.visibility = View.GONE

                status.visibility = View.VISIBLE

                if(order.status == Order.Status.CONCLUÍDO) {
                    status.setTextColor(ContextCompat.getColor(context, R.color.green))
                } else {
                    status.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
            } else {
                status.visibility = View.GONE
            }
        }
    }

}
