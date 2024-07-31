package com.example.dmos5_projetofinal.model

data class Employee(
    var id: String,
    var prontuario: Int,
    var nome: String,
    var email: String,
    var cpf: String,
    var cargo: Role?
) {
    constructor() : this("", 0, "", "", "", null)

    enum class Role {
        GARCOM,
        COZINHEIRO
    }
}
