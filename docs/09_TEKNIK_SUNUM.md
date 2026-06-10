# Teknik Sunum — GİB İnteraktif Sosyal App
> 3 dakikalık "jüri etkileme" konuşma metni + arkasındaki gerçek mühendislik gerekçeleri.

---

## 0. Açılış cümlesi (ezberlik)
> "Bu proje sadece bir hackathon ekranı değil; **production-grade, çok modüllü bir Android
> mimarisi** üzerine kurulu. Aynı kod tabanı yarın 10 ekiple büyütülebilir, çünkü her katman
> birbirinden izole, test edilebilir ve değiştirilebilir."

Bu cümle tek başına jüriye "bunlar amatör değil" mesajını veriyor — gerisi ispat.

---

## 1. Mimari: Clean Architecture + 17 modül

```
app
├── core:designsystem   (UI Kit: GibCard, PointBadge, AvatarView, BottomBar...)
├── core:common         (ApiResult, Constants — sıfır framework bağımlılığı)
├── core:network        (Retrofit/OkHttp/Json — tek noktadan ağ konfigürasyonu)
├── core:di             (Hilt — Dispatcher qualifiers)
└── feature:{tasks,shop,events,game}
        ├── domain      (saf Kotlin — UseCase + Repository interface)
        ├── data        (Retrofit API + DTO + Mapper + RepositoryImpl)
        └── presentation(Compose Screen + ViewModel + UiState)
```

**Söylenecek cümle:**
> "domain modülleri **saf Kotlin/JVM** — Android, Compose, hiçbir framework import etmiyor.
> Bu sayede iş kuralları milisaniyeler içinde, emülatörsüz unit test ediliyor."

