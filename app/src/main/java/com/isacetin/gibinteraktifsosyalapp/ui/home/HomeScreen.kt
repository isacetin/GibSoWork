package com.isacetin.gibinteraktifsosyalapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.CoinIcon
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibCard
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibProgress
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.PointBadge
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.CardShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.PillShape

@Composable
fun HomeScreen(
    onNavigateToTasks: () -> Unit,
    onNavigateToShop: () -> Unit,
    onNavigateToGame: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 20.dp, top = 6.dp, end = 20.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { GreetingHeader() }
        item { HeroAvatarScene() }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                QuickTile(
                    label = "Görevlerim",
                    sub = "3 açık",
                    badge = "3",
                    icon = Icons.Outlined.TaskAlt,
                    iconBackground = MaterialTheme.colorScheme.primaryContainer,
                    iconTint = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = onNavigateToTasks,
                    modifier = Modifier.weight(1f),
                )
                QuickTile(
                    label = "Shop",
                    sub = "Yeni 5",
                    icon = Icons.Outlined.ShoppingBag,
                    iconBackground = GibExtendedTheme.colors.accentSoft,
                    iconTint = GibExtendedTheme.colors.accentDeep,
                    onClick = onNavigateToShop,
                    modifier = Modifier.weight(1f),
                )
                QuickTile(
                    label = "Oyna",
                    sub = "Skor kazan",
                    icon = Icons.Filled.PlayArrow,
                    iconBackground = GibExtendedTheme.colors.successSoft,
                    iconTint = GibExtendedTheme.colors.success,
                    onClick = onNavigateToGame,
                    modifier = Modifier.weight(1f),
                )
            }
        }
        item { TodayEarnedCard() }
        item { WeeklyLeaderboardCard() }
    }
}

@Composable
private fun GreetingHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(11.dp),
            modifier = Modifier.weight(1f),
        ) {
            AvatarThumb(
                size = 44.dp,
                radius = 14.dp,
                face = "🧑‍💼",
                background = Brush.linearGradient(listOf(Color(0xFFA5B4FC), Color(0xFF6366F1))),
            )
            Column {
                Text(
                    text = "İyi çalışmalar 👋",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Merhaba, Ahmet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        PointBadge(points = 1240)
    }
}

@Composable
private fun HeroAvatarScene() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF6D6DF0), Color(0xFF5B5BD6), Color(0xFF8E5BE0)),
                ),
            )
            .padding(18.dp),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(130.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(Color.White.copy(alpha = 0.12f)),
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(110.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(Color(0xFFFFC24D).copy(alpha = 0.18f)),
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                BigAvatar()
                Column(modifier = Modifier.weight(1f)) {
                    LevelPill()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        CoinIcon(size = 28.dp)
                        Text(
                            text = "1.240",
                            fontSize = 34.sp,
                            lineHeight = 34.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = (-1).sp,
                        )
                    }
                    Text(
                        text = "toplam puanın",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.80f),
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Seviye 4",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.85f),
                )
                Text(
                    text = "760 / 1000 XP",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.85f),
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            GibProgress(
                value = 0.76f,
                height = 9.dp,
                color = Color(0xFFFFB020),
                trackColor = Color.Black.copy(alpha = 0.18f),
            )
        }
    }
}

@Composable
private fun BigAvatar() {
    Box(
        modifier = Modifier
            .size(108.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Brush.linearGradient(listOf(Color(0xFFFFE08A), Color(0xFFFF9F45))))
            .border(2.dp, Color.White.copy(alpha = 0.28f), RoundedCornerShape(28.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "🧑‍💼", fontSize = 54.sp)
        Text(
            text = "🎩",
            fontSize = 28.sp,
            modifier = Modifier.align(Alignment.TopCenter),
        )
        Text(
            text = "🕶️",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun LevelPill() {
    Row(
        modifier = Modifier
            .clip(PillShape)
            .background(Color.White.copy(alpha = 0.18f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "⚡", fontSize = 13.sp)
        Text(
            text = "Seviye 4 · Uzman",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
private fun QuickTile(
    label: String,
    sub: String,
    icon: ImageVector,
    iconBackground: Color,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: String? = null,
) {
    GibCard(
        modifier = modifier.clickable(onClick = onClick),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 13.dp),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            badge?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .height(18.dp)
                        .clip(PillShape)
                        .background(GibExtendedTheme.colors.danger)
                        .padding(horizontal = 6.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = it, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
                }
            }
            Column {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(iconBackground),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(21.dp))
                }
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = label,
                    fontSize = 13.5.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = sub,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun TodayEarnedCard() {
    GibCard(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                Icon(
                    imageVector = Icons.Filled.LocalFireDepartment,
                    contentDescription = null,
                    tint = Color(0xFFFF7A45),
                    modifier = Modifier.size(17.dp),
                )
                Text(
                    text = "Bugün Kazandıkların",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                CoinIcon(size = 15.dp)
                Text(
                    text = "+120",
                    color = GibExtendedTheme.colors.success,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            StatPill(value = "+120", label = "puan", color = GibExtendedTheme.colors.accentDeep, modifier = Modifier.weight(1f))
            StatDivider()
            StatPill(value = "2", label = "görev", modifier = Modifier.weight(1f))
            StatDivider()
            StatPill(value = "1", label = "etkinlik", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatPill(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 20.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color,
            letterSpacing = (-0.4).sp,
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(28.dp)
            .background(MaterialTheme.colorScheme.outline),
    )
}

@Composable
private fun WeeklyLeaderboardCard() {
    GibCard(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Sıralama · Bu Hafta",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
            )
            Text(
                text = "Tümünü gör →",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LeaderRow(
            rank = 1,
            name = "Elif Y.",
            dept = "Tasarım",
            score = "2.150",
            face = "👩‍🎨",
            background = Brush.linearGradient(listOf(Color(0xFFFBCFE8), Color(0xFFEC4899))),
        )
        LeaderRow(
            rank = 2,
            name = "Ahmet K.",
            dept = "Mühendislik",
            score = "1.240",
            face = "🧑‍💼",
            background = Brush.linearGradient(listOf(Color(0xFFA5B4FC), Color(0xFF6366F1))),
            you = true,
        )
        LeaderRow(
            rank = 3,
            name = "Mert D.",
            dept = "Satış",
            score = "1.180",
            face = "🧑‍💻",
            background = Brush.linearGradient(listOf(Color(0xFFA7F3D0), Color(0xFF10B981))),
        )
    }
}

@Composable
private fun LeaderRow(
    rank: Int,
    name: String,
    dept: String,
    score: String,
    face: String,
    background: Brush,
    you: Boolean = false,
) {
    val medalColor = when (rank) {
        1 -> Color(0xFFFFC24D)
        2 -> Color(0xFFC0C7D1)
        3 -> Color(0xFFE0965B)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(11.dp),
    ) {
        Text(
            text = rank.toString(),
            modifier = Modifier.width(22.dp),
            color = medalColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        AvatarThumb(size = 38.dp, radius = 13.dp, face = face, background = background)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (you) "$name (sen)" else name,
                color = if (you) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = dept,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            CoinIcon(size = 15.dp)
            Text(
                text = score,
                color = GibExtendedTheme.colors.accentDeep,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        }
    }
}

@Composable
private fun AvatarThumb(
    size: Dp,
    radius: Dp,
    face: String,
    background: Brush,
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(radius))
            .background(background),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = face, fontSize = (size.value * 0.52f).sp)
    }
}
