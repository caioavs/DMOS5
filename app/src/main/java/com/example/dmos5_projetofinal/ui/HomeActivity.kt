package com.example.dmos5_projetofinal.ui

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.FrameLayout
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.dmos5_projetofinal.R
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.example.dmos5_projetofinal.model.Order
import com.example.dmos5_projetofinal.model.Item
import com.example.dmos5_projetofinal.data.ItemDataInitializer

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var contentFrame: LinearLayout
    private lateinit var buttonNewOrder: Button
    private lateinit var buttonChangeStatus: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

        buttonNewOrder = findViewById(R.id.button_new_order)
        buttonChangeStatus = findViewById(R.id.button_change_status)

        setSupportActionBar(toolbar)

        firestore = FirebaseFirestore.getInstance()

        initializeItems()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigation(menuItem)
            true
        }

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        setupContentFrame()

        buttonNewOrder.setOnClickListener {
            // Lógica para novo pedido
        }

        buttonChangeStatus.setOnClickListener {
            // Lógica para alterar status
        }
    }

    private fun initializeItems() {
        val itemsCollection = firestore.collection("items")
        itemsCollection.get().addOnSuccessListener { result ->
            if(result.isEmpty) {
                val initialItems = ItemDataInitializer.getInitialItems()
                for(item in initialItems) {
                    itemsCollection.document(item.id).set(item)
                        .addOnSuccessListener {
                            // Item adicionado com sucesso
                        }
                        .addOnFailureListener {
                            // Falha ao adicionar item
                        }
                }
            }
        }.addOnFailureListener {
            // Falha ao acessar a coleção de items
        }
    }

    private fun setupContentFrame() {
        val frameLayout = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val scrollView = ScrollView(this)
        scrollView.addView(contentFrame)

        frameLayout.addView(scrollView)

        displayOrders()
    }

    private fun displayOrders() {
        val orders = getOrdersFromDatabase()
        for(order in orders) {
            val orderView = createOrderView(order)
            contentFrame.addView(orderView)
        }
    }

    private fun createOrderView(order: Order): LinearLayout {
        val orderView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }
        val orderIdTextView = TextView(this).apply {
            text = "Pedido ID: ${order.id}"
            textSize = 16f
        }
        val mainDishTextView = TextView(this).apply {
            text = "Prato Principal: ${order.pratoPrincipal?.descricao ?: "N/A"}"
            textSize = 16f
        }
        val additionalDishTextView = TextView(this).apply {
            text = "Prato Adicional: ${order.pratoAdicional?.descricao ?: "N/A"}"
            textSize = 16f
        }
        val drinkTextView = TextView(this).apply {
            text = "Bebida: ${order.bebida?.descricao ?: "N/A"}"
            textSize = 16f
        }
        val observationsTextView = TextView(this).apply {
            text = "Observações: ${order.observacoes ?: "Nenhuma"}"
            textSize = 16f
        }
        val statusTextView = TextView(this).apply {
            text = "Status: ${order.status}"
            textSize = 16f
        }
        val creationDateTextView = TextView(this).apply {
            text = "Data: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(order.dt)}"
            textSize = 16f
        }

        orderView.addView(orderIdTextView)
        orderView.addView(mainDishTextView)
        orderView.addView(additionalDishTextView)
        orderView.addView(drinkTextView)
        orderView.addView(observationsTextView)
        orderView.addView(statusTextView)
        orderView.addView(creationDateTextView)

        return orderView
    }

    private fun getOrdersFromDatabase(): List<Order> {
        return listOf(
            Order(
                id = "1",
                pratoPrincipal = Item("item1", Item.ItemType.PRATO_PRINCIPAL, "Prato Principal Teste", 20.0),
                pratoAdicional = Item("item2", Item.ItemType.PRATO_ADICIONAL, "Adicional Teste", 10.0),
                bebida = Item("item3", Item.ItemType.BEBIDA, "Bebida Teste", 5.0),
                observacoes = "Observação Teste",
                status = Order.Status.EM_ANDAMENTO,
                dt = Date(),
                prontuarioEmployee = "1001"
            )
        )
    }

    private fun handleNavigation(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navHome -> {
                // Lógica para navegar para a home
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
