package com.example.dmos5_projetofinal.ui

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dmos5_projetofinal.R
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        auth = FirebaseAuth.getInstance()

        val signInButton: Button = findViewById(R.id.btnSignIn)
        signInButton.setOnClickListener { signIn() }

        val signUpTextView: TextView = findViewById(R.id.txtSignUp)
        signUpTextView.setOnClickListener { signUp() }
    }

    private fun signIn() {
        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val senha = findViewById<EditText>(R.id.etSenha).text.toString()
        if (email.isNotEmpty() && senha.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    signalError(findViewById(R.id.tvHeader))
                }
            }
        } else {
            signalError(findViewById(R.id.tvHeader))
        }
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

    private fun signUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
