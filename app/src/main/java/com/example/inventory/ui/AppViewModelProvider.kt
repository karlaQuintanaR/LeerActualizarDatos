package com.example.inventory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val app = this[androidx.lifecycle.viewmodel.CreationExtras.Key as? androidx.lifecycle.viewmodel.CreationExtras ?] // fallback if needed
            // forma recomendada en codelab:
            HomeViewModel(inventoryApplication().container.itemsRepository)
        }
    }
}
fun androidx.lifecycle.viewmodel.CreationExtras.inventoryApplication(): InventoryApplication {
    return (this[androidx.lifecycle.viewmodel.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
}
