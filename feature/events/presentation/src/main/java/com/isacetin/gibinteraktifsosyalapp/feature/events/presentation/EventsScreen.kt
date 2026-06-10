package com.isacetin.gibinteraktifsosyalapp.feature.events.presentation

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.CoinIcon
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.EmptyState
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibCard
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibPrimaryButton
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibProgress
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.LoadingShimmer
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.PointBadge
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.ButtonShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.CardShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.PillShape
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EventsRoute(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    EventsScreen(
        uiState = uiState,
        onEventClick = viewModel::selectEvent,
        onBackFromDetail = viewModel::closeDetail,
        onCreateClick = viewModel::showCreateForm,
        onDismissCreate = viewModel::dismissCreateForm,
        onTitleChange = viewModel::updateTitle,
        onDescriptionChange = viewModel::updateDescription,
        onLocationChange = viewModel::updateLocation,
        onIncreaseCapacity = viewModel::increaseCapacity,
        onDecreaseCapacity = viewModel::decreaseCapacity,
        onPublish = viewModel::createEvent,
        onJoinToggle = viewModel::toggleJoin,
        onDismissBanner = viewModel::dismissBanner,
        modifier = modifier,
    )
}

@Composable
fun EventsScreen(
    uiState: EventsUiState,
    onEventClick: (Event) -> Unit,
    onBackFromDetail: () -> Unit,
    onCreateClick: () -> Unit,
    onDismissCreate: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onIncreaseCapacity: () -> Unit,
    onDecreaseCapacity: () -> Unit,
    onPublish: () -> Unit,
    onJoinToggle: (Event) -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        EventsUiState.Loading -> EventsLoading(modifier)
        is EventsUiState.Error -> EmptyState(
            emoji = "⚠️",
            title = "Etkinlikler yüklenemedi",
            description = uiState.message,
            modifier = modifier.fillMaxSize(),
        )

        is EventsUiState.Content -> when {
            uiState.showCreateForm -> EventCreateContent(
                state = uiState,
                onBack = onDismissCreate,
                onTitleChange = onTitleChange,
                onDescriptionChange = onDescriptionChange,
                onLocationChange = onLocationChange,
                onIncreaseCapacity = onIncreaseCapacity,
                onDecreaseCapacity = onDecreaseCapacity,
                onPublish = onPublish,
                modifier = modifier,
            )

            uiState.selectedEvent != null -> EventDetailContent(
                event = uiState.selectedEvent,
                onBack = onBackFromDetail,
                onJoinToggle = onJoinToggle,
                modifier = modifier,
            )

            else -> EventListContent(
                state = uiState,
                onEventClick = onEventClick,
                onCreateClick = onCreateClick,
                onJoinToggle = onJoinToggle,
                onDismissBanner = onDismissBanner,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun EventListContent(
    state: EventsUiState.Content,
    onEventClick: (Event) -> Unit,
    onCreateClick: () -> Unit,
    onJoinToggle: (Event) -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            HeaderRow(
                title = "Etkinlikler",
                points = state.balance,
                action = "+ Oluştur",
                onAction = onCreateClick,
            )
        }

        state.bannerMessage?.let { message ->
            item {
                EventBanner(message = message, onDismiss = onDismissBanner)
            }
        }

        items(items = state.events, key = { it.id }) { event ->
            EventCard(
                event = event,
                onClick = { onEventClick(event) },
                onJoinToggle = { onJoinToggle(event) },
            )
        }
    }
}

@Composable
private fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onJoinToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GibCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(event.coverBrush()),
        ) {
            Text(
                text = event.emoji(),
                fontSize = 32.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
            )
            Text(
                text = event.whenLabel(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(Color.Black.copy(alpha = 0.25f), PillShape)
                    .padding(horizontal = 10.dp, vertical = 3.dp),
            )
        }

        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "📍 ${event.location}",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AvatarStack(extra = (event.participantCount - 3).coerceAtLeast(0))
                Text(
                    text = "${event.participantCount}/${event.capacity} ${if (event.isFull) "dolu" else "katılıyor"}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (event.isFull) GibExtendedTheme.colors.danger else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            GibProgress(
                value = event.participantCount.toFloat() / event.capacity.toFloat(),
                color = if (event.isFull) GibExtendedTheme.colors.danger else GibExtendedTheme.colors.success,
                height = 6.dp,
            )

            EventActionButton(event = event, onClick = onJoinToggle)
        }
    }
}

