package com.example.dmos5_projetofinal.model

import com.google.firebase.firestore.DocumentId

data class Item(
    @DocumentId var id: String,
    var tipo: FoodType?,
    var descricao: String,
    var valor: Double
) {
    // Necessário para o Firebase Firestore
    constructor() : this("", null, "", 0.0)

    enum class FoodType {
        PRATO_PRINCIPAL,
        PRATO_ADICIONAL,
        BEBIDA
    }
}
