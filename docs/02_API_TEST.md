# REST API & Test Planı — GİB İnteraktif Sosyal App

> Hackathon "Test Kalitesi Beklentileri"ni karşılar: HTTP metodları + idempotency, hata kategorileri,
> boundary testleri, edge-case'ler ve **Action / Data / Expected Results** test yapısı.

---

## 1. REST API Sözleşmesi

Backend: **Supabase (PostgREST)**. Base URL: `{SUPABASE_URL}/rest/v1`. Auth header: `Authorization: Bearer <jwt>`, `apikey: <anon>`.

| # | Metod | Endpoint | Açıklama | İdempotent? | Başarı |
|---|---|---|---|---|---|
| 1 | `GET` | `/users?id=eq.{id}` | Profil + bakiye getir | ✅ | 200 |
| 2 | `PATCH` | `/users?id=eq.{id}` | Profil (display_name vb.) güncelle | ✅ | 200 |
| 3 | `GET` | `/shop_items` | Aksesuarları listele | ✅ | 200 |
| 4 | `POST` | `/rpc/purchase_item` | Aksesuar satın al (bakiye düş + envanter) | ❌ | 201 |
| 5 | `GET` | `/jira_tasks?assignee_id=eq.{id}` | Task listesi | ✅ | 200 |
| 6 | `PATCH` | `/rpc/set_task_status` | Task durumu değiştir (Done → point) | ✅ | 200 |
| 7 | `GET` | `/events` | Etkinlikleri listele | ✅ | 200 |
| 8 | `POST` | `/events` | Etkinlik oluştur | ❌ | 201 |
| 9 | `PUT` | `/events?id=eq.{id}` | Etkinliği tüm alanlarıyla değiştir | ✅ | 200 |
| 10 | `DELETE` | `/events?id=eq.{id}` | Etkinlik sil | ✅ | 204 |
| 11 | `POST` | `/rpc/join_event` | Etkinliğe katıl (kapasite kontrolü) | ❌ | 201 |
| 12 | `DELETE` | `/event_participants?event_id=eq.{e}&user_id=eq.{u}` | Etkinlikten ayrıl | ✅ | 204 |
| 13 | `POST` | `/game_scores` | Oyun skoru gönder | ❌ | 201 |
| 14 | `GET` | `/leaderboard?order=score.desc&limit=20` | Leaderboard | ✅ | 200 |

**Metod seçim gerekçeleri (idempotency prensibi):**
- `POST` → kaynak **oluşturur**, her çağrı yeni kayıt üretebilir → idempotent **değil** (satın alma, katılım, skor).
- `PUT` → kaynağı **tümüyle** değiştirir → aynı body ile tekrar = aynı sonuç → idempotent.
- `PATCH` → **kısmi** güncelleme; iş kuralıyla idempotent kılındı (task tekrar "Done" → çift puan yok).
- `DELETE` → idempotent; var olan silinir, yoksa zaten yok sayılır (204/404).
- `GET` → güvenli (safe) ve idempotent; yan etki yok.

## 2. Hata / Durum Kategorileri
| Kategori | Kod | Anlam | Örnek senaryo |
|---|---|---|---|
| **INFO** | 200 OK | Başarılı okuma/güncelleme | Bakiye getirildi |
| **INFO** | 201 Created | Kaynak oluşturuldu | Aksesuar satın alındı, etkinlik açıldı |
| **INFO** | 204 No Content | Başarılı, gövde yok | Etkinlik silindi / ayrılındı |
| **WARNING** | 202 Accepted | Kabul edildi, async işleniyor | Skor sonrası point hesabı kuyruğa alındı |
| **ERROR (4xx)** | 400 Bad Request | Geçersiz girdi / yetersiz bakiye | Negatif story-point, bakiye < fiyat |
| **ERROR (4xx)** | 401 Unauthorized | Token yok/geçersiz | Giriş yapılmadan istek |
| **ERROR (4xx)** | 403 Forbidden | Yetki yok | Başkasının etkinliğini silmek |
| **ERROR (4xx)** | 404 Not Found | Kaynak yok | Olmayan task'ı güncelle |
| **ERROR (4xx)** | 409 Conflict | Çakışma | Dolu etkinliğe katıl / tekrar katıl |
| **ERROR (5xx)** | 500 Internal | Sunucu hatası | Beklenmeyen DB hatası |