@Composable
private fun EventDetailContent(
    event: Event,
    onBack: () -> Unit,
    onJoinToggle: (Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(event.coverBrush()),
        ) {
            Text(
                text = event.emoji(),
                fontSize = 56.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, bottom = 22.dp),
            )
            BackButton(onClick = onBack, modifier = Modifier.padding(start = 16.dp, top = 14.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 192.dp)
                .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 118.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "🕒 ${event.whenLabel()}", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "📍 ${event.location}", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                text = event.description.ifBlank {
                    "Haftanın yorgunluğunu birlikte atalım! Tüm ekipler davetli, katılan herkese +15 puan."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp,
            )
            GibCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Katılımcılar", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.ExtraBold)
                    Text(
                        text = "${event.participantCount}/${event.capacity} katılıyor",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (event.isFull) GibExtendedTheme.colors.danger else GibExtendedTheme.colors.success,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                GibProgress(
                    value = event.participantCount.toFloat() / event.capacity.toFloat(),
                    color = if (event.isFull) GibExtendedTheme.colors.danger else GibExtendedTheme.colors.success,
                    height = 6.dp,
                )
                Spacer(modifier = Modifier.height(14.dp))
                AvatarGrid()
            }
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(text = "Ödül", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        CoinIcon(size = 18.dp)
                        Text(text = "+${event.rewardPoints}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = GibExtendedTheme.colors.accentDeep)
                    }
                }
                EventActionButton(event = event, onClick = { onJoinToggle(event) }, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun EventCreateContent(
    state: EventsUiState.Content,
    onBack: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onIncreaseCapacity: () -> Unit,
    onDecreaseCapacity: () -> Unit,
    onPublish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val form = state.createForm
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, top = 16.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            BackButton(onClick = onBack)
            Text(text = "Etkinlik Oluştur", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            UiKitTextField(
                label = "Başlık",
                value = form.title,
                placeholder = "örn. Cuma Kahve Molası",
                error = form.titleError,
                onValueChange = onTitleChange,
            )
            UiKitTextField(
                label = "Açıklama",
                value = form.description,
                placeholder = "Kısa bir açıklama yaz",
                onValueChange = onDescriptionChange,
                minLines = 3,
            )
            UiKitTextField(
                label = "Konum",
                value = form.location,
                placeholder = "Teras Kafe, 4. kat",
                onValueChange = onLocationChange,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StaticField(label = "Tarih", value = "12 Haz", error = form.dateError, modifier = Modifier.weight(1f))
                StaticField(label = "Saat", value = "15:00", modifier = Modifier.weight(1f))
            }
            CapacityStepper(
                capacity = form.capacity,
                onDecrease = onDecreaseCapacity,
                onIncrease = onIncreaseCapacity,
            )
        }

        Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 8.dp) {
            GibPrimaryButton(
                text = "Etkinliği Yayınla",
                onClick = onPublish,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
            )
        }
    }
}

@Composable
private fun HeaderRow(
    title: String,
    points: Int,
    action: String,
    onAction: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
            )
            Text(
                text = "Liste · detay · oluşturma · kapasite",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PointBadge(points = points)
            Text(
                text = action,
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(PillShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onAction)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
            )
        }
    }
}

@Composable
private fun EventActionButton(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = when {
        event.isFull && !event.isJoined -> GibExtendedTheme.colors.surface3
        event.isJoined -> GibExtendedTheme.colors.success
        else -> MaterialTheme.colorScheme.primary
    }
    val label = when {
        event.isFull && !event.isJoined -> "Kapasite Dolu"
        event.isJoined -> "✓ Katıldın"
        else -> "Katıl · +${event.rewardPoints}"
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(ButtonShape)
            .background(background)
            .clickable(enabled = !(event.isFull && !event.isJoined), onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!event.isJoined && !event.isFull) {
            CoinIcon(size = 14.dp)
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (event.isFull && !event.isJoined) MaterialTheme.colorScheme.onSurfaceVariant else Color.White,
        )
    }
}

@Composable
private fun EventBanner(message: String, onDismiss: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(13.dp))
            .background(
                if (message.contains("dolu", ignoreCase = true)) GibExtendedTheme.colors.dangerSoft else GibExtendedTheme.colors.successSoft,
            )
            .clickable(onClick = onDismiss)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = if (message.contains("dolu", ignoreCase = true)) "🚫" else "✅")
        Text(
            text = message,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = if (message.contains("dolu", ignoreCase = true)) GibExtendedTheme.colors.danger else GibExtendedTheme.colors.success,
        )
    }
}

