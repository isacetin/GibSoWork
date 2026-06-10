# Yeni Sohbet Kickoff Promptu

> Yeni bir Claude Code sohbetinde **bu repoyu açıp** aşağıdaki promptu yapıştır.
> Ekstra olarak şunları da bağla/aç: `GİB İnteraktif UI Kit (standalone).html` (görsel referans) ve `docs/` klasörü.

---

```
Rolün: Senior Android Architect & Developer.
Bu repo (GibInteraktifSosyalApp) bir hackathon projesinin başlangıç iskeletidir (boş, tek-modül
Compose scaffold). Görevin: onu Multi-Module + Clean Architecture + MVVM mimarisine dönüştürmek
ve REFERANS özelliği uçtan uca yazmak. Önce Faz 0 + Faz 1'i yap, SONRA durup onay iste.

Tüm bağlam repodaki docs/ klasöründedir — ÖNCE OKU:
- docs/01_ANALIZ.md  → ürün, kapsam, veri alanları, kabul kriterleri
- docs/02_API_TEST.md → REST sözleşmesi (HTTP metodları, status kodları, idempotency) + test planı
- docs/03_MIMARI_PLAN.md → SQL şema, RPC'ler, ekran listesi
- docs/05_FAZLAR.md   → fazlar + modül bağımlılık haritası (BUNU TAKİP ET)
- docs/07_EKRAN_ENVANTERI.md → her ekranın içeriği/durumu/metni (UI'ın TEK doğruluk kaynağı)
Görsel referans: "GİB İnteraktif UI Kit (standalone).html" — ekran tasarımlarının kaynağıdır.

═══════ MİMARİ KURALLAR (KESİN) ═══════
- SOLID / DRY / KISS / YAGNI. Interface-driven. "İleride lazım olur" diye fazladan kod yok.
- Modüller (docs/05_FAZLAR.md'deki harita):
  :app · :core:designsystem · :core:common · :core:network · :core:di · :feature:<f>:{domain,data,presentation}
- Bağımlılık yönü içe doğru: presentation→domain, data→domain. domain SAF KOTLIN (yalnız
  kotlinx-coroutines-core; Android importu YASAK). Hiçbir feature başka feature'a bağlanmaz.
- :app, tüm feature:data + feature:presentation modüllerine bağlanır (Hilt graph birleşsin).
- DOSYA AYRIMI (çok kritik):
  * DTO, Domain Model ve UiState AYRI .kt dosyalarında. UI dosyasına gömülü (nested) data class YOK.
  * Hilt @Module'ler business/UI sınıfı içinde DEĞİL; her modülün DI'ı kendi katmanında ayrı dosyada.
  * Data→Domain geçişinde DTO mutlaka Mapper ile Domain Model'e çevrilir. UI yalnız Domain/UiState bilir.
- MVVM: ViewModel sadece UseCase tetikler + StateFlow<UiState> yönetir. Compose sadece state observe
  eder, iş mantığı/karar içermez.

═══════ TEKNİK STACK ═══════
Kotlin + Jetpack Compose (Material3) · Hilt (DI) · Retrofit + OkHttp + kotlinx.serialization ·
Navigation-Compose · Coil · DataStore · Coroutines/Flow · androidx.biometric (bonus).
minSdk 26 / targetSdk 36. Mevcut gradle/libs.versions.toml'u GENİŞLET (sıfırdan yazma).
build-logic/ altında convention plugin'ler kur: gib.android.library, gib.android.compose,
gib.android.hilt, gib.jvm.domain — modül build dosyaları minimal kalsın (DRY).
Backend: Supabase (PostgREST). URL/anon key local.properties'ten BuildConfig'e enjekte edilir.

═══════ DESIGN SYSTEM TOKEN'LARI (:core:designsystem → GibTheme) ═══════
LIGHT:  primary #5B5BD6 · primaryContainer #E6E6FB · accent/points #FFB020 · success #1FB873 ·
        danger #E5484D · background #FAFAFD · surface #FFFFFF · surfaceVariant #F6F6FB ·
        outline #E2E2EC · onSurface #16161D · onSurfaceVariant #6B6B7B
DARK:   primary #8E8EF5 · accent #FFC24D · success #1FB873 · danger #FF8A80 ·
        background #0E0E14 · surface #181820 · surfaceVariant #22222E ·
        onSurface #F2F2F7 · onSurfaceVariant #A0A0B0
SHAPE:  card 20dp · button 16dp · pill 999 · avatar canvas 28dp.  TYPE: Display 28/700,
        Title 20/600, Body 15/400, Label 13/500. Point değerleri DAİMA bold + gold + coin ikon.
Ortak component'ler (designsystem'de, ayrı dosyalar): PointBadge, AvatarView, GibBottomBar,
GibPrimaryButton, RewardOverlay (confetti), EmptyState, LoadingShimmer.

═══════ REFERANS ÖZELLİK: feature:tasks (Jira → Point) ═══════
Bu, mimarinin kanıtı + ürünün hero akışı. Tam 3 katman split ile yaz.
REST (docs/02_API_TEST.md):
  GET   {base}/rest/v1/jira_tasks?assignee_id=eq.{id}   → 200, List<TaskDto>
  PATCH {base}/rest/v1/rpc/set_task_status  body{task_id,status} → 200; status="done" & henüz
        ödenmemişse bakiye += story_points*10 (idempotent: tekrar done = çift puan YOK).
- domain: Task(key,title,storyPoints,status,reward), TasksRepository(interface),
          GetTasksUseCase, CompleteTaskUseCase.
- data: TaskDto, TaskStatusDto, TaskApi(Retrofit), TaskMapper(DTO→Domain),
        TasksRepositoryImpl, TasksDataModule(Hilt @Module @InstallIn(SingletonComponent)).
- presentation: TasksUiState(ayrı dosya: Loading/Content(list,balance)/Error/Empty),
        TasksViewModel(@HiltViewModel), TasksScreen + TaskCard + RewardOverlay.
- Kabul: AC-01 (sp=5 → +50), AC-02 (idempotency) — docs/01_ANALIZ.md. CompleteTaskUseCase'e unit test.

═══════ İLK TESLİMAT (bu sohbette) ═══════
1) Faz 0: build-logic + version catalog + :core:* modülleri + :app NavHost/bottom nav + GibTheme +
   placeholder ekranlar. `./gradlew assembleDebug` YEŞİL olmalı.
2) Faz 1: feature:tasks tam dikey kesit + unit test.
3) Sonra DUR, ne yaptığını özetle, build/test sonucunu göster, Faz 2 için onay iste.

Çalışırken: değişiklikten önce ilgili dosyaları oku, küçük commit'ler yap, her modül derlensin.
Mevcut com.isacetin.gibinteraktifsosyalapp paketini base alarak modül namespace'lerini türet.
```
