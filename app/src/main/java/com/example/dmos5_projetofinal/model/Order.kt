package com.example.dmos5_projetofinal.model

import java.util.Date

data class Order(
    var id: String = "",
    var pratoPrincipal: Item? = null,
    var pratoAdicional: Item? = null,
    var bebida: Item? = null,
    var prontuarioUsuario: String = "",
    var observacoes: String = "", // Adicionado campo de observação
    var status: Status? = null,
    var dataCriacao: Date = Date() // Usando java.util.Date
) {
    enum class Status {
        EM_ANDAMENTO,
        PRONTO,
        CONCLUIDO,
        CANCELADO
    }
}
