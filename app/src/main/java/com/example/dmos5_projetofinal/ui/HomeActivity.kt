package com.example.dmos5_projetofinal.ui

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.dmos5_projetofinal.R
import com.example.dmos5_projetofinal.adapter.OrderAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.example.dmos5_projetofinal.model.Order
import com.example.dmos5_projetofinal.model.Item
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        firestore = FirebaseFirestore.getInstance()

        setToolbar()
        setDrawerLayout()
        setNavigationView()
        setAdapter()

        val newOrderButton: FloatingActionButton = findViewById(R.id.btnNewOrder)
        newOrderButton.setOnClickListener { newOrder() }
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerButton: ImageButton = findViewById(R.id.btnDrawer)
        drawerButton.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
    }

    private fun setDrawerLayout() {
        drawerLayout = findViewById(R.id.drawerLayout)
    }

    private fun setNavigationView() {
        navigationView = findViewById<NavigationView>(R.id.drawer)
        navigationView.setNavigationItemSelectedListener { item ->
            var intent: Intent?
            when(item.itemId) {
                R.id.navHome -> {
                    intent = Intent(this@HomeActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setAdapter() {
        val orders = getOrdersFromDatabase()
        val ordersLayout = findViewById<LinearLayout>(R.id.ordersLayout)
        val orderAdapter = OrderAdapter(this, orders, ordersLayout)
        orderAdapter.populateOrders()
    }

    private fun getOrdersFromDatabase(): List<Order> {
        return listOf(
            Order(
                id = "1",
                pratoPrincipal = Item("item1", Item.ItemType.PRATO_PRINCIPAL, "Principal Teste", 20.0),
                pratoAdicional = Item("item2", Item.ItemType.PRATO_ADICIONAL, "Adicional Teste", 10.0),
                bebida = Item("item3", Item.ItemType.BEBIDA, "Bebida Teste", 5.0),
                observacoes = "Observação Teste",
                status = Order.Status.EM_ESPERA,
                dt = Date(),
                prontuarioEmployee = "1001"
            )
        )
    }

    private fun newOrder() {
        val intent = Intent(this, NewOrderActivity::class.java)
        startActivity(intent)
    }

}
