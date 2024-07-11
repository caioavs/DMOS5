package com.example.dmos5_projetofinal

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.*

import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val btnCadastrar : Button = findViewById(R.id.btnConcluir)
        btnCadastrar.setOnClickListener { signUp() }

        val btnRetornar : ImageButton = findViewById(R.id.btnRetornar)
        btnRetornar.setOnClickListener { returnToSignIn() }
    }

    private fun signUp() {
        val nome = findViewById<EditText>(R.id.etNome).text.toString()
        val cpf = findViewById<EditText>(R.id.etCpf).text.toString()
        val cargo = if (findViewById<RadioButton>(R.id.rbGarcom).isChecked) Role.GARCOM else Role.COZINHEIRO

        // Validação dos campos
        if (nome.length < 3 || cpf.length != 11) {
            signalError(findViewById(R.id.tvHeader))
            return
        }

        /* Gera o prontuário
        Atualizar método para que, ao invés de gerar um número aleatório de 4 dígitos,
        o primeiro prontuário comece em 1000 e seja incrementado a cada novo cadastro,
        evitando prontuários repetidos e expondo a ordem de admissão dos funcionários
         */
        val prontuario = (1000..9999).random()

        // Pop-up com o prontuário
        AlertDialog.Builder(this)
            .setTitle("Cadastro concluído")
            .setMessage(Html.fromHtml("Guarde seu prontuário: <b>$prontuario</b>"))
            .setPositiveButton("OK") { _, _ ->
                // Registra o funcionário
                val employee = Employee(prontuario, nome, cpf, cargo)
                // Retorna para o SignIn
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
            .show()
    }

    private fun returnToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
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
