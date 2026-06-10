package com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.CardShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibPrimaryButton
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus

/** A single Jira task row: key, title, story points, status and "Tamamla" action. */
@Composable
fun TaskCard(
    task: Task,
    onCompleteClick: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = task.key,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                StatusChip(status = task.status)
            }

            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${task.storyPoints} SP · +${task.reward} 🪙",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GibExtendedTheme.colors.accent,
                    fontWeight = FontWeight.Bold,
                )

                if (task.status != TaskStatus.DONE) {
                    GibPrimaryButton(
                        text = "Tamamla",
                        onClick = { onCompleteClick(task) },
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusChip(status: TaskStatus) {
    val (label, color) = when (status) {
        TaskStatus.TODO -> "Yapılacak" to MaterialTheme.colorScheme.onSurfaceVariant
        TaskStatus.IN_PROGRESS -> "Devam Ediyor" to MaterialTheme.colorScheme.primary
        TaskStatus.DONE -> "Tamamlandı" to GibExtendedTheme.colors.success
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = color,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .background(color = color.copy(alpha = 0.12f), shape = RoundedCornerShape(percent = 50))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}

@Preview
@Composable
private fun TaskCardPreview() {
    GibTheme {
        TaskCard(
            task = Task(
                id = "1",
                key = "GIB-12",
                title = "Ödeme servisini yeni API'ye taşı",
                storyPoints = 5,
                status = TaskStatus.TODO,
                reward = 50,
            ),
            onCompleteClick = {},
        )
    }
}
