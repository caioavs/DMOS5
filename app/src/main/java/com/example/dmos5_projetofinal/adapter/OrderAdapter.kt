package com.example.dmos5_projetofinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.dmos5_projetofinal.R
import com.example.dmos5_projetofinal.model.Order
import com.example.dmos5_projetofinal.model.Item
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(
    context: Context,
    orders: List<Order>
) : ArrayAdapter<Order>(context, R.layout.item_order, orders) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.item_order, parent, false)

        val order = getItem(position)!!

        val textViewOrderId = view.findViewById<TextView>(R.id.textViewOrderId)
        val textViewMainDish = view.findViewById<TextView>(R.id.textViewMainDish)
        val textViewAdditionalDish = view.findViewById<TextView>(R.id.textViewAdditionalDish)
        val textViewDrink = view.findViewById<TextView>(R.id.textViewDrink)
        val textViewStatus = view.findViewById<TextView>(R.id.textViewStatus)
        val textViewValue = view.findViewById<TextView>(R.id.textViewValue)
        val textViewPaymentStatus = view.findViewById<TextView>(R.id.textViewPaymentStatus)

        textViewOrderId.text = "Pedido ID: ${order.id}"

        val mainDish = order.itens?.find { it.tipo == Item.FoodType.PRATO }
        val additionalDish = order.itens?.find { it.tipo == Item.FoodType.ENTRADA }
        val drink = order.itens?.find { it.tipo == Item.FoodType.BEBIDA }

        textViewMainDish.text = "Prato Principal: ${mainDish?.descricao ?: "Não especificado"}"
        textViewAdditionalDish.text = "Prato Adicional: ${additionalDish?.descricao ?: "Não especificado"}"
        textViewDrink.text = "Bebida: ${drink?.descricao ?: "Não especificado"}"
        textViewStatus.text = "Status: ${order.status?.name ?: "Não especificado"}"

        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        textViewValue.text = "Valor: ${numberFormat.format(order.valor)}"
        textViewPaymentStatus.text = "Pago: ${if (order.isPago) "Sim" else "Não"}"

        return view
    }
}
