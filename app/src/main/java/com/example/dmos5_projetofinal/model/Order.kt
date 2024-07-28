package com.example.dmos5_projetofinal.model

import java.time.LocalDate
import java.time.LocalTime

data class Order(
    var id: String,
    var orderId: Int,
    var garcomId: Int,
    var cozinheiroId: Int,
    var nomeCliente: String,
    var status: Status?,
    var itens: ArrayList<Item>?,
    var inicioPedido: LocalTime,
    var finalPedido: LocalTime?,
    var dataCriacao: LocalDate,
    var valor: Double,
    var isPago: Boolean
) {
    // Necessário para o Firebase Firestore
    constructor() : this("", 0, 0, 0, "", null, null, LocalTime.now(), null, LocalDate.now(), 0.0, false)

    enum class Status {
        ANOTADO,
        PRODUZINDO,
        CONCLUIDO,
        PARADO,
        CANCELADO
    }
}



