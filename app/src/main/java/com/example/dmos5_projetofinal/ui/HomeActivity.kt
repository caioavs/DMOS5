package com.example.dmos5_projetofinal.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.dmos5_projetofinal.R
import com.example.dmos5_projetofinal.adapter.OrderAdapter
import com.example.dmos5_projetofinal.model.Order
import com.example.dmos5_projetofinal.model.Item
import com.google.android.material.navigation.NavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var newOrderButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        firestore = FirebaseFirestore.getInstance()

        setToolbar()
        setDrawerLayout()
        setNavigationView()
        setAdapter()

        newOrderButton = findViewById(R.id.btnNewOrder)
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
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) { }
            override fun onDrawerOpened(drawerView: View) {
                newOrderButton.hide()
            }
            override fun onDrawerClosed(drawerView: View) {
                newOrderButton.show()
            }
            override fun onDrawerStateChanged(newState: Int) { }
        })
    }

    private fun setNavigationView() {
        navigationView = findViewById(R.id.drawer)
        navigationView.setNavigationItemSelectedListener { item ->
            val intent: Intent?
            when(item.itemId) {
                R.id.navHome -> {
                    intent = Intent(this@HomeActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.navNewOrder -> {
                    intent = Intent(this@HomeActivity, NewOrderActivity::class.java)
                    startActivity(intent)
                }
                else -> return@setNavigationItemSelectedListener false
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
        val ordersLayout = findViewById<LinearLayout>(R.id.ordersLayout)

        // Atualiza a interface do usuário após recuperar os pedidos
        getOrdersFromDatabase { orders ->
            val orderAdapter = OrderAdapter(this, orders, ordersLayout)
            orderAdapter.populateOrders()
        }
    }

    private fun getOrdersFromDatabase(onOrdersLoaded: (List<Order>) -> Unit) {
        firestore.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                val orders = result.mapNotNull { document ->
                    document.toObject(Order::class.java).apply {
                        id = document.id // Define o ID do documento no objeto Order
                    }
                }
                onOrdersLoaded(orders)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onOrdersLoaded(emptyList()) // Retorna uma lista vazia em caso de erro
            }
    }

    private fun newOrder() {
        val intent = Intent(this, NewOrderActivity::class.java)
        startActivity(intent)
    }
}
