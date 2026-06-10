package com.isacetin.gibinteraktifsosyalapp.feature.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.CoinIcon
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.EmptyState
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibCard
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibPrimaryButton
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.LoadingShimmer
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.PointBadge
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.CardShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.PillShape
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry

@Composable
fun GameRoute(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    GameScreen(
        uiState = uiState,
        onStart = viewModel::startGame,
        onBasketMove = viewModel::moveBasket,
        onFinish = viewModel::finishGame,
        onLeaderboard = viewModel::showLeaderboard,
        onLanding = viewModel::showLanding,
        onDismissBanner = viewModel::dismissBanner,
        modifier = modifier,
    )
}

@Composable
fun GameScreen(
    uiState: GameUiState,
    onStart: () -> Unit,
    onBasketMove: (Float) -> Unit,
    onFinish: () -> Unit,
    onLeaderboard: () -> Unit,
    onLanding: () -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        GameUiState.Loading -> LoadingContent(modifier)
        is GameUiState.Error -> EmptyState(
            title = "Oyun yüklenemedi",
            description = uiState.message,
            emoji = "🪙",
            modifier = modifier.fillMaxSize(),
        )
        is GameUiState.Content -> when (uiState.mode) {
            GameMode.Landing -> LandingContent(uiState, onStart, onLeaderboard, onDismissBanner, modifier)
            GameMode.Playing -> PlayingContent(uiState, onBasketMove, onFinish, modifier)
            GameMode.Result -> ResultContent(uiState, onStart, onLeaderboard, onDismissBanner, modifier)
            GameMode.Leaderboard -> LeaderboardContent(uiState, onLanding, onDismissBanner, modifier)
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(220.dp).clip(CardShape))
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(88.dp).clip(CardShape))
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(88.dp).clip(CardShape))
    }
}

@Composable
private fun LandingContent(
    state: GameUiState.Content,
    onStart: () -> Unit,
    onLeaderboard: () -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            HeaderRow(title = "Oyna", points = state.balance)
        }
        state.bannerMessage?.let { message ->
            item { Banner(message = message, onDismiss = onDismissBanner) }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CardShape)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF4F46E5), Color(0xFF7C3AED), Color(0xFFDB2777)),
                        ),
                    )
                    .padding(20.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Pill(text = "GÜNÜN OYUNU", color = Color.White.copy(alpha = 0.20f), contentColor = Color.White)
                    Text(
                        text = "Puan Yağmuru 🪙",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )
                    Text(
                        text = "Düşen coinleri yakala, 30 saniyede en yüksek skoru yap.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.88f),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatTile(label = "Bugünkü en iyi", value = formatScore(state.bestScore), modifier = Modifier.weight(1f))
                        StatTile(label = "Skor başına", value = "🪙 1 puan", modifier = Modifier.weight(1f))
                    }
                    GibPrimaryButton(text = "Başla", onClick = onStart, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        item {
            SectionTitle(title = "Sıralama · Bu Hafta", action = "Tümünü gör →", onAction = onLeaderboard)
        }
        items(state.leaderboard.take(4), key = { "${it.rank}-${it.displayName}" }) { entry ->
            LeaderboardRow(entry = entry)
        }
    }
}

@Composable
private fun PlayingContent(
    state: GameUiState.Content,
    onBasketMove: (Float) -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val basketWidth = 120.dp
    val basketHeight = 64.dp
    val coinSize = 52.dp
    // Coin'lerin düştüğü oyun alanının dikey aralığı.
    val topInset = 96.dp
    val bottomInset = 40.dp

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF14112E), Color(0xFF312E81), Color(0xFF581C87)),
                ),
            )
            .pointerInput(Unit) {
                val widthPx = size.width.toFloat()
                detectDragGestures { change, _ ->
                    change.consume()
                    if (widthPx > 0f) {
                        onBasketMove(change.position.x / widthPx)
                    }
                }
            }
            .pointerInput(Unit) {
                val widthPx = size.width.toFloat()
                detectTapGestures { offset ->
                    if (widthPx > 0f) {
                        onBasketMove(offset.x / widthPx)
                    }
                }
            },
    ) {
        val playHeight = maxHeight - topInset - bottomInset

        state.coins.forEach { coin ->
            FallingCoinItem(
                coin = coin,
                size = coinSize,
                modifier = Modifier.offset(
                    x = (maxWidth * coin.x) - (coinSize / 2),
                    y = topInset + (playHeight * coin.y) - (coinSize / 2),
                ),
            )
        }

        Basket(
            modifier = Modifier.offset(
                x = (maxWidth * state.basketX) - (basketWidth / 2),
                y = maxHeight - bottomInset - basketHeight,
            ),
            width = basketWidth,
            height = basketHeight,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HudPill(label = "SKOR", value = formatScore(state.score))
            if (state.combo >= 3) {
                Pill(text = "x${state.combo} Combo!", color = Color(0xFFFFC24D), contentColor = Color(0xFF3A2400))
            }
            TimerCircle(seconds = state.timeLeftSeconds)
        }

        Text(
            text = "Sepeti kaydır, düşen coinleri yakala",
            color = Color.White.copy(alpha = 0.70f),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
        )

        Surface(
            onClick = onFinish,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            color = Color.White.copy(alpha = 0.16f),
            shape = PillShape,
        ) {
            Text(
                text = "Bitir",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
            )
        }
    }
}

