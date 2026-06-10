package com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.RewardOverlay
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task

@Composable
fun TasksRoute(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    TasksScreen(
        uiState = uiState,
        onCompleteTask = viewModel::completeTask,
        onRewardConsumed = viewModel::consumeReward,
        modifier = modifier,
    )
}

@Composable
fun TasksScreen(
    uiState: TasksUiState,
    onCompleteTask: (Task) -> Unit,
    onRewardConsumed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is TasksUiState.Loading -> LoadingContent()
            is TasksUiState.Empty -> EmptyState(
                title = "Görev yok",
                description = "Şu anda sana atanmış bir görev bulunmuyor.",
                modifier = Modifier.align(Alignment.Center),
            )
            is TasksUiState.Error -> EmptyState(
                emoji = "⚠️",
                title = "Bir şeyler ters gitti",
                description = uiState.message,
                modifier = Modifier.align(Alignment.Center),
            )
            is TasksUiState.Content -> TasksContent(
                state = uiState,
                onCompleteTask = onCompleteTask,
            )
        }

        val rewardAmount = (uiState as? TasksUiState.Content)?.rewardAmount
        RewardOverlay(
            amount = rewardAmount ?: 0,
            visible = rewardAmount != null,
            onDismiss = onRewardConsumed,
        )
    }
}

@Composable
private fun TasksContent(
    state: TasksUiState.Content,
    onCompleteTask: (Task) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Görevler",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            PointBadge(points = state.balance)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items = state.tasks, key = { it.id }) { task ->
                TaskCard(task = task, onCompleteClick = onCompleteTask)
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        repeat(4) {
            LoadingShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
            )
        }
    }
}
