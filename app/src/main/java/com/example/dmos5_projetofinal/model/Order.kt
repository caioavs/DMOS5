package com.example.dmos5_projetofinal.model

import java.util.Date

data class Order(
    var id: String = "",
    var pratoPrincipal: Item? = null,
    var pratoAdicional: Item? = null,
    var bebida: Item? = null,
    var observacoes: String = "",
    var status: Status? = null,
    var dt: Date = Date(),
    var prontuarioEmployee: String = ""
) {
    enum class Status {
        EM_ESPERA,
        EM_ANDAMENTO,
        PRONTO_PARA_RETIRADA,
        CONCLUIDO,
        CANCELADO
    }
}
