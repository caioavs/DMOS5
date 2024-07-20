package com.example.dmos5_projetofinal

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val btnCadastrar: Button = findViewById(R.id.btnConcluir)
        btnCadastrar.setOnClickListener { signUp() }

        val btnRetornar: ImageButton = findViewById(R.id.btnRetornar)
        btnRetornar.setOnClickListener { returnToSignIn() }
    }

    private fun signUp() {
        val nome = findViewById<EditText>(R.id.etNome).text.toString()
        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val cpf = findViewById<EditText>(R.id.etCpf).text.toString()
        val senha = findViewById<EditText>(R.id.etSenha).text.toString()
        val cargo = if (findViewById<RadioButton>(R.id.rbGarcom).isChecked) "Garçom" else "Cozinheiro"

        // Validação dos campos
        if (nome.length < 3 || !validateEmail(email) || cpf.length != 11 || senha.length < 6) {
            signalError(findViewById(R.id.tvHeader))
            return
        }

        val firestore = FirebaseFirestore.getInstance()
        val prontuarioDocRef = firestore.collection("settings").document("prontuario_counter")

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(prontuarioDocRef)
            val currentProntuario = snapshot.getLong("current") ?: 1000L

            // Atualiza o prontuário no Firestore
            transaction.update(prontuarioDocRef, "current", currentProntuario + 1)

            // Retorna o prontuário atual
            currentProntuario
        }.addOnSuccessListener { prontuario ->
            // Cria o usuário com email e senha
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Cria o documento do usuário no Firestore
                        val user = FirebaseAuth.getInstance().currentUser
                        val userDoc = hashMapOf(
                            "nome" to nome,
                            "email" to email,
                            "cpf" to cpf,
                            "cargo" to cargo,
                            "prontuario" to prontuario
                        )

                        firestore.collection("users").document(user?.uid ?: "").set(userDoc)
                            .addOnSuccessListener {
                                // Pop-up com o prontuário
                                AlertDialog.Builder(this)
                                    .setTitle("Cadastro concluído")
                                    .setMessage(Html.fromHtml("Guarde seu prontuário: <b>$prontuario</b>"))
                                    .setPositiveButton("OK") { _, _ ->
                                        // Retorna para o SignIn
                                        val intent = Intent(this, SignInActivity::class.java)
                                        startActivity(intent)
                                    }
                                    .show()
                            }
                            .addOnFailureListener { e ->
                                // Handle error
                                Toast.makeText(this, "Erro ao salvar dados do usuário", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        // Handle signup failure
                        Toast.makeText(this, "Erro ao criar usuário", Toast.LENGTH_SHORT).show()
                    }
                }
        }.addOnFailureListener { e ->
            // Handle transaction failure
            Toast.makeText(this, "Erro ao gerar prontuário", Toast.LENGTH_SHORT).show()
        }
    }

    private fun returnToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right)
    }

    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
