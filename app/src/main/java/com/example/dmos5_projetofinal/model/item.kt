package com.example.dmos5_projetofinal.model

import com.google.firebase.firestore.DocumentId

data class Item(
    @DocumentId var id: String,
    var image: String ,
    var tipo: FoodType?,
    var descricao: String ,
    var valor: Double ,
    var observacoes: String
) {
    // Necessário para o Firebase Firestore
    constructor() : this("", "", null, "", 0.0, "")

    enum class FoodType {
        ENTRADA,
        PRATO,
        SOBREMESA,
        BEBIDA
    }
}


