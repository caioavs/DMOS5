package com.example.dmos5_projetofinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.dmos5_projetofinal.adapter.OrderAdapter
import com.example.dmos5_projetofinal.model.Order
import com.example.dmos5_projetofinal.viewmodel.OrderViewModel
import com.example.dmos5_projetofinal.viewmodel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.LocalTime

class OrderListActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var txtTitulo: TextView
    private lateinit var btnNewOrder: FloatingActionButton
    private lateinit var ordersList: ListView
    private lateinit var adapter: OrderAdapter

    private val userViewModel by viewModels<UserViewModel>()
    private val orderViewModel by viewModels<OrderViewModel>()

    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        setToolBar()
        setBtnNewOrder()

        loadUserLogged()
    }

    private fun setAdapter(orders: List<Order>) {
        adapter = OrderAdapter(this@OrderListActivity, orders)
        ordersList = findViewById(R.id.order_list)
        ordersList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun deleteOrder(pos: Int) {
        orderViewModel.allOrders(userId).observe(this, Observer { orders ->
            orderViewModel.deleteOrder(orders[pos])
            setAdapter(orders) // Atualiza a lista após deletar
            Toast.makeText(this@OrderListActivity, "Pedido removido com sucesso!", Toast.LENGTH_LONG).show()
        })
    }

    private fun loadUserLogged() {
        userViewModel.currentUser.observe(this, Observer { employee ->
            if (employee == null) {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            } else {
                txtTitulo.text = "Prontuário: ${employee.prontuario}"  // Exibir prontuário na toolbar
                userId = employee.id!!
                orderViewModel.allOrders(employee.id).observe(this, Observer { orders ->
                    setAdapter(orders)
                })
            }
        })
        userViewModel.loadLoggedUser()
    }

    private fun setBtnNewOrder() {
        btnNewOrder = findViewById(R.id.btn_add_order)
        btnNewOrder.setOnClickListener {
            val intent = Intent(this@OrderListActivity, OrderRegisterActivity::class.java)
            val order = Order("", 0, 0, 0, "", null, null, LocalTime.now(), null, LocalDate.now(), 0.0, false)
            intent.putExtra("order", order)
            startActivity(intent)
        }
    }

    private fun setToolBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        txtTitulo = findViewById(R.id.toolbar_title)
    }

    override fun onResume() {
        super.onResume()
        loadUserLogged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
