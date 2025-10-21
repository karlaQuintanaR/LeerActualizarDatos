package com.example.inventory.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.ItemsRepository
import com.example.inventory.data.Item
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ItemUiState(
    val item: Item? = null,
    val isEntryValid: Boolean = false
)

class ItemUpdateViewModel(
    private val itemsRepository: ItemsRepository,
    private val itemId: Int
) : ViewModel() {

    val uiState: StateFlow<ItemUiState> = itemsRepository.getItemStream(itemId)
        .map { item -> ItemUiState(item = item, isEntryValid = item != null) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), ItemUiState())

    fun updateItem(name: String, price: Double, quantity: Int, onComplete: () -> Unit = {}) {
        val current = uiState.value.item ?: return
        viewModelScope.launch {
            itemsRepository.updateItem(current.copy(name = name, price = price, quantity = quantity))
            onComplete()
        }
    }

    fun deleteItem(onComplete: () -> Unit = {}) {
        val current = uiState.value.item ?: return
        viewModelScope.launch {
            itemsRepository.deleteItem(current)
            onComplete()
        }
    }
}
