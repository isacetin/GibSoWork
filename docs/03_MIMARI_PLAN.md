# Mimari, Veri Modeli, Ekranlar & Zaman Planı

---

## 1. Mimari (MVVM + Repository)

```
┌─────────────── UI (Compose) ───────────────┐
│ Screens  ←→  ViewModels (StateFlow)         │
└───────────────────┬─────────────────────────┘
                    │  (domain models)
┌───────────────────▼─────────────────────────┐
│ Repository (interface + impl)                │
│  - mapper: DTO ↔ Domain                      │
└───────────────────┬─────────────────────────┘
        ┌───────────┴───────────┐
┌───────▼────────┐      ┌────────▼─────────┐
│ Retrofit API   │      │ DataStore        │
│ (Supabase REST)│      │ (token, session) │
└────────────────┘      └──────────────────┘
```

**Paket yapısı** (`com.isacetin.gibinteraktifsosyalapp`):
```
data/
  remote/   ApiService.kt, dto/*, AuthInterceptor.kt
  repo/     UserRepository, JiraRepository, ShopRepository, EventRepository, GameRepository
  local/    SessionStore.kt (DataStore)
domain/
  model/    User, ShopItem, JiraTask, Event, GameScore
  Constants.kt  (POINT_PER_SP=10, SP_MIN=0, SP_MAX=21, TITLE_MAX=80, ...)
ui/
  home/ avatar/ shop/ jira/ events/ game/ login/
  theme/  (mevcut)
  components/  PointBadge.kt, AvatarView.kt, EmptyState.kt
MainActivity.kt  (NavHost)
```

