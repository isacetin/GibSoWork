package com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.EmptyState
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.LoadingShimmer
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.PointBadge
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem

@Composable
fun ShopRoute(
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    ShopScreen(
        uiState = uiState,
        onCategorySelected = viewModel::selectCategory,
        onItemClick = viewModel::onItemClick,
        onConfirmPurchase = viewModel::confirmPurchase,
        onDismissPurchase = viewModel::dismissPurchaseConfirmation,
        onDismissInsufficientBalance = viewModel::dismissInsufficientBalance,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    uiState: ShopUiState,
    onCategorySelected: (ItemCategory) -> Unit,
    onItemClick: (ShopItem) -> Unit,
    onConfirmPurchase: () -> Unit,
    onDismissPurchase: () -> Unit,
    onDismissInsufficientBalance: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is ShopUiState.Loading -> LoadingContent()
            is ShopUiState.Error -> EmptyState(
                emoji = "⚠️",
                title = "Bir şeyler ters gitti",
                description = uiState.message,
                modifier = Modifier.align(Alignment.Center),
            )

            is ShopUiState.Content -> ShopContent(
                state = uiState,
                onCategorySelected = onCategorySelected,
                onItemClick = onItemClick,
            )
        }
    }

    val content = uiState as? ShopUiState.Content
    content?.purchaseConfirmation?.let { item ->
        PurchaseConfirmationSheet(
            item = item,
            onConfirm = onConfirmPurchase,
            onDismiss = onDismissPurchase,
        )
    }
}

@Composable
private fun ShopContent(
    state: ShopUiState.Content,
    onCategorySelected: (ItemCategory) -> Unit,
    onItemClick: (ShopItem) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AvatarCanvas(
            equippedByCategory = state.equippedByCategory,
            modifier = Modifier.padding(16.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Shop",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            PointBadge(points = state.balance)
        }

        if (state.insufficientBalanceMessage != null) {
            InsufficientBalanceBanner(
                message = state.insufficientBalanceMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }

        CategoryPills(
            selected = state.selectedCategory,
            onCategorySelected = onCategorySelected,
            modifier = Modifier.padding(vertical = 12.dp),
        )

        if (state.itemsInSelectedCategory.isEmpty()) {
            EmptyState(
                title = "Bu kategoride ürün yok",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(items = state.itemsInSelectedCategory, key = { it.id }) { item ->
                    ShopItemCard(item = item, onClick = onItemClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryPills(
    selected: ItemCategory,
    onCategorySelected: (ItemCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ItemCategory.entries.forEach { category ->
            FilterChip(
                selected = category == selected,
                onClick = { onCategorySelected(category) },
                label = { Text(text = category.toLabel()) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GibExtendedTheme.colors.accent.copy(alpha = 0.2f),
                ),
            )
        }
    }
}

@Composable
private fun InsufficientBalanceBanner(message: String, modifier: Modifier = Modifier) {
    val danger = GibExtendedTheme.colors.danger
    Text(
        text = "⚠️ $message",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.SemiBold,
        color = danger,
        modifier = modifier
            .background(color = danger.copy(alpha = 0.12f), shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
    )
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(220.dp))
        repeat(2) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LoadingShimmer(modifier = Modifier.weight(1f).height(140.dp))
                LoadingShimmer(modifier = Modifier.weight(1f).height(140.dp))
            }
        }
    }
}
