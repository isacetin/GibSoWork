package com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.CoinIcon
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus

private val CARD_SHAPE = RoundedCornerShape(18.dp)
private val PILL = RoundedCornerShape(percent = 50)

@Composable
fun TaskCard(
    task: Task,
    onStatusChange: (Task, TaskStatus) -> Unit,
    modifier: Modifier = Modifier,
) {
    val ext = GibExtendedTheme.colors
    val done = task.status == TaskStatus.DONE

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CARD_SHAPE)
            .background(if (done) ext.successSoft else MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = if (done) ext.success.copy(alpha = 0.33f) else MaterialTheme.colorScheme.outline,
                shape = CARD_SHAPE,
            )
            .padding(14.dp),
    ) {
        Column {
            // Header row: key + sp chip + avatar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    KeyChip(key = task.key)
                    SpChip(sp = task.storyPoints)
                }
                TaskAvatar(taskKey = task.key, size = 26)
            }

            Spacer(modifier = Modifier.height(7.dp))

            // Title
            Text(
                text = task.title,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.01f * 14.5f).sp,
                lineHeight = (14.5f * 1.3f).sp,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(11.dp))

            if (done) {
                // Done state
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        CheckCircle()
                        Text(
                            text = "Tamamlandı",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ext.success,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        CoinIcon(size = 15.dp)
                        Text(
                            text = "+${task.reward} kazanıldı",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ext.accentDeep,
                        )
                    }
                }
            } else {
                // Status segmented control
                StatusSeg(
                    currentStatus = task.status,
                    onStatusChange = { onStatusChange(task, it) },
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Reward preview row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Text(
                        text = "Tamamlayınca",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    CoinIcon(size = 14.dp)
                    Text(
                        text = "+${task.reward}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ext.accentDeep,
                    )
                }
            }
        }
    }
}

@Composable
private fun KeyChip(key: String) {
    Text(
        text = key,
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = (0.02f * 11f).sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(RoundedCornerShape(7.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp, vertical = 2.dp),
    )
}

@Composable
private fun SpChip(sp: Int) {
    val ext = GibExtendedTheme.colors
    Text(
        text = "$sp SP",
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        color = ext.accentDeep,
        modifier = Modifier
            .clip(PILL)
            .background(ext.accentSoft)
            .padding(horizontal = 9.dp, vertical = 3.dp),
    )
}

@Composable
private fun CheckCircle() {
    val ext = GibExtendedTheme.colors
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(PILL)
            .background(ext.success),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "✓",
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            lineHeight = 11.sp,
        )
    }
}

@Composable
private fun StatusSeg(
    currentStatus: TaskStatus,
    onStatusChange: (TaskStatus) -> Unit,
) {
    val segments = listOf(
        TaskStatus.TODO to "To Do",
        TaskStatus.IN_PROGRESS to "Devam",
        TaskStatus.DONE to "Bitti",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(PILL)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        segments.forEach { (status, label) ->
            val selected = currentStatus == status
            val isDone = status == TaskStatus.DONE
            val ext = GibExtendedTheme.colors

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(PILL)
                    .background(
                        when {
                            selected && isDone -> ext.success
                            selected -> MaterialTheme.colorScheme.surface
                            else -> Color.Transparent
                        }
                    )
                    .clickable { onStatusChange(status) }
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        selected && isDone -> Color.White
                        selected -> MaterialTheme.colorScheme.onSurface
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
            }
        }
    }
}

// Deterministic mini avatar from task key
private data class AvatarConfig(val emoji: String, val gradientStart: Color, val gradientEnd: Color)

private val AVATAR_CONFIGS = listOf(
    AvatarConfig("🧑‍💼", Color(0xFFA5B4FC), Color(0xFF6366F1)),
    AvatarConfig("👩‍🎨", Color(0xFFFBCFE8), Color(0xFFEC4899)),
    AvatarConfig("🧑‍💻", Color(0xFFA7F3D0), Color(0xFF10B981)),
    AvatarConfig("🧑‍🔧", Color(0xFFFDE68A), Color(0xFFF59E0B)),
    AvatarConfig("🧑‍🎓", Color(0xFFBFDBFE), Color(0xFF3B82F6)),
    AvatarConfig("👩‍💻", Color(0xFFDDD6FE), Color(0xFF7C3AED)),
)

@Composable
private fun TaskAvatar(taskKey: String, size: Int) {
    val num = taskKey.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
    val cfg = AVATAR_CONFIGS[num % AVATAR_CONFIGS.size]

    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(Brush.linearGradient(listOf(cfg.gradientStart, cfg.gradientEnd))),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = cfg.emoji,
            fontSize = (size * 0.52f).sp,
            lineHeight = (size * 0.52f).sp,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAFAFD)
@Composable
private fun TaskCardTodoPreview() {
    GibTheme {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(11.dp)) {
            TaskCard(
                task = Task(id = "1", key = "GIB-12", title = "Ödeme servisini yeni API'ye taşı", storyPoints = 5, status = TaskStatus.IN_PROGRESS, reward = 50),
                onStatusChange = { _, _ -> },
            )
            TaskCard(
                task = Task(id = "2", key = "GIB-19", title = "Onboarding ekranı QA testleri", storyPoints = 3, status = TaskStatus.TODO, reward = 30),
                onStatusChange = { _, _ -> },
            )
            TaskCard(
                task = Task(id = "3", key = "GIB-07", title = "Dashboard grafik performansı", storyPoints = 8, status = TaskStatus.DONE, reward = 80),
                onStatusChange = { _, _ -> },
            )
        }
    }
}
