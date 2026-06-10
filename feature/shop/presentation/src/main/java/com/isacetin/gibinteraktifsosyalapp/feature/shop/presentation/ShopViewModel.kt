package com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.PurchaseException
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.PurchaseOutcome
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase.EquipItemUseCase
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase.GetShopItemsUseCase
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase.PurchaseItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getShopItemsUseCase: GetShopItemsUseCase,
    private val purchaseItemUseCase: PurchaseItemUseCase,
    private val equipItemUseCase: EquipItemUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ShopUiState>(ShopUiState.Loading)
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init {
        loadShop()
    }

    fun loadShop() {
        viewModelScope.launch {
            _uiState.value = ShopUiState.Loading
            getShopItemsUseCase()
                .onSuccess { overview ->
                    _uiState.value = ShopUiState.Content(items = overview.items, balance = overview.balance)
                }
                .onFailure { error ->
                    _uiState.value = ShopUiState.Error(error.message ?: "Bir hata oluştu")
                }
        }
    }

    fun selectCategory(category: ItemCategory) {
        updateContent { it.copy(selectedCategory = category) }
    }

    /** Owned items equip immediately; unowned items open the purchase confirmation sheet. */
    fun onItemClick(item: ShopItem) {
        if (item.isOwned) {
            equip(item)
        } else {
            updateContent { it.copy(purchaseConfirmation = item) }
        }
    }

    fun dismissPurchaseConfirmation() {
        updateContent { it.copy(purchaseConfirmation = null) }
    }

    fun dismissInsufficientBalance() {
        updateContent { it.copy(insufficientBalanceMessage = null) }
    }

    fun confirmPurchase() {
        val state = _uiState.value as? ShopUiState.Content ?: return
        val item = state.purchaseConfirmation ?: return

        viewModelScope.launch {
            purchaseItemUseCase(item, state.balance)
                .onSuccess { outcome ->
                    when (outcome) {
                        is PurchaseOutcome.Success -> updateContent {
                            it.copy(
                                items = it.items.map { existing ->
                                    if (existing.id == outcome.item.id) outcome.item else existing
                                },
                                balance = outcome.newBalance,
                                purchaseConfirmation = null,
                            )
                        }
                    }
                }
                .onFailure { error ->
                    updateContent {
                        it.copy(
                            purchaseConfirmation = null,
                            insufficientBalanceMessage = error.toPurchaseMessage(),
                        )
                    }
                }
        }
    }

    private fun equip(item: ShopItem) {
        viewModelScope.launch {
            equipItemUseCase(item).onSuccess {
                updateContent { state ->
                    state.copy(
                        items = state.items.map { existing ->
                            when {
                                existing.id == item.id -> existing.copy(isEquipped = true)
                                existing.category == item.category -> existing.copy(isEquipped = false)
                                else -> existing
                            }
                        },
                    )
                }
            }
        }
    }

    private fun updateContent(transform: (ShopUiState.Content) -> ShopUiState.Content) {
        _uiState.update { state -> if (state is ShopUiState.Content) transform(state) else state }
    }

    private fun Throwable.toPurchaseMessage(): String = when (this) {
        is PurchaseException.InsufficientBalance -> shortfall?.let { "Yetersiz bakiye · $it puan eksik" } ?: message.orEmpty()
        PurchaseException.AlreadyOwned -> message.orEmpty()
        else -> message ?: "Satın alma tamamlanamadı"
    }
}
