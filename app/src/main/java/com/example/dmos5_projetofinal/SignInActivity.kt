package com.example.dmos5_projetofinal

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
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

        // Validação do campo
        if (prontuario.length !=4) {
            signalError(findViewById(R.id.tvHeader))
            return
        }
    }

    private fun signUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun signalError(textView: TextView) {
        val animator = ObjectAnimator.ofObject(
            textView,
            "textColor",
            ArgbEvaluator(),
            textView.currentTextColor,
            Color.parseColor("#ff4e5f")
        )
        animator.duration = 700
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.repeatCount = 1
        animator.start()
    }

}