**Önerilen ek bağımlılıklar** (`libs.versions.toml`'a ekle):
- `retrofit`, `converter-kotlinx-serialization`, `okhttp-logging-interceptor`
- `kotlinx-serialization-json`, `kotlin("plugin.serialization")`
- `navigation-compose`, `lifecycle-viewmodel-compose`
- `coil-compose`, `androidx-datastore-preferences`
- `androidx-biometric` (bonus), `material-icons-extended`

## 2. Veri Modeli (Supabase SQL — kurulumda çalıştır)

```sql
create table users (
  id uuid primary key references auth.users,
  email text not null,
  display_name text not null check (char_length(display_name) between 1 and 50),
  department text,
  points_balance int not null default 0 check (points_balance >= 0)
);

create table shop_items (
  id uuid primary key default gen_random_uuid(),
  name text not null,
  category text not null check (category in ('hat','glasses','outfit','bg')),
  price int not null check (price >= 0),
  rarity text default 'common',
  asset_key text not null
);

create table user_items (
  user_id uuid references users, item_id uuid references shop_items,
  acquired_at timestamptz default now(), primary key (user_id, item_id)
);

create table avatars (
  user_id uuid primary key references users,
  base_id text not null default 'base_01',
  equipped_items jsonb default '{}'::jsonb
);

create table jira_tasks (
  id uuid primary key default gen_random_uuid(),
  key text unique not null, title text not null,
  story_points int not null check (story_points between 0 and 21),
  status text not null default 'todo' check (status in ('todo','in_progress','done')),
  assignee_id uuid references users,
  points_awarded boolean not null default false
);

create table events (
  id uuid primary key default gen_random_uuid(),
  title text not null check (char_length(title) between 1 and 80),
  description text, location text,
  starts_at timestamptz not null, ends_at timestamptz,
  capacity int not null check (capacity between 1 and 500),
  created_by uuid references users, created_at timestamptz default now()
);

create table event_participants (
  event_id uuid references events, user_id uuid references users,
  joined_at timestamptz default now(), primary key (event_id, user_id)
);

create table game_scores (
  id uuid primary key default gen_random_uuid(),
  user_id uuid references users, game_id text not null,
  score int not null check (score >= 0), created_at timestamptz default now()
);

create table point_transactions (
  id uuid primary key default gen_random_uuid(),
  user_id uuid references users, amount int not null,
  source text not null check (source in ('jira','game','event','purchase')),
  ref_id text, created_at timestamptz default now()
);

create view leaderboard as
  select u.display_name, max(g.score) as score
  from game_scores g join users u on u.id = g.user_id
  group by u.display_name;
```

**Kritik iş kuralları (Postgres RPC fonksiyonları — atomiklik + idempotency):**
- `set_task_status(task_id, status)` → status='done' ve `points_awarded=false` ise: `points += sp*10`, PointTx ekle, `points_awarded=true`. Aksi halde sadece status günceller (çift puan **yok**).
- `purchase_item(item_id)` → bakiye ≥ fiyat ve item envanterde değilse: bakiye düş, user_items'a ekle, PointTx(-price, purchase). Değilse 400/409.
- `join_event(event_id)` → katılımcı < capacity ve daha önce katılmamışsa ekle; değilse 409.

## 3. Ekran Listesi
> **Tek doğruluk kaynağı: [`07_EKRAN_ENVANTERI.md`](07_EKRAN_ENVANTERI.md)** — UI Kit'in render'ından birebir
> çıkarıldı. Bottom nav 5 sekme: **Ana Sayfa · Görevler · Oyna · Etkinlik · Profil**.

| Sekme/Bölüm | Ekran & durumlar | Bonus |
|---|---|---|
| Ana Sayfa | Dashboard (Açık/Koyu): hero kart, hızlı eylemler, mini leaderboard | Widget besler |
| Görevler | Jira: liste, **ödül anı**, boş durum | — |
| Oyna | "Puan Yağmuru" landing/oyun içi/sonuç + Sıralama (podyum) | — |
| Etkinlik | Liste, detay, oluştur (validasyon), kapasite dolu | Deeplink |
| Profil | Profil: istatistik, rozetler, puan geçmişi | — |
| Avatar & Shop | Canvas + kategori + item grid + satın alma onayı + yetersiz bakiye | Tasarım vitrin |
| Giriş | E-posta + "Face ID ile gir" | Biometrik |
| Global | Nav (2 varyant) · skeleton · başarı toast · hata · çevrimdışı | — |

**Tema:** Material3, **Açık + Koyu** her ekran için UI Kit'te mevcut (Dark Mode bonusu baştan).

## 4. Zaman Planı & Görev Dağılımı (~5 saat net)

> **Dev A** = Data/Backend · **Dev B** = UI/Compose · **QA** = Analist/Test

| Saat | Dev A | Dev B | QA |
|---|---|---|---|
| 10:15–10:45 **Setup** | Supabase proje + SQL şema + seed | Nav iskeleti + tema + Home placeholder | ANALIZ §1–4 yazımı |
| 10:45–13:00 **Blok 1** | Retrofit + Auth + User/Jira/Shop repo + RPC'ler | Home + Avatar/Shop ekranı + PointBadge | API tablosu + ANALIZ §5–6 |
| 13:00–14:00 | — Öğle — | | |
| 14:00–15:30 **Blok 2** | Jira point mantığı + Events + Game/leaderboard API | Jira + Events + Game ekranları | Test senaryoları (TC) + boundary |
| 15:30–16:00 **Bonus+Polish** | FCM push / biometrik backend | Dark mode + boş durumlar + 3 screenshot | ANALIZ §7–8 + edge-case TC |
| 16:00–16:15 **Freeze** | README final + push (16:30 hard) | son commit | PDF export (ANALIZ + TEST) |
| (paralel) | | | Demo senaryosu yazımı |

**Risk kuralı:** Bonuslar (push/widget/AI) ancak çekirdek loop bittiyse. Oyun çalışmıyorsa demodan çıkar — leaderboard'ı statik seed ile göster.

## 5. Demo Senaryosu (10 dk)
1. (1 dk) Problem + tek cümle pitch — "Çalış, Kazan, Harca."
2. (1 dk) Login (Face ID) → Home, bakiye + avatar.
3. (2 dk) Jira → task'ı Done → **+50 point animasyonu** (hero an).
4. (2 dk) Shop → aksesuar al → avatara giydir → Home'da yansır.
5. (1.5 dk) Etkinlik oluştur + katıl.
6. (1.5 dk) Mini oyun → skor → leaderboard'da sırala.
7. (1 dk) Bonuslar + mimari + test kalitesi vurgusu (idempotency, boundary).
