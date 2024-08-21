package com.example.dmos5_projetofinal.model

import com.google.firebase.firestore.DocumentId

data class Item(
    @DocumentId var id: String,
    var tipo: ItemType?,
    var descricao: String,
    var valor: Double
) {
    constructor() : this("", null, "", 0.0)
    enum class ItemType {
        PRATO_PRINCIPAL,
        PRATO_ADICIONAL,
        BEBIDA
    }
}
