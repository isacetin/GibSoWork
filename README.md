# GİB İnteraktif Sosyal App

> **GİB Mobil Hackathon 2026** — Konu 2: *Corporate Playground & Social Fun*
> Oyunlaştırılmış şirket içi sosyalleşme uygulaması.

Çalışanlar **çalışarak** (Jira task'larını tamamlayarak) ve **eğlenerek** (etkinlikler + mini oyun) **point** kazanır; bu point'lerle **avatarlarına aksesuar** alır. Tek bir engagement loop etrafında kurulu: **Kazan → Harca → Göster.**

## 🎯 Çekirdek Döngü (Core Loop)
```
Jira task "Done"  ─┐
Etkinliğe katıl   ─┼─►  POINT kazan  ──►  Shop'tan aksesuar al  ──►  Avatarı özelleştir
Mini oyun + skor  ─┘                                                      │
                                            Leaderboard'da yüksel  ◄──────┘
```

## ✨ Özellikler (MVP)
- **Avatar & Shop** — katmanlı avatar, point ile aksesuar satın alma/giyme.
- **Mock Jira** — task listesi; "Done" çekilince story-point oranında point. *(Idempotent: tekrar Done = çift puan yok.)*
- **Etkinlikler (Events)** — oluştur, listele, katıl/ayrıl; kapasite kontrolü.
- **"Puan Yağmuru" + Leaderboard** — 30 sn'de düşen coinleri yakala, şirket genelinde sıralan, point kazan.
- **Point Ekonomisi** — tüm kaynakları birleştiren cüzdan + işlem geçmişi.

## 🛠 Teknik Stack
| Katman | Teknoloji |
|---|---|
| UI | Jetpack Compose + Material3 (Dark Mode) |
| Mimari | MVVM + Repository, Kotlin Coroutines/Flow |
| Ağ | Retrofit + OkHttp + kotlinx.serialization |
| Backend | **Supabase** (PostgREST REST API + Auth + Postgres) |
| Navigasyon | Navigation-Compose |
| Görsel | Coil |
| minSdk / target | 26 / 36 |

## 🎁 Bonus Hedefleri
- [ ] Biometrik giriş (BiometricPrompt) · [ ] Dark Mode · [ ] Push (FCM)
- [ ] Deeplink (`gibapp://event/{id}`) · [ ] Home Widget (point bakiyesi, Glance) · [ ] AI/LLM (günlük görev önerisi)

## 📦 Kurulum
1. `local.properties` içine Supabase bilgilerini ekle:
   ```
   SUPABASE_URL=https://xxxx.supabase.co
   SUPABASE_ANON_KEY=eyJhbGci...
   ```
2. Supabase'de `docs/03_MIMARI_PLAN.md` içindeki SQL şemasını çalıştır + seed verisini ekle.
3. Android Studio'da aç → Run.

## 📁 Dokümantasyon
- [`docs/01_ANALIZ.md`](docs/01_ANALIZ.md) — Analiz dokümanı (8 zorunlu başlık)
- [`docs/02_API_TEST.md`](docs/02_API_TEST.md) — REST API + Test planı (HTTP metodları, boundary, hata yönetimi)
- [`docs/03_MIMARI_PLAN.md`](docs/03_MIMARI_PLAN.md) — Mimari, veri modeli, ekranlar, zaman planı
- [`docs/04_TASARIM_PROMPT.md`](docs/04_TASARIM_PROMPT.md) — Claude Design ekran tasarım promptu
- [`docs/05_FAZLAR.md`](docs/05_FAZLAR.md) — Multi-module mimari + geliştirme fazları
- [`docs/06_KICKOFF_PROMPT.md`](docs/06_KICKOFF_PROMPT.md) — Yeni sohbet kodlama başlangıç promptu
- [`docs/07_EKRAN_ENVANTERI.md`](docs/07_EKRAN_ENVANTERI.md) — UI Kit'ten türetilmiş ekran envanteri (tek doğruluk kaynağı)
- [`docs/08_UI_KIT_COMPOSE_MAPPING.md`](docs/08_UI_KIT_COMPOSE_MAPPING.md) — UI Kit → Compose sadakat rehberi

## 👥 Ekip
- Geliştirici 1 — Data/Backend katmanı
- Geliştirici 2 — UI/Compose katmanı
- Analist/Test — Analiz dokümanı + test senaryoları
