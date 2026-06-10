# Geliştirme Fazları — GİB İnteraktif Sosyal App

> Mimari: **Multi-Module + Clean Architecture + MVVM** · UI: Compose (Material 3) · Backend: Supabase
> Her faz, **bağımsız çalışan ve build alan** bir çıktı bırakır. Faz sonunda "Definition of Done" kontrol edilir.

---

## Mimari Genel Bakış

### Modül Bağımlılık Haritası
```
:app  (Application, Hilt graph, MainActivity, NavHost, bottom nav)
 │
 ├─► :core:designsystem   (Compose theme + UI Kit token'ları + ortak component'ler)
 ├─► :core:common         (Result/ApiResult, Dispatchers, extension'lar, base)
 ├─► :core:network        (Retrofit + OkHttp + Supabase config, AuthInterceptor)
 ├─► :core:di             (app-scope Hilt modülleri: Dispatchers, qualifiers)
 │
 ├─► :feature:tasks:presentation ─► :feature:tasks:domain
 ├─► :feature:tasks:data ─────────► :feature:tasks:domain   (+ :core:network, :core:common)
 │      ⮑ (REFERANS ÖZELLİK — tam 3 katman split, pattern'i kanıtlar)
 │
 ├─► :feature:home:*      (dashboard: hero kart, hızlı eylemler, mini leaderboard)
 ├─► :feature:shop:*      (avatar canvas + shop + satın alma)
 ├─► :feature:events:*    (liste/detay/oluştur/katıl, kapasite)
 ├─► :feature:game:*      ("Puan Yağmuru" + leaderboard)
 ├─► :feature:profile:*   (kullanıcı + rozet + puan geçmişi)
 └─► :feature:auth:*      (giriş + biometrik)
```
> Ekran içerikleri = [`07_EKRAN_ENVANTERI.md`](07_EKRAN_ENVANTERI.md) (UI Kit'ten birebir). Bottom nav 5 sekme:
> Ana Sayfa · Görevler · Oyna · Etkinlik · Profil. Her ekranın Açık + Koyu varyantı UI Kit'te hazır.

### Bağımlılık Kuralları (içe doğru — Clean Architecture)
- `presentation → domain`, `data → domain` · **`domain` saf Kotlin** (sadece coroutines-core; Android yok).
- `presentation → :core:designsystem, :core:common` · `data → :core:network, :core:common`.
- **Hiçbir feature başka bir feature'ın `data`/`presentation`'ına bağlanmaz.** (Ortak ihtiyaç → `:core`.)
- `:app` tüm `feature:*:data` + `feature:*:presentation` modüllerine bağlanır (Hilt graph birleşsin).
- DTO → Domain mapping `data` katmanında zorunlu. UI yalnız Domain Model + UiState bilir.

### Boilerplate'i ucuzlatan kural: `build-logic` convention plugin'leri
Çok sayıda modülde Gradle tekrarını önlemek için `build-logic/` altında convention plugin'ler:
`gib.android.library`, `gib.android.compose`, `gib.android.hilt`, `gib.jvm.domain`.
Her modül 3–5 satırlık `build.gradle.kts` ile bu plugin'leri uygular. (KISS + DRY.)

---

## Faz 0 — Proje & Mimari İskelet (Foundation)
**Amaç:** Boş ama mimari olarak eksiksiz, build alan ve gezilebilen uygulama.
- `build-logic` convention plugin'leri + version catalog (mevcut `libs.versions.toml` genişletilir: Hilt, Retrofit, kotlinx-serialization, Navigation, Coil, DataStore, Biometric).
- `:core:common`, `:core:network` (Supabase Retrofit + AuthInterceptor + `ApiResult`), `:core:di`, `:core:designsystem` (UI Kit token'larından `GibTheme`, renkler, tipografi, şekiller + `PointBadge`, `AvatarView`, `GibBottomBar`, `EmptyState` iskeletleri).
- `:app`: `Application` + Hilt + `NavHost` + bottom nav; her sekme placeholder ekran.
- **DoD:** Uygulama açılıyor, 5 sekme arasında geçiş yapılıyor, light/dark tema çalışıyor, `./gradlew assembleDebug` yeşil.

## Faz 1 — Referans Özellik: Görevler (Jira → Point) — HERO
**Amaç:** Tam dikey kesit; mimari pattern'i kanıtlar + çekirdek döngünün "kazan" ayağı + test hikâyesi.
- `:feature:tasks:domain`: `Task` modeli, `TasksRepository` interface, `GetTasksUseCase`, `CompleteTaskUseCase` (idempotent point).
- `:feature:tasks:data`: `TaskDto`, `TaskApi` (Retrofit), `TaskMapper` (DTO→Domain), `TasksRepositoryImpl`, Hilt `TasksDataModule`.
- `:feature:tasks:presentation`: `TasksViewModel`, `TasksUiState` (ayrı dosya), `TasksScreen` (Compose), reward overlay (`+50 🪙` + confetti).
- Supabase RPC `set_task_status` ile bakiye atomik artar; ekran bakiyeyi yeniler.
- **DoD:** Task listesi geliyor, "Done" → point animasyonu → bakiye artıyor; tekrar Done çift puan vermiyor. `CompleteTaskUseCase` için unit test yeşil.

## Faz 2 — Avatar & Shop + Point Cüzdanı
**Amaç:** Döngünün "harca + göster" ayağı; görsel vitrin.
- `:feature:shop` (3 katman): `ShopItem`, `GetShopItemsUseCase`, `PurchaseItemUseCase` (bakiye kontrolü), `EquipItemUseCase`.
- Avatar canvas (katmanlı: base + hat/glasses/outfit/bg), kategori sekmeleri, item grid (price / Sahip / Giyili / yetersiz-bakiye durumları).
- **DoD:** Aksesuar satın alınıyor (bakiye düşüyor), giydiriliyor, avatar canlı güncelleniyor; yetersiz bakiye 400 ile reddediliyor.

## Faz 3 — Sosyal: Etkinlikler + Oyun/Leaderboard
**Amaç:** Özellik setini tamamla.
- `:feature:events`: liste/detay/oluştur/katıl-ayrıl, kapasite kontrolü, form validasyonu (boş başlık/geçmiş tarih).
- `:feature:game`: tek kişilik mini oyun + skor gönderme + leaderboard (podyum + sıralı liste, kullanıcı satırı vurgulu).
- **DoD:** Etkinlik oluşturulup katılınıyor (dolu → 409); oyun oynanıp skor leaderboard'a düşüyor + point kazanılıyor.

## Faz 4 — Bonus & Polish
**Amaç:** Bonus puanlar + kalite.
- Biometrik giriş (BiometricPrompt) · Dark mode doğrulama · FCM push (görev/etkinlik bildirimi) · Deeplink (`gibapp://event/{id}`) · (zaman varsa) Glance widget (bakiye).
- Loading (skeleton) / empty / error / offline durumları tüm ekranlarda.
- **DoD:** En az 2 bonus çalışıyor; min. 3 ekran görüntüsü (PNG) alındı; demo'da çökme yok.

## Faz 5 — Test & Teslim
**Amaç:** Puanı kilitle.
- Unit test: point hesabı, idempotency guard, bakiye düşme, kapasite. UI test: satın alma akışı.
- `01_ANALIZ.md` + `02_API_TEST.md` → PDF export. README final. Demo senaryosu provası.
- **DoD:** 16:30'dan önce son commit, public repo, README mevcut, testler yeşil.

---

## Hackathon Zaman Riski (Senior Architect notu)
Tam multi-module split (7 özellik × 3 modül + 4 core ≈ 25 modül) **5 saatte risklidir.** Strateji:
- **Faz 0 + Faz 1**'i tam split ile yap → mimari ustalığı *referans özellikte* kanıtla (Teknik Zorluk %20).
- Süre daralırsa **Faz 2+** özelliklerini *tek modül içinde data/domain/presentation paketleri* olarak yaz (lite mod) — pattern aynı, Gradle yükü düşük.
- Convention plugin'ler bu kararı ucuzlatır; karar Faz 1 bitiş saatine göre verilir.
