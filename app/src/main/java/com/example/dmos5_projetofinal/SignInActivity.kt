package com.example.dmos5_projetofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        val btnAcessar : Button = findViewById(R.id.btnAcessar)
        btnAcessar.setOnClickListener { signIn() }

        val txtCadastrar : TextView = findViewById(R.id.txtCadastrar)
        txtCadastrar.setOnClickListener { signUp() }
    }

    private fun signIn() {
        val prontuario = findViewById<EditText>(R.id.etProntuario).text.toString()
    }

    private fun signUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}