## 3. Test Senaryoları (Action / Data / Expected Results)

### TC-01 — Jira "Done" point kazanımı (happy path)
| Action | Data | Expected Results |
|---|---|---|
| `PATCH /rpc/set_task_status` | `{task: GIB-12, status: "done"}`, sp=5, bakiye=0 | 200 OK · bakiye=50 · PointTx(+50, jira) oluşur |

### TC-02 — Idempotency (çift Done)
| Action | Data | Expected Results |
|---|---|---|
| Aynı task'ı 2. kez "Done" PATCH et | task zaten done, points_awarded=true | 200 OK · bakiye **değişmez** · yeni PointTx **yok** |

### TC-03 — Story-point boundary testleri
| Action | Data (story_points) | Expected Results |
|---|---|---|
| Task Done | **-1** (min-1) | 400 Bad Request (geçersiz) |
| Task Done | **0** (min) | 200 OK · +0 point |
| Task Done | **1** (min+1) | 200 OK · +10 point |
| Task Done | **20** (max-1) | 200 OK · +200 point |
| Task Done | **21** (max) | 200 OK · +210 point |
| Task Done | **22** (max+1) | 400 Bad Request (sınır dışı) |

### TC-04 — Satın alma bakiye boundary (fiyat=50)
| Action | Data (bakiye) | Expected Results |
|---|---|---|
| `POST /rpc/purchase_item` | **49** (price-1) | 400 · "Yetersiz bakiye" · bakiye 49 kalır |
| `POST /rpc/purchase_item` | **50** (price) | 201 · bakiye=0 · envantere eklenir |
| `POST /rpc/purchase_item` | **51** (price+1) | 201 · bakiye=1 |

### TC-05 — Tekrar satın alma (çakışma)
| Action | Data | Expected Results |
|---|---|---|
| Sahip olunan aksesuarı tekrar al | item zaten user_items'da | 409 Conflict · bakiye değişmez |

### TC-06 — Etkinlik kapasite boundary (capacity=2)
| Action | Data (mevcut katılımcı) | Expected Results |
|---|---|---|
| `POST /rpc/join_event` | **1** (max-1) | 201 · katılım eklenir |
| `POST /rpc/join_event` | **2** (max) | 409 Conflict · "Kapasite dolu" |

### TC-07 — Etkinlik oluşturma input validasyon
| Action | Data (title) | Expected Results |
|---|---|---|
| `POST /events` | `""` (boş) | 400 Bad Request |
| `POST /events` | `null` | 400 Bad Request |
| `POST /events` | `"A"` (min=1) | 201 Created |
| `POST /events` | 80 karakter (max) | 201 Created |
| `POST /events` | 81 karakter (max+1) | 400 Bad Request |
| `POST /events` | `starts_at` geçmiş tarih | 400 Bad Request |

### TC-08 — Yetkilendirme & güvenlik edge-case
| Action | Data | Expected Results |
|---|---|---|
| Token'sız `GET /users` | header yok | 401 Unauthorized |
| Başka kullanıcının etkinliğini sil | `DELETE /events?id=eq.{x}` | 403 Forbidden |
| SQL injection denemesi | `title = "'; DROP TABLE..."` | Parametrik sorgu · 400/normal kayıt · tablo zarar görmez |
| Olmayan task güncelle | `id = 999999` | 404 Not Found |

### TC-09 — Leaderboard sıralama & limit
| Action | Data | Expected Results |
|---|---|---|
| `GET /leaderboard?order=score.desc&limit=20` | 25 skor mevcut | 200 · ilk 20 · skor azalan sırada |
| `POST /game_scores` | `score = -5` | 400 (negatif skor reddi) |

### TC-10 — UI / Edge davranışları
| Action | Data | Expected Results |
|---|---|---|
| Ağ yokken Shop aç | offline | INFO mesajı + cache/retry, çökme yok |
| Boş task listesi | 0 task | "Görev yok" boş-durum ekranı |

## 4. Test Edilebilirlik Notları
- **Unit test** (`app/src/test`): point hesabı (`storyPoints * 10`), bakiye düşme, idempotency guard mantığı.
- **Instrumented/UI test** (`app/src/androidTest`): Compose ekran akışları (satın alma → bakiye düşer).
- Boundary değerleri merkezî sabitlerden (`Constants.kt`) okunur ki test ve kod aynı sınırları paylaşsın.
