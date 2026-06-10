# UI Kit → Compose Uygulama Rehberi

Kaynak dosya: `/Users/isacetin/Downloads/GİB İnteraktif UI Kit (standalone).html`

Bu dosya, ekran tasarlarken “yaklaşık benzer” yerine UI Kit'e sadık Compose karşılığı üretmek için uygulanacak kontrol listesidir. HTML kaynak bundle içinden `lib.jsx`, `screens-events.jsx` ve `screens-game.jsx` çıkarılarak token/component eşlemesi yapılmıştır.

## Genel Kurallar

- UI Kit ana doğruluk kaynağıdır; `docs/07_EKRAN_ENVANTERI.md` sadece ekran envanteri özetidir.
- Her yeni presentation ekranı önce UI Kit state'lerine bölünür: light, dark, loading/error/empty ve özel senaryolar.
- Ortak parçalar önce `:core:designsystem` içine alınır; feature ekranları raw hex, raw radius ve ad-hoc badge üretmez.
- Faz 3 için `EventScreen` ve `GameScreen` Compose karşılıkları HTML'deki spacing, copy, renk, shape ve badge mantığını takip eder.

## Token Eşlemesi

| UI Kit token | Compose karşılığı |
|---|---|
| `primary #5B5BD6` | `LightPrimary` |
| `primaryPress #4F4FC9` | `GibPrimaryButton` gradient ikinci rengi |
| `primaryContainer #E6E6FB` | `LightPrimaryContainer` |
| `onPrimaryCont #3A3A9E` | `LightOnPrimaryContainer` |
| `accent #FFB020` / dark `#FFC24D` | `GibExtendedColors.accent` |
| `accentDeep #C8861A` / dark `#E0A02E` | `GibExtendedColors.accentDeep` |
| `accentSoft rgba(..., .14/.16)` | `GibExtendedColors.accentSoft` |
| `successSoft` / `dangerSoft` | `GibExtendedColors.successSoft` / `dangerSoft` |
| `surface3 #EEEEF6` / dark `#2B2B3A` | `GibExtendedColors.surface3` |
| Card radius `20` | `CardShape` / `GibCard` |
| Button radius `16` | `ButtonShape` / `GibPrimaryButton` |
| Pill radius `999` | `PillShape` veya `RoundedCornerShape(percent = 50)` |

## Ortak Component Eşlemesi

| UI Kit component | Compose component | Not |
|---|---|---|
| `PointBadge` | `PointBadge` | Gold soft pill + radial `CoinIcon` + `accentDeep` text |
| `Coin` | `CoinIcon` | UI Kit'teki `₺` glyph'li gold disk |
| `Button(kind=primary)` | `GibPrimaryButton` | Indigo gradient, 16dp radius, 14x22 padding |
| `Card` | `GibCard` | 20dp radius, outline border, subtle elevation |
| `Progress` | `GibProgress` | Kapasite ve progress barları |
| `BottomNav` variant A | `GibBottomBar` | Faz 3'te mevcut Material pill yaklaşımı korunur |

## Faz 3 Ekran Notları

### Etkinlikler

- Liste header: `Etkinlikler` solda, `+ Oluştur` tonal/primary action sağda.
- Kart: emoji kapak alanı, saat chip'i, başlık, pin/konum satırı, avatar stack, `N/M katılıyor`, kapasite barı.
- Detay: büyük cover, tarih/konum, açıklama, katılımcı grid, `+15 puan`, primary `Katıl`.
- Oluştur: `Başlık`, `Açıklama`, `Konum`, `Tarih`, `Saat`, kapasite stepper, sticky bottom `Etkinliği Yayınla`.
- Dolu senaryo: danger soft banner + disabled `Kapasite Dolu`; RPC karşılığı `409`.

### Oyna + Sıralama

- Landing hero: indigo/purple/magenta gradient, “Günün Oyunu”, `Puan Yağmuru 🪙`, iki stat kutusu.
- Oyun içi: dark full-bleed gradient, skor HUD, süre dairesi, combo banner, düşen coinler, sepet.
- Sonuç: confetti, `Oyun Bitti`, skor, `+N kazandın`, `Sıralama` ve `Tekrar Oyna`.
- Leaderboard: üst segment `Hafta/Tüm`, 3 kolon podyum, sıralı liste, kullanıcının satırı ve sticky “Senin sıran” barı.

## PR/Commit Öncesi Görsel Kontrol

- `./gradlew assembleDebug test` yeşil olmalı.
- Ekranda raw placeholder kalmamalı.
- Light/dark token farkları `GibTheme` üzerinden gelmeli.
- Faz 3 ekranları HTML state adlarını karşılamalı: `ev-l`, `ev-d`, `ev-detail`, `ev-create`, `ev-full`, `play-l`, `play-d`, `play-game`, `play-result`, `play-lb-l`, `play-lb-d`.