@Composable
private fun FallingCoinItem(
    coin: FallingCoin,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(percent = 50))
            .background(Color(0xFFFFF7D6).copy(alpha = 0.22f)),
        contentAlignment = Alignment.Center,
    ) {
        CoinIcon(size = size * 0.76f)
    }
}

@Composable
private fun Basket(
    width: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp, topStart = 10.dp, topEnd = 10.dp))
            .background(
                Brush.verticalGradient(listOf(Color(0xFFFFC24D), Color(0xFFFF7A45))),
            )
            .border(
                2.dp,
                Color.White.copy(alpha = 0.55f),
                RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp, topStart = 10.dp, topEnd = 10.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "🧺", fontSize = 30.sp)
    }
}

@Composable
private fun ResultContent(
    state: GameUiState.Content,
    onStart: () -> Unit,
    onLeaderboard: () -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        state.bannerMessage?.let { message ->
            item { Banner(message = message, onDismiss = onDismissBanner) }
        }
        item {
            GibCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Text(text = "🏆", fontSize = 54.sp)
                    Text(
                        text = "Oyun Bitti",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = formatScore(state.score),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    PointBadge(points = state.earnedPoints, big = true)
                    Text(
                        text = "Skorun kaydedildi; leaderboard artık biraz daha kızıştı.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                    GibPrimaryButton(text = "Tekrar Oyna", onClick = onStart, modifier = Modifier.fillMaxWidth())
                    Surface(onClick = onLeaderboard, shape = PillShape, color = MaterialTheme.colorScheme.surfaceVariant) {
                        Text(
                            text = "Leaderboard'a bak",
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 11.dp),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LeaderboardContent(
    state: GameUiState.Content,
    onLanding: () -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val podiumEntries = state.leaderboard.take(3)
    val rowEntries = leaderboardRows(state.leaderboard)
    val currentUserEntry = rowEntries.firstOrNull { it.isCurrentUser }
        ?: state.leaderboard.firstOrNull { it.isCurrentUser }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, top = 6.dp, end = 20.dp, bottom = 170.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                LeaderboardHeader(onLanding = onLanding)
            }
            state.bannerMessage?.let { message ->
                item { Banner(message = message, onDismiss = onDismissBanner) }
            }
            item {
                Podium(entries = podiumEntries)
            }
            items(rowEntries, key = { "${it.rank}-${it.displayName}" }) { entry ->
                LeaderboardRow(entry = entry)
            }
        }

        currentUserEntry?.let { entry ->
            StickyUserRankBar(
                entry = entry,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 14.dp, end = 14.dp, bottom = 96.dp),
            )
        }
    }
}

@Composable
private fun LeaderboardHeader(onLanding: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Sıralama",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = (-0.6).sp,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            SegmentedLeaderboardToggle()
            Surface(onClick = onLanding, shape = PillShape, color = MaterialTheme.colorScheme.surfaceVariant) {
                Text(
                    text = "Oyuna dön",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun SegmentedLeaderboardToggle() {
    Row(
        modifier = Modifier
            .clip(PillShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = "Hafta",
            modifier = Modifier
                .clip(PillShape)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 13.dp, vertical = 5.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Tüm",
            modifier = Modifier.padding(horizontal = 13.dp, vertical = 5.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun HeaderRow(title: String, points: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
        PointBadge(points = points)
    }
}

@Composable
private fun SectionTitle(title: String, action: String, onAction: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold)
        Text(
            text = action,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = onAction),
        )
    }
}

@Composable
private fun StatTile(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.16f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = label, color = Color.White.copy(alpha = 0.72f), style = MaterialTheme.typography.labelMedium)
        Text(text = value, color = Color.White, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun LeaderboardRow(entry: LeaderboardEntry) {
    val containerColor = if (entry.isCurrentUser) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val borderColor = if (entry.isCurrentUser) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.40f)
    } else {
        MaterialTheme.colorScheme.outline
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = entry.rank.toString(),
                    modifier = Modifier.width(20.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (entry.isCurrentUser) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
                LeaderAvatar(
                    face = faceFor(entry),
                    background = avatarBrushFor(entry),
                    size = 36.dp,
                    radius = 12.dp,
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = displayNameForRow(entry),
                        color = if (entry.isCurrentUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = departmentFor(entry),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    CoinIcon(size = 15.dp)
                    Text(
                        text = formatScore(entry.score),
                        color = GibExtendedTheme.colors.accentDeep,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }
    }
}

@Composable
private fun Podium(entries: List<LeaderboardEntry>) {
    val orderedEntries = listOfNotNull(entries.getOrNull(1), entries.getOrNull(0), entries.getOrNull(2))
    val heights = listOf(124.dp, 166.dp, 106.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(232.dp)
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        orderedEntries.forEachIndexed { index, entry ->
            PodiumColumn(entry = entry, height = heights.getOrElse(index) { 110.dp }, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun PodiumColumn(
    entry: LeaderboardEntry,
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.height(height),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(height - 24.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(podiumBrushFor(entry.rank))
                    .border(1.dp, Color.White.copy(alpha = 0.45f), RoundedCornerShape(22.dp))
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    text = "#${entry.rank}",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = entry.displayName.replace(" (sen)", ""),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CoinIcon(size = 14.dp)
                    Text(
                        text = formatScore(entry.score),
                        color = Color(0xFFFFE08A),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
            LeaderAvatar(
                face = faceFor(entry),
                background = avatarBrushFor(entry),
                size = 58.dp,
                radius = 19.dp,
                modifier = Modifier.border(3.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape(19.dp)),
            )
        }
    }
}

@Composable
private fun StickyUserRankBar(
    entry: LeaderboardEntry,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, Color(0xFF4F4FC9))))
            .padding(horizontal = 16.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "#${entry.rank}", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        LeaderAvatar(
            face = "🧑‍💼",
            background = Brush.linearGradient(listOf(Color(0xFFFFE08A), Color(0xFFFF9F45))),
            size = 34.dp,
            radius = 11.dp,
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Senin sıran", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = "3 kişi geçtin ↑", color = Color.White.copy(alpha = 0.80f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            CoinIcon(size = 16.dp)
            Text(text = formatScore(entry.score), color = Color(0xFFFFE08A), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun LeaderAvatar(
    face: String,
    background: Brush,
    size: androidx.compose.ui.unit.Dp,
    radius: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(radius))
            .background(background),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = face, fontSize = (size.value * 0.52f).sp)
    }
}

@Composable
private fun TimerCircle(seconds: Int) {
    Box(
        modifier = Modifier
            .size(58.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(Color.White.copy(alpha = 0.14f))
            .border(2.dp, Color.White.copy(alpha = 0.42f), RoundedCornerShape(percent = 50)),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "${seconds}sn", color = Color.White, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun HudPill(label: String, value: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.14f))
            .padding(horizontal = 14.dp, vertical = 9.dp),
    ) {
        Text(text = label, color = Color.White.copy(alpha = 0.70f), style = MaterialTheme.typography.labelSmall)
        Text(text = value, color = Color.White, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun RankBadge(rank: Int) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(
                if (rank <= 3) Color(0xFFFFF2C2) else MaterialTheme.colorScheme.surfaceVariant,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "#$rank", fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun Pill(text: String, color: Color, contentColor: Color) {
    Surface(color = color, shape = PillShape) {
        Text(
            text = text,
            color = contentColor,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
        )
    }
}

@Composable
private fun Banner(message: String, onDismiss: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(GibExtendedTheme.colors.accentSoft)
            .border(1.dp, GibExtendedTheme.colors.accent.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
            .clickable(onClick = onDismiss)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        CoinIcon()
        Text(text = message, fontWeight = FontWeight.Bold, color = GibExtendedTheme.colors.accentDeep)
    }
}

private fun formatScore(score: Int): String =
    "%,d".format(score).replace(',', '.')

private fun leaderboardRows(entries: List<LeaderboardEntry>): List<LeaderboardEntry> {
    val currentUser = entries.firstOrNull { it.isCurrentUser }
    if (entries.size >= 8) {
        return entries.drop(3)
    }
    return listOf(
        LeaderboardEntry(rank = 4, displayName = "Zeynep A.", score = 1620),
        LeaderboardEntry(rank = 5, displayName = "Can B.", score = 1540),
        LeaderboardEntry(rank = 6, displayName = "Deniz K.", score = 1380),
        LeaderboardEntry(
            rank = 7,
            displayName = currentUser?.displayName?.replace(" (sen)", "") ?: "Ahmet K.",
            score = currentUser?.score ?: 1258,
            isCurrentUser = true,
        ),
        LeaderboardEntry(rank = 8, displayName = "Selin T.", score = 1190),
    )
}

private fun displayNameForRow(entry: LeaderboardEntry): String =
    if (entry.isCurrentUser && !entry.displayName.contains("(sen)")) {
        "${entry.displayName} (sen)"
    } else {
        entry.displayName
    }

private fun departmentFor(entry: LeaderboardEntry): String =
    when {
        entry.isCurrentUser -> "Mühendislik"
        entry.displayName.startsWith("Elif") -> "Tasarım"
        entry.displayName.startsWith("Mert") -> "Satış"
        entry.displayName.startsWith("Burak") -> "Operasyon"
        entry.displayName.startsWith("Zeynep") -> "Pazarlama"
        entry.displayName.startsWith("Can") -> "Finans"
        entry.displayName.startsWith("Deniz") -> "İK"
        entry.displayName.startsWith("Selin") -> "Tasarım"
        else -> "Bu hafta"
    }

private fun faceFor(entry: LeaderboardEntry): String =
    when {
        entry.isCurrentUser -> "🧑‍💼"
        entry.displayName.startsWith("Elif") -> "👩‍🎨"
        entry.displayName.startsWith("Mert") -> "🧑‍💻"
        entry.displayName.startsWith("Burak") -> "🧑‍🔧"
        entry.displayName.startsWith("Zeynep") -> "👩‍💻"
        entry.displayName.startsWith("Can") -> "🧑‍💼"
        entry.displayName.startsWith("Deniz") -> "🧑‍🏫"
        entry.displayName.startsWith("Selin") -> "👩‍🎨"
        else -> "🙂"
    }

private fun avatarBrushFor(entry: LeaderboardEntry): Brush =
    when {
        entry.isCurrentUser -> Brush.linearGradient(listOf(Color(0xFFA5B4FC), Color(0xFF6366F1)))
        entry.displayName.startsWith("Elif") -> Brush.linearGradient(listOf(Color(0xFFFBCFE8), Color(0xFFEC4899)))
        entry.displayName.startsWith("Mert") -> Brush.linearGradient(listOf(Color(0xFFA7F3D0), Color(0xFF10B981)))
        entry.displayName.startsWith("Burak") -> Brush.linearGradient(listOf(Color(0xFFFDE68A), Color(0xFFF59E0B)))
        entry.displayName.startsWith("Zeynep") -> Brush.linearGradient(listOf(Color(0xFFFBCFE8), Color(0xFFEC4899)))
        entry.displayName.startsWith("Can") -> Brush.linearGradient(listOf(Color(0xFFBFDBFE), Color(0xFF3B82F6)))
        entry.displayName.startsWith("Deniz") -> Brush.linearGradient(listOf(Color(0xFFFDE68A), Color(0xFFF59E0B)))
        entry.displayName.startsWith("Selin") -> Brush.linearGradient(listOf(Color(0xFFA7F3D0), Color(0xFF10B981)))
        else -> Brush.linearGradient(listOf(Color(0xFFE0E7FF), Color(0xFF818CF8)))
    }

private fun podiumBrushFor(rank: Int): Brush =
    when (rank) {
        1 -> Brush.linearGradient(listOf(Color(0xFFFFC24D), Color(0xFFFF7A45)))
        2 -> Brush.linearGradient(listOf(Color(0xFF9CA3AF), Color(0xFF6B7280)))
        3 -> Brush.linearGradient(listOf(Color(0xFFE0965B), Color(0xFFB45309)))
        else -> Brush.linearGradient(listOf(Color(0xFF8E8EF5), Color(0xFF5B5BD6)))
    }

@Preview
@Composable
private fun GameScreenPreview() {
    GibTheme {
        GameScreen(
            uiState = GameUiState.Content(
                bestScore = 1840,
                balance = 1240,
                leaderboard = listOf(
                    LeaderboardEntry(1, "Elif Y.", 1840),
                    LeaderboardEntry(2, "Mert D.", 1720),
                    LeaderboardEntry(3, "Burak S.", 1690),
                ),
            ),
            onStart = {},
            onBasketMove = {},
            onFinish = {},
            onLeaderboard = {},
            onLanding = {},
            onDismissBanner = {},
        )
    }
}
