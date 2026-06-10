package com.isacetin.gibinteraktifsosyalapp.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme

private val ScreenTop = Color(0xFFECECF8)
private val ScreenBottom = Color(0xFFFCFCFE)
private val Indigo = Color(0xFF5B5BD6)
private val IndigoDeep = Color(0xFF4F4FC9)
private val AvatarTop = Color(0xFF9A9AF7)
private val AvatarBottom = Color(0xFF6E6EE6)
private val Ink = Color(0xFF16161D)
private val Muted = Color(0xFF6B6B7B)
private val FieldBorder = Color(0xFFE2E2EC)

/**
 * UI Kit `auth · login` ekranı (açık). Kimlik doğrulama yok — "Giriş Yap" veya
 * "Face ID ile gir"e basınca doğrudan uygulamaya girer.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by remember { mutableStateOf("ahmet.kaya@gib.com.tr") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(ScreenTop, ScreenBottom)))
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(56.dp))

        // Memoji avatar — mor squircle, ⭐ ve 🪙 rozetleriyle
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(44.dp))
                    .background(Brush.verticalGradient(listOf(AvatarTop, AvatarBottom))),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "🤵", fontSize = 96.sp)
            }
            Text(
                text = "⭐",
                fontSize = 34.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-6).dp, y = 6.dp),
            )
            Text(
                text = "🪙",
                fontSize = 34.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 8.dp, y = (-6).dp),
            )
        }

        Spacer(Modifier.height(28.dp))

        // Logo satırı: ⚡ kutu + "GİB İnteraktif"
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Indigo),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "⚡", fontSize = 26.sp)
            }
            Spacer(Modifier.width(14.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Ink)) { append("GİB ") }
                    withStyle(SpanStyle(color = Indigo)) { append("İnteraktif") }
                },
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Çalış. Kazan. Eğlen.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Muted,
        )

        Spacer(Modifier.height(64.dp))

        // Kurumsal e-posta
        Text(
            text = "Kurumsal e-posta",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Ink,
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            leadingIcon = { Text(text = "✉️", fontSize = 18.sp) },
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Indigo,
                unfocusedBorderColor = FieldBorder,
                focusedTextColor = Ink,
                unfocusedTextColor = Ink,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
        )

        Spacer(Modifier.height(18.dp))

        // Giriş Yap
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Brush.linearGradient(listOf(Indigo, IndigoDeep)))
                .clickable(onClick = onLoginSuccess),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Giriş Yap",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(Modifier.height(18.dp))

        // veya ayırıcı
        Row(verticalAlignment = Alignment.CenterVertically) {
            Divider(modifier = Modifier.weight(1f), color = FieldBorder)
            Text(
                text = "veya",
                modifier = Modifier.padding(horizontal = 14.dp),
                color = Muted,
                fontSize = 15.sp,
            )
            Divider(modifier = Modifier.weight(1f), color = FieldBorder)
        }

        Spacer(Modifier.height(18.dp))

        // Face ID
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(Color.White)
                .border(2.dp, Indigo, RoundedCornerShape(48.dp))
                .clickable(onClick = onLoginSuccess),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "🙂", fontSize = 40.sp)
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Face ID ile gir",
            color = Indigo,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = onLoginSuccess),
        )

        Spacer(Modifier.height(40.dp))
    }
}

@Preview(widthDp = 400, heightDp = 854)
@Composable
private fun LoginScreenPreview() {
    GibTheme {
        LoginScreen(onLoginSuccess = {})
    }
}
