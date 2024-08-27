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
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class OrderAdapter(
    private val context: Context,
    private var orders: List<Order>,
    private val container: LinearLayout
) {

    private val firestore = FirebaseFirestore.getInstance()

    fun populateOrders() {
        val inflater = LayoutInflater.from(context)

        // Filtrar e ordenar pedidos
        val filteredOrders = orders.filter { it.status != Order.Status.CANCELADO }
        val concludedOrders = orders.filter { it.status == Order.Status.CONCLUÍDO }

        // Juntar pedidos restantes com os concluídos no final
        val sortedOrders = filteredOrders + concludedOrders

        // Limpar container antes de adicionar os pedidos
        container.removeAllViews()

        // Adicionar os pedidos na visualização
        sortedOrders.forEach { order ->
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
        id.text = order.id

        val pratoPrincipal = view.findViewById<TextView>(R.id.tvPratoPrincipal)
        pratoPrincipal.text = order.pratoPrincipal?.descricao

        val pratoAdicional = view.findViewById<TextView>(R.id.tvPratoAdicional)
        pratoAdicional.text = order.pratoAdicional?.descricao

        val bebida = view.findViewById<TextView>(R.id.tvBebida)
        bebida.text = order.bebida?.descricao

        val observacoes = view.findViewById<TextView>(R.id.tvObservacoes)
        observacoes.text = order.observacoes

        val dt = view.findViewById<TextView>(R.id.tvDt)
        val dtFormat = SimpleDateFormat("EEEE, dd/MM/yy HH:mm", Locale("pt", "BR"))
        dt.text = dtFormat.format(order.dt)

        val status = view.findViewById<TextView>(R.id.tvStatus)
        status.text = order.status?.name
            ?.replace("_", " ")
            ?.lowercase()
            ?.replaceFirstChar { it.uppercase() }
    }

    private fun editOrder(itemView: View) {
        val imageView: ImageView = itemView.findViewById(R.id.ImgEditOrder)
        imageView.setOnClickListener {
            val intent = Intent(context, NewOrderActivity::class.java)
            context.startActivity(intent)
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
            if (position >= 0) {
                spinner.setSelection(position)
            }
        }
    }

    private fun setStatusLabel(itemView: View, order: Order) {
        val tvStatusLabel: TextView = itemView.findViewById(R.id.tvStatusLabel)
        val colorResId = when (order.status) {
            Order.Status.EM_ESPERA -> R.color.brown_lighter
            Order.Status.EM_ANDAMENTO -> R.color.blue_light
            Order.Status.PRONTO_PARA_RETIRADA -> R.color.yellow
            Order.Status.CONCLUÍDO -> R.color.green
            else -> R.color.red
        }
        tvStatusLabel.setTextColor(ContextCompat.getColor(context, colorResId))
    }

    private fun setButtons(itemView: View, order: Order) {
        val cancelOrderButton: Button = itemView.findViewById(R.id.btnCancelOrder)
        cancelOrderButton.setOnClickListener {
            updateOrderStatus(order.id, Order.Status.CANCELADO)
        }

        val concludeOrderButton: Button = itemView.findViewById(R.id.btnConcludeOrder)
        concludeOrderButton.setOnClickListener {
            updateOrderStatus(order.id, Order.Status.CONCLUÍDO)
        }
    }

    private fun setVisibility(itemView: View, order: Order) {
        val status = itemView.findViewById<TextView>(R.id.tvStatus)
        val cardView = itemView.findViewById<androidx.cardview.widget.CardView>(R.id.cardView)
        val imageView: ImageView = itemView.findViewById(R.id.ImgEditOrder)
        val spinner: Spinner = itemView.findViewById(R.id.spStatus)
        val cancelOrderButton: Button = itemView.findViewById(R.id.btnCancelOrder)
        val concludeOrderButton: Button = itemView.findViewById(R.id.btnConcludeOrder)

        if (order.status == Order.Status.CONCLUÍDO || order.status == Order.Status.CANCELADO) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.brown_strong_light))
            imageView.visibility = View.GONE
            spinner.visibility = View.GONE
            cancelOrderButton.visibility = View.GONE
            concludeOrderButton.visibility = View.GONE

            status.visibility = View.VISIBLE
            status.setTextColor(
                if (order.status == Order.Status.CONCLUÍDO) {
                    ContextCompat.getColor(context, R.color.green)
                } else {
                    ContextCompat.getColor(context, R.color.red)
                }
            )
        } else {
            status.visibility = View.GONE
        }
    }

    private fun updateOrderStatus(orderId: String, newStatus: Order.Status) {
        firestore.collection("orders").document(orderId)
            .update("status", newStatus)
            .addOnSuccessListener {
                // Atualizar a lista de pedidos após a alteração
                refreshOrders()
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    private fun refreshOrders() {
        firestore.collection("orders").get()
            .addOnSuccessListener { result ->
                orders = result.map { document ->
                    document.toObject(Order::class.java)
                }
                populateOrders()
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}