**Neden etkileyici:** Çoğu hackathon projesi tek `app` modülünde her şeyi yazar. Burada
**5 custom Gradle convention plugin** (`gib.android.application`, `gib.android.library`,
`gib.android.compose`, `gib.android.hilt`, `gib.jvm.domain`) ile her modülün build
konfigürasyonu merkezi ve tutarlı — büyük şirketlerin (Google'ın "Now in Android" şablonu)
kullandığı yapı.

---

## 2. Backend: Supabase = Postgres + PostgREST + Auth

| Katman | Detay |
|---|---|
| Veritabanı | PostgreSQL (managed, Supabase) |
| API | Otomatik üretilen REST (PostgREST) — endpoint yazmaya gerek yok |
| İş kuralları | **Postgres RPC fonksiyonları** (stored procedures) |
| Güvenlik | Row Level Security (RLS) + `apikey`/Bearer auth |

**En güçlü argüman — atomik & idempotent iş mantığı veritabanında:**

```sql
set_task_status(task_id, status)
  -- 'done' + points_awarded=false ise → puan ekle + transaction kaydet + flag'i kapat
  -- aksi halde sadece status günceller → ÇİFT PUAN İMKANSIZ

purchase_item(item_id)
  -- bakiye >= fiyat VE envanterde yok ise → düş + ekle + tx kaydet
  -- değilse 409 Conflict

join_event(event_id)
  -- kapasite dolu değilse VE katılmamışsa → ekle
  -- değilse 409 Conflict
```

**Söylenecek cümle:**
> "Puan kazanma ve harcama mantığını **client'a değil, veritabanına** koyduk. Yani kullanıcı
> isteği iki kere gönderse, ağ koparsa ya da retry yapsa bile **çift puan veya negatif bakiye
> imkansız** — bu race-condition'lara karşı atomik garanti, tek satır mobil kod yazmadan
> Postgres transaction'ı ile sağlanıyor."

Bu, jürinin "peki ya kullanıcı hile yapmaya çalışırsa / ağ kesilirse?" sorusuna **hazır
cevap** — gerçek bir mühendislik kaygısının çözüldüğünü gösteriyor.

---

## 3. Network Katmanı: Retrofit + sealed Result + tek interceptor

```kotlin
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> = try {
    ApiResult.Success(apiCall())
} catch (e: HttpException) { ApiResult.Error(e.message(), e.code()) }
  catch (e: IOException)  { ApiResult.Error(e.message ?: "Ağ hatası...") }
```

- Tüm repository'ler tek bir `safeApiCall` ile sarmalanıyor → **try/catch tekrarı yok**.
- `AuthInterceptor` her isteğe `apikey` + `Authorization` header'ını otomatik ekler →
  feature kodları auth'tan habersiz.
- `kotlinx.serialization` ile DTO ↔ JSON, reflection yok → **derleme zamanında tip güvenliği**
  + APK'da daha küçük/performanslı serialization.

**Söylenecek cümle:**
> "Hatalı HTTP kodu, timeout, parse hatası — hepsi tek bir `ApiResult` sealed type'a
> indirgeniyor. ViewModel sadece `onSuccess` / `onFailure` ile ilgileniyor, ağ detaylarıyla
> hiç uğraşmıyor."

---

## 4. Reaktif UI: MVVM + StateFlow + sealed UiState

```
EventsViewModel ──StateFlow<EventsUiState>──► EventsScreen (Compose)
   │                  ├─ Loading
   │                  ├─ Content(events, balance, ...)
   │                  └─ Error(message)
   └─ UseCase'leri çağırır (CreateEvent, Join, Leave, GetEvents)
```

- Her ekranın `UiState`'i **sealed class/interface** → Compose'ta `when` ile exhaustive
  kontrol, "unutulan state" derleme hatası verir.
- Coroutines + Flow ile tamamen reaktif, callback yok.
- "Puan Yağmuru" mini oyununda `viewModelScope` içinde 30 saniyelik coin-spawn loop'u —
  oyun durumu da aynı sealed `GameUiState` ile yönetiliyor.

---

## 5. Test & Kalite Garantisi

- Domain katmanında **6 unit test sınıfı**, kritik iş kurallarını kapsıyor:
  - `CompleteTaskUseCaseTest` → idempotency (tekrar "Done" = puan yok)
  - `PurchaseItemUseCaseTest` / `EquipItemUseCaseTest` → bakiye & envanter kuralları
  - `CreateEventUseCaseTest` / `JoinEventUseCaseTest` → validasyon & kapasite
  - `GameUseCaseTest` → skor/leaderboard mantığı
- `mockk` + `kotlinx-coroutines-test` ile **emülatörsüz, saniyeler içinde** çalışıyor.

**Söylenecek cümle:**
> "İş mantığının kritik noktaları (idempotency, bakiye kontrolü, kapasite) unit testlerle
> kanıtlanmış durumda — demoda hata olsa bile, 'bu kural test edilmiş' diyebiliyoruz."

---

## 6. UI/UX Vurguları

- **Jetpack Compose + Material3**, Açık/Koyu tema (`ExtendedColors`, ikiye katlanan tema sistemi)
- Custom Design System (`core:designsystem`): `GibCard`, `GibPrimaryButton`, `PointBadge`,
  `AvatarView`, `LoadingShimmer`, `RewardOverlay` (ödül animasyonu), `GibBottomBar`
  (yüzen pill bar + ortada yükseltilmiş "Oyna" FAB)
- Coil ile asenkron görsel yükleme

---

## 7. "Kazan → Harca → Göster" döngüsü (1 cümlede mimari ile bağla)

> "Her kazanım yolu (Jira görevi, etkinlik, mini oyun) **aynı `point_transactions` tablosuna**
> ve **aynı `users.points_balance` alanına** yazıyor — yani 4 farklı feature modülü, tek bir
> ekonomi kaynağına bağlı. Modüller birbirini bilmiyor ama veri modeli sayesinde tutarlı."

---

## 8. Olası Jüri Soruları & Hazır Cevaplar

| Soru | Cevap |
|---|---|
| "Ölçeklenir mi?" | Supabase/Postgres managed servis; iş mantığı RPC'de olduğu için yatay ölçekte client değişmeden kalır. |
| "Güvenlik?" | RLS politikaları + apikey/Bearer; gerçek auth (Supabase Auth) entegrasyonu altyapısı `AuthInterceptor`'da hazır, sadece anon-key'den user-token'a geçiş. |
| "Neden Supabase, kendi backend'iniz değil?" | Hackathon süresinde (5 saat) custom backend yazmak yerine, Postgres'in gücünü (RPC, RLS, transaction) doğrudan kullanarak **iş kuralı kalitesinden ödün vermeden** hız kazandık. |
| "Test kapsamı?" | Domain katmanı %100 framework-free olduğu için her UseCase izole test edilebiliyor — 6 test sınıfı kritik kuralları kapsıyor. |
| "Çoklu modül neden?" | Derleme süresi, sorumluluk ayrımı, paralel geliştirme (data/UI ekipleri çakışmadan çalışabildi) ve test edilebilirlik. |
| "Bonus hedefler?" | Mimari zaten Biometric, FCM, Deeplink, Glance widget gibi modülleri ekleyecek şekilde (DI + network hazır) tasarlandı — sadece yeni feature modülü eklemek yeterli. |

---

## 9. Demo Akışı (kısa hatırlatma — detay: `05_FAZLAR.md` / `03_MIMARI_PLAN.md`)
1. Login → Home (bakiye + avatar)
2. Görevler → "Done" → **+puan animasyonu** (RewardOverlay, hero an)
3. Shop → satın al → avatara giydir → Home'da yansır
4. Etkinlik oluştur + katıl
5. Puan Yağmuru → skor → Leaderboard'da sırala
6. Kapanış: "Tüm bunlar tek bir ekonomi motoruna, atomik RPC'lere ve test edilmiş domain
   katmanına bağlı." → 1 cümlede mimariye geri dön.