@Composable
private fun AvatarStack(extra: Int) {
    val faces = listOf("🧑‍💼", "👩‍🎨", "🧑‍💻")
    Row(verticalAlignment = Alignment.CenterVertically) {
        faces.forEachIndexed { index, face ->
            MiniAvatar(
                face = face,
                modifier = Modifier.offset(x = (-10 * index).dp),
            )
        }
        if (extra > 0) {
            Box(
                modifier = Modifier
                    .offset(x = (-30).dp)
                    .size(26.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .border(2.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape(11.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "+$extra", fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}

@Composable
private fun AvatarGrid() {
    val names = listOf("Elif", "Mert", "Can", "Zeynep", "Ada", "Deniz", "Ali", "Ece")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        names.chunked(4).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                row.forEachIndexed { index, name ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(52.dp)) {
                        MiniAvatar(face = listOf("👩‍🎨", "🧑‍💻", "🧑‍💼", "🧑‍🔧")[(index + name.length) % 4], size = 42.dp)
                        Text(text = name, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                    }
                }
            }
        }
    }
}

@Composable
private fun MiniAvatar(
    face: String,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 26.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape((size.value * 0.42f).dp))
            .background(Brush.linearGradient(listOf(Color(0xFFA5B4FC), Color(0xFF6366F1))))
            .border(2.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape((size.value * 0.42f).dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = face, fontSize = (size.value * 0.52f).sp, textAlign = TextAlign.Center)
    }
}

@Composable
private fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(38.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.92f))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "‹", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun UiKitTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    minLines: Int = 1,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = placeholder) },
            isError = error != null,
            minLines = minLines,
            shape = RoundedCornerShape(14.dp),
            supportingText = error?.let { { Text(text = "⚠️ $it", fontWeight = FontWeight.Bold) } },
        )
    }
}

@Composable
private fun StaticField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    error: String? = null,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.5.dp, if (error == null) MaterialTheme.colorScheme.outline else GibExtendedTheme.colors.danger, RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(text = value, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        }
        error?.let {
            Text(text = "⚠️ $it", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = GibExtendedTheme.colors.danger)
        }
    }
}

@Composable
private fun CapacityStepper(
    capacity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(text = "Kapasite", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.5.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(14.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Maksimum kişi", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StepButton(text = "−", onClick = onDecrease, tonal = false)
                Text(text = capacity.toString(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                StepButton(text = "+", onClick = onIncrease, tonal = true)
            }
        }
    }
}

@Composable
private fun StepButton(text: String, onClick: () -> Unit, tonal: Boolean) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(if (tonal) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = if (tonal) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun EventsLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(56.dp))
        repeat(4) {
            LoadingShimmer(modifier = Modifier.fillMaxWidth().height(214.dp))
        }
    }
}

private fun Event.emoji(): String = when {
    title.contains("Kahve", ignoreCase = true) -> "☕"
    title.contains("Oyun", ignoreCase = true) -> "🎮"
    title.contains("Koşu", ignoreCase = true) -> "🏃"
    title.contains("Pizza", ignoreCase = true) -> "🍕"
    else -> "🎉"
}

private fun Event.coverBrush(): Brush = when (emoji()) {
    "☕" -> Brush.linearGradient(listOf(Color(0xFFF59E0B), Color(0xFFFBBF24)))
    "🎮" -> Brush.linearGradient(listOf(Color(0xFF6366F1), Color(0xFF8B5CF6)))
    "🏃" -> Brush.linearGradient(listOf(Color(0xFF10B981), Color(0xFF34D399)))
    "🍕" -> Brush.linearGradient(listOf(Color(0xFFE5484D), Color(0xFFF87171)))
    else -> Brush.linearGradient(listOf(Color(0xFF5B5BD6), Color(0xFF8E8EF5)))
}

private fun Event.whenLabel(): String =
    DateTimeFormatter.ofPattern("EEE HH:mm", Locale.forLanguageTag("tr-TR"))
        .withZone(ZoneId.systemDefault())
        .format(startsAt)
        .replaceFirstChar { it.titlecase(Locale.forLanguageTag("tr-TR")) }

@Preview
@Composable
private fun EventsScreenPreview() {
    GibTheme {
        EventsScreen(
            uiState = EventsUiState.Content(
                events = listOf(
                    Event(
                        id = "1",
                        title = "Cuma Kahve Molası",
                        description = "Haftalık takım buluşması",
                        location = "Teras Kafe, 4. kat",
                        startsAt = java.time.Instant.now(),
                        capacity = 12,
                        participantCount = 8,
                        isJoined = true,
                    ),
                ),
                balance = 1240,
            ),
            onEventClick = {},
            onBackFromDetail = {},
            onCreateClick = {},
            onDismissCreate = {},
            onTitleChange = {},
            onDescriptionChange = {},
            onLocationChange = {},
            onIncreaseCapacity = {},
            onDecreaseCapacity = {},
            onPublish = {},
            onJoinToggle = {},
            onDismissBanner = {},
        )
    }
}
