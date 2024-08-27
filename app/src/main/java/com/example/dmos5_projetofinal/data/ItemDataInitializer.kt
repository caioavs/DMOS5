package com.example.dmos5_projetofinal.data

import com.example.dmos5_projetofinal.model.Item

object ItemDataInitializer {

    fun getInitialItems(): List<Item> {
        return listOf(
            // Pratos principais
            Item("pp_1", Item.ItemType.PRATO_PRINCIPAL, "Filé mignon ao molho madeira", 25.0),
            Item("pp_2", Item.ItemType.PRATO_PRINCIPAL, "Sobrecoxa de frango", 22.0),
            Item("pp_3", Item.ItemType.PRATO_PRINCIPAL, "Salmão grelhado", 28.0),
            // Pratos adicionais
            Item("pa_1", Item.ItemType.PRATO_ADICIONAL, "Salada", 8.0),
            Item("pa_2", Item.ItemType.PRATO_ADICIONAL, "Batata rústica assada com ervas", 10.0),
            Item("pa_3", Item.ItemType.PRATO_ADICIONAL, "Arroz branco", 7.0),
            // Bebidas
            Item("bb_1", Item.ItemType.BEBIDA, "Água Crystal 500ml sem gás", 3.0),
            Item("bb_2", Item.ItemType.BEBIDA, "Água Crystal 500ml com gás", 4.0),
            Item("bb_3", Item.ItemType.BEBIDA, "Água de coco", 5.0),
            Item("bb_4", Item.ItemType.BEBIDA, "Coca-Cola lata", 6.0)
        )
    }

}
