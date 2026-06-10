package com.isacetin.gibinteraktifsosyalapp.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.CoinIcon
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibCard
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibProgress
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.PillShape

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 20.dp, top = 6.dp, end = 20.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = "Profil",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        item { ProfileHeroCard() }
        item { StatsRow() }
        item { BadgesSection() }
        item { PointsHistorySection() }
    }
}

@Composable
private fun ProfileHeroCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(listOf(Color(0xFF6D6DF0), Color(0xFF5B5BD6), Color(0xFF8E5BE0))),
            )
            .padding(20.dp),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(140.dp)
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileAvatar()
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Ahmet Kaya",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
            )
            Text(
                text = "Mühendislik · Yazılım Geliştirici",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.80f),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LevelPill()
                Row(
                    modifier = Modifier
                        .clip(PillShape)
                        .background(Color.White.copy(alpha = 0.18f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CoinIcon(size = 16.dp)
                    Text(
                        text = "1.240 puan",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
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
private fun ProfileAvatar() {
    Box {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.linearGradient(listOf(Color(0xFFFFE08A), Color(0xFFFF9F45))))
                .border(2.dp, Color.White.copy(alpha = 0.28f), RoundedCornerShape(28.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "🧑‍💼", fontSize = 48.sp)
            Text(text = "🎩", fontSize = 24.sp, modifier = Modifier.align(Alignment.TopCenter))
            Text(text = "🕶️", fontSize = 22.sp, modifier = Modifier.align(Alignment.Center))
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(28.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(Color.White)
                .border(2.dp, Color(0xFF5B5BD6), RoundedCornerShape(percent = 50)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "✏️", fontSize = 13.sp)
        }
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
private fun StatsRow() {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        ProfileStatCard(
            icon = Icons.Outlined.TaskAlt,
            value = "47",
            label = "Görev",
            iconBackground = MaterialTheme.colorScheme.primaryContainer,
            iconTint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(1f),
        )
        ProfileStatCard(
            icon = Icons.Filled.EmojiEvents,
            value = "12",
            label = "Etkinlik",
            iconBackground = GibExtendedTheme.colors.accentSoft,
            iconTint = GibExtendedTheme.colors.accentDeep,
            modifier = Modifier.weight(1f),
        )
        ProfileStatCard(
            icon = Icons.Filled.LocalFireDepartment,
            value = "5",
            label = "Gün Seri",
            iconBackground = GibExtendedTheme.colors.successSoft,
            iconTint = GibExtendedTheme.colors.success,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ProfileStatCard(
    icon: ImageVector,
    value: String,
    label: String,
    iconBackground: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
) {
    GibCard(modifier = modifier, contentPadding = PaddingValues(horizontal = 12.dp, vertical = 13.dp)) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBackground),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(19.dp))
        }
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = value,
            fontSize = 19.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface,
            letterSpacing = (-0.4).sp,
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

private data class ProfileBadge(val emoji: String, val label: String, val unlocked: Boolean)

private val PROFILE_BADGES = listOf(
    ProfileBadge("🎯", "İlk Adım", unlocked = true),
    ProfileBadge("🔥", "Seri Avcısı", unlocked = true),
    ProfileBadge("🦋", "Sosyal Kelebek", unlocked = true),
    ProfileBadge("💎", "Puan Canavarı", unlocked = false),
    ProfileBadge("🏆", "Şampiyon", unlocked = false),
    ProfileBadge("🎨", "Koleksiyoner", unlocked = false),
)

@Composable
private fun BadgesSection() {
    Column {
        SectionHeader(
            title = "Rozetlerim",
            trailing = "${PROFILE_BADGES.count { it.unlocked }} / ${PROFILE_BADGES.size} kazanıldı",
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(PROFILE_BADGES) { badge -> BadgeItem(badge) }
        }
    }
}

@Composable
private fun BadgeItem(badge: ProfileBadge) {
    val ext = GibExtendedTheme.colors
    Column(
        modifier = Modifier.width(76.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(if (badge.unlocked) ext.accentSoft else MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    width = 1.dp,
                    color = if (badge.unlocked) ext.accent.copy(alpha = 0.35f) else MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(18.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = badge.emoji,
                fontSize = 26.sp,
                modifier = if (badge.unlocked) Modifier else Modifier.alpha(0.35f),
            )
            if (!badge.unlocked) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = badge.label,
            fontSize = 11.sp,
            lineHeight = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (badge.unlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
    }
}

private data class PointHistoryItem(
    val icon: ImageVector,
    val iconBackground: Color,
    val iconTint: Color,
    val title: String,
    val subtitle: String,
    val points: Int,
)

@Composable
private fun PointsHistorySection() {
    val ext = GibExtendedTheme.colors
    val historyItems = listOf(
        PointHistoryItem(
            icon = Icons.Outlined.TaskAlt,
            iconBackground = MaterialTheme.colorScheme.primaryContainer,
            iconTint = MaterialTheme.colorScheme.onPrimaryContainer,
            title = "Görev tamamlandı",
            subtitle = "Haftalık raporu gönder · 2 saat önce",
            points = 50,
        ),
        PointHistoryItem(
            icon = Icons.Filled.EmojiEvents,
            iconBackground = ext.accentSoft,
            iconTint = ext.accentDeep,
            title = "Etkinliğe katılım",
            subtitle = "Take a Yoga Break · Dün",
            points = 120,
        ),
        PointHistoryItem(
            icon = Icons.Filled.SportsEsports,
            iconBackground = ext.successSoft,
            iconTint = ext.success,
            title = "Mini oyun skoru",
            subtitle = "Refleks Testi · 3 gün önce",
            points = 30,
        ),
        PointHistoryItem(
            icon = Icons.Filled.ShoppingCart,
            iconBackground = ext.dangerSoft,
            iconTint = ext.danger,
            title = "Shop alışverişi",
            subtitle = "Güneş Gözlüğü · 4 gün önce",
            points = -200,
        ),
        PointHistoryItem(
            icon = Icons.Outlined.TaskAlt,
            iconBackground = MaterialTheme.colorScheme.primaryContainer,
            iconTint = MaterialTheme.colorScheme.onPrimaryContainer,
            title = "Görev tamamlandı",
            subtitle = "Kod incelemesi · 5 gün önce",
            points = 80,
        ),
    )

    Column {
        SectionHeader(title = "Puan Geçmişi")
        Spacer(modifier = Modifier.height(10.dp))
        GibCard(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
        ) {
            historyItems.forEachIndexed { index, item ->
                PointHistoryRow(item)
                if (index != historyItems.lastIndex) {
                    HistoryDivider()
                }
            }
        }
    }
}

@Composable
private fun PointHistoryRow(item: PointHistoryItem) {
    val ext = GibExtendedTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(item.iconBackground),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = item.icon, contentDescription = null, tint = item.iconTint, modifier = Modifier.size(19.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.subtitle,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            text = (if (item.points > 0) "+" else "") + item.points.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (item.points > 0) ext.success else ext.danger,
        )
    }
}

@Composable
private fun HistoryDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
    )
}

@Composable
private fun SectionHeader(title: String, trailing: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.ExtraBold,
        )
        if (trailing != null) {
            Text(
                text = trailing,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
