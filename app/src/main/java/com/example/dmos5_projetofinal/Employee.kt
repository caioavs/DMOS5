package com.example.dmos5_projetofinal

data class Employee(
    val prontuario: Int,
    val nome: String,
    val cpf: String,
    val cargo: Role
)

enum class Role {
    GARCOM,
    COZINHEIRO
}
