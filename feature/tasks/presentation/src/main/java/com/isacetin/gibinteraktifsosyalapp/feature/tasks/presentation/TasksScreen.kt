package com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.EmptyState
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.LoadingShimmer
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.PointBadge
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.RewardOverlay
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus

private val FILTERS = listOf("Tümü", "Yapılacak", "Devam", "Tamamlanan")

@Composable
fun TasksRoute(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    TasksScreen(
        uiState = uiState,
        onStatusChange = viewModel::setTaskStatus,
        onFilterChange = viewModel::setFilter,
        onRewardConsumed = viewModel::consumeReward,
        modifier = modifier,
    )
}

@Composable
fun TasksScreen(
    uiState: TasksUiState,
    onStatusChange: (Task, TaskStatus) -> Unit,
    onFilterChange: (String) -> Unit,
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
                onStatusChange = onStatusChange,
                onFilterChange = onFilterChange,
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
    onStatusChange: (Task, TaskStatus) -> Unit,
    onFilterChange: (String) -> Unit,
) {
    val filteredTasks = when (state.selectedFilter) {
        "Yapılacak" -> state.tasks.filter { it.status == TaskStatus.TODO }
        "Devam" -> state.tasks.filter { it.status == TaskStatus.IN_PROGRESS }
        "Tamamlanan" -> state.tasks.filter { it.status == TaskStatus.DONE }
        else -> state.tasks
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Görevlerim",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.66).sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            PointBadge(points = state.balance)
        }

        // Filter chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            items(FILTERS) { filter ->
                FilterChip(
                    label = filter,
                    selected = filter == state.selectedFilter,
                    onClick = { onFilterChange(filter) },
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp),
        ) {
            items(items = filteredTasks, key = { it.id }) { task ->
                TaskCard(task = task, onStatusChange = onStatusChange)
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(percent = 50)
    Text(
        text = label,
        fontSize = 12.5.sp,
        fontWeight = FontWeight.Bold,
        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .clip(shape)
            .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
            .border(
                width = 1.5.dp,
                color = if (selected) Color.Transparent else MaterialTheme.colorScheme.outline,
                shape = shape,
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 13.dp, vertical = 7.dp),
    )
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp),
    ) {
        repeat(4) {
            LoadingShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
            )
        }
    }
}
