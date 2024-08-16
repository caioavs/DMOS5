// Arquivo: ItemDataInitializer.kt
package com.example.dmos5_projetofinal.model

object ItemDataInitializer {

    fun getInitialItems(): List<Item> {
        return listOf(
            // Pratos principais
            Item("main_dish_1", Item.FoodType.PRATO_PRINCIPAL, "Bife", 25.0),
            Item("main_dish_2", Item.FoodType.PRATO_PRINCIPAL, "Frango", 22.0),
            Item("main_dish_3", Item.FoodType.PRATO_PRINCIPAL, "Peixe", 28.0),

            // Pratos adicionais
            Item("side_dish_1", Item.FoodType.PRATO_ADICIONAL, "Salada", 8.0),
            Item("side_dish_2", Item.FoodType.PRATO_ADICIONAL, "Batata", 10.0),
            Item("side_dish_3", Item.FoodType.PRATO_ADICIONAL, "Arroz", 7.0),

            // Bebidas
            Item("drink_1", Item.FoodType.BEBIDA, "Água", 3.0),
            Item("drink_2", Item.FoodType.BEBIDA, "Suco", 5.0),
            Item("drink_3", Item.FoodType.BEBIDA, "Refrigerante", 6.0)
        )
    }
}
