package com.example.dmos5_projetofinal

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        auth = FirebaseAuth.getInstance()

        val btnAcessar: Button = findViewById(R.id.btnAcessar)
        btnAcessar.setOnClickListener { signIn() }

        val txtCadastrar: TextView = findViewById(R.id.txtCadastrar)
        txtCadastrar.setOnClickListener { signUp() }
    }

    private fun signIn() {
        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            signalError(findViewById(R.id.tvHeader))
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        // Passar o ID do usuário para a próxima tela
                        val intent = Intent(this, OrderListActivity::class.java)
                        intent.putExtra("USER_ID", user.uid)  // Passar o ID do usuário
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
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
