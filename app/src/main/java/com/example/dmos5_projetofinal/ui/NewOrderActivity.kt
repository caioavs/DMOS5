package com.example.dmos5_projetofinal.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.dmos5_projetofinal.R
import com.example.dmos5_projetofinal.data.ItemDataInitializer
import com.example.dmos5_projetofinal.model.Item
import com.google.android.material.navigation.NavigationView

class NewOrderActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_order)

        setToolbar()
        setDrawerLayout()
        setNavigationView()
        setSpinners()

        val placeOrderButton: Button = findViewById(R.id.btnPlaceOrder)
        placeOrderButton.setOnClickListener { placeOrder() }

        val returnButton: ImageButton = findViewById(R.id.btnReturn)
        returnButton.setOnClickListener { returnToHome() }
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
                    intent = Intent(this@NewOrderActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.navNewOrder -> {
                    intent = Intent(this@NewOrderActivity, NewOrderActivity::class.java)
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

    private fun setSpinners() {
        val pratoPrincipal: Spinner = findViewById(R.id.spPratoPrincipal)
        setPratosPrincipais(pratoPrincipal)

        val pratoAdicional: Spinner = findViewById(R.id.spPratoAdicional)
        setPratosAdicionais(pratoAdicional)

        val bebida: Spinner = findViewById(R.id.spBebida)
        setBebidas(bebida)
    }

    private fun setPratosPrincipais(pratoPrincipal: Spinner) {
        val pratosPrincipais = ItemDataInitializer.getInitialItems()
            .filter { it.tipo == Item.ItemType.PRATO_PRINCIPAL }
            .map { it.descricao }

        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            pratosPrincipais
        )

        adapter.setDropDownViewResource(R.layout.spinner_dropdown)

        pratoPrincipal.adapter = adapter
    }

    private fun setPratosAdicionais(pratoAdicional: Spinner) {
        val pratosAdicionais = ItemDataInitializer.getInitialItems()
            .filter { it.tipo == Item.ItemType.PRATO_ADICIONAL }
            .map { it.descricao }

        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            pratosAdicionais
        )

        adapter.setDropDownViewResource(R.layout.spinner_dropdown)

        pratoAdicional.adapter = adapter
    }

    private fun setBebidas(bebida: Spinner) {
        val bebidas = ItemDataInitializer.getInitialItems()
            .filter { it.tipo == Item.ItemType.BEBIDA }
            .map { it.descricao }

        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            bebidas
        )

        adapter.setDropDownViewResource(R.layout.spinner_dropdown)

        bebida.adapter = adapter
    }

    private fun placeOrder() {
        val pratoPrincipal: String = findViewById<Spinner>(R.id.spPratoPrincipal).selectedItem.toString()
        val pratoAdicional: String = findViewById<Spinner>(R.id.spPratoAdicional).selectedItem.toString()
        val bebida: String = findViewById<Spinner>(R.id.spBebida).selectedItem.toString()
        val observacoes: String = findViewById<EditText>(R.id.etObservacoes).text.toString()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun returnToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right)
    }

}
