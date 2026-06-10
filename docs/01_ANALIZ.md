# Analiz Dokümanı — GİB İnteraktif Sosyal App

> Bu doküman, hackathon "Analiz Standardı"ndaki **8 zorunlu başlığı** karşılar.

---

## 1. Proje Bilgileri
- **Proje Adı:** GİB İnteraktif Sosyal App
- **Seçilen Konu:** Konu 2 — *Corporate Playground & Social Fun*
- **Tek Cümle Tanım:** Çalışanların işlerini yaparak ve birlikte eğlenerek point kazandığı, bu point'lerle avatarlarını özelleştirdiği oyunlaştırılmış şirket içi sosyalleşme uygulaması.
- **Ekip:** 2 Geliştirici + 1 Analist/Test Uzmanı
- **Platform:** Android (Kotlin, Jetpack Compose), Backend: Supabase

## 2. Problem Tanımı
- **Problem:** Hibrit/uzaktan çalışma arttıkça çalışanlar arasındaki sosyal bağ zayıflıyor; ofis içi etkileşim ve motivasyon düşüyor, günlük iş başarıları görünmez kalıyor ve takdir edilmiyor.
- **Hedef Kullanıcı:** Şirketin tüm beyaz yaka çalışanları (geliştirici, analist, yönetici); özellikle takım içi etkileşime ihtiyaç duyan hibrit çalışanlar.
- **Amaç:** İş başarısını (Jira task'ları) ve sosyal katılımı (etkinlik + oyun) ortak bir **point ekonomisi** ile ödüllendirerek motivasyonu ve şirket içi bağı eğlenceli biçimde artırmak.

## 3. Kullanıcı Hikâyeleri
1. **Kullanıcı olarak**, tamamladığım Jira task'larından point kazanmak istiyorum, **çünkü** günlük emeğimin görünür ve ödüllendirilmiş olmasını istiyorum.
2. **Kullanıcı olarak**, kazandığım point'lerle avatarıma yeni aksesuarlar almak istiyorum, **çünkü** profilimi kişiselleştirip takım arkadaşlarıma göstermek bana keyif veriyor.
3. **Kullanıcı olarak**, şirket içi etkinlikleri görüp tek dokunuşla katılmak istiyorum, **çünkü** öğle/kahve molalarını arkadaşlarımla planlamak istiyorum.
4. **Kullanıcı olarak**, mola aralarında mini oyun oynayıp leaderboard'da sıralanmak istiyorum, **çünkü** kısa bir eğlence ve takımca rekabet ofis stresini azaltıyor.

## 4. Kapsam
**Dâhil (In Scope):**
- Kullanıcı girişi (Supabase Auth) ve profil.
- Avatar oluşturma + Shop (aksesuar satın alma/giyme).
- Mock Jira task listesi + durum değiştirme + story-point bazlı point kazanımı.
- Etkinlik oluşturma / listeleme / katılma-ayrılma (kapasite kontrolü).
- Tek kişilik mini oyun + global leaderboard + oyundan point.
- Point cüzdanı + işlem geçmişi.

**Hariç (Out of Scope):**
- Gerçek Jira/Atlassian entegrasyonu (mock REST ile simüle edilir).
- Çok oyunculu gerçek zamanlı (realtime PvP) oyun.
- Toplantı odası rezervasyonu (bu sürümde yok).
- Ödeme/gerçek para, çalışan IK/bordro entegrasyonu.
- Admin paneli / web yönetim arayüzü.

## 5. Fonksiyonel Gereksinimler
> Net, uygulanabilir, tek cümle.

- **FR-01:** Sistem, kullanıcının e-posta ile giriş yapmasını sağlamalıdır.
- **FR-02:** Sistem, kullanıcının güncel point bakiyesini göstermelidir.
- **FR-03:** Sistem, Jira task listesini ve her task'ın story-point değerini göstermelidir.
- **FR-04:** Sistem, bir task "Done" durumuna alındığında story-point × 10 kadar point eklemelidir.
- **FR-05:** Sistem, aynı task ikinci kez "Done" yapıldığında tekrar point vermemelidir (idempotent).
- **FR-06:** Sistem, Shop'taki aksesuarları fiyatlarıyla listelemelidir.
- **FR-07:** Sistem, bakiyesi yeterliyse aksesuarı satın alıp kullanıcı envanterine eklemelidir.
- **FR-08:** Sistem, bakiyesi yetersizse satın almayı reddetmelidir.
- **FR-09:** Sistem, sahip olunan aksesuarın avatara giydirilmesini sağlamalıdır.
- **FR-10:** Sistem, kullanıcının etkinlik oluşturmasını sağlamalıdır.
- **FR-11:** Sistem, kapasite dolmadıysa kullanıcının etkinliğe katılmasını sağlamalıdır.
- **FR-12:** Sistem, kapasite doluysa katılımı reddetmelidir.
- **FR-13:** Sistem, mini oyun skorunu leaderboard'a kaydetmelidir.
- **FR-14:** Sistem, leaderboard'ı skora göre azalan sırada göstermelidir.

## 6. Kabul Kriterleri (Given / When / Then)
> Test edilebilir.

- **AC-01 (Jira point):**
  *Given* bakiyesi 0 olan ve story-point'i 5 olan bir task'a sahip kullanıcı,
  *When* task'ı "Done" durumuna alır,
  *Then* bakiyesi 50 olur ve işlem geçmişine "+50 (jira)" kaydı eklenir.
- **AC-02 (Idempotency):**
  *Given* zaten "Done" olup point'i verilmiş bir task,
  *When* task tekrar "Done" olarak PATCH edilir,
  *Then* bakiye değişmez ve HTTP 200 döner.
- **AC-03 (Yetersiz bakiye):**
  *Given* bakiyesi 40 olan kullanıcı ve fiyatı 50 olan aksesuar,
  *When* kullanıcı satın almayı dener,
  *Then* satın alma reddedilir, HTTP 400 döner ve bakiye 40 kalır.
- **AC-04 (Başarılı satın alma):**
  *Given* bakiyesi 100 olan kullanıcı ve fiyatı 50 olan aksesuar,
  *When* satın alır,
  *Then* bakiye 50 olur, aksesuar envantere eklenir, HTTP 201 döner.
- **AC-05 (Etkinlik kapasitesi):**
  *Given* kapasitesi 1 ve 1 katılımcısı olan etkinlik,
  *When* ikinci kullanıcı katılmak ister,
  *Then* katılım reddedilir ve HTTP 409 (Conflict) döner.
- **AC-06 (Tekrar katılım):**
  *Given* etkinliğe zaten katılmış kullanıcı,
  *When* tekrar katılmayı dener,
  *Then* HTTP 409 döner ve katılımcı sayısı artmaz.

## 7. Ekran / Akış
**Ana Akış — "Çalış → Kazan → Harca":**
1. **Başlangıç:** Giriş ekranı → (biometrik) → Ana Sayfa (point bakiyesi + avatar önizleme).
2. **Adım:** Ana Sayfa → "Görevlerim" → Jira task listesi.
3. **Adım:** Task'ı "Done" durumuna al → point animasyonu → bakiye güncellenir.
4. **Adım:** Ana Sayfa → "Shop" → aksesuar seç → satın al → bakiye düşer.
5. **Adım:** "Avatarım" → aksesuarı giydir → kaydet.
6. **Bitiş:** Ana Sayfa, güncel point ve giydirilmiş avatar ile yansıtılır.

**Yan Akışlar:**
- *Etkinlik:* Ana Sayfa → Etkinlikler → Detay → "Katıl" → katılımcı listesinde görünür.
- *Oyun:* Ana Sayfa → Oyna → skor → "Skoru Gönder" → Leaderboard'da sıra + point.

*(Detaylı ekran listesi: `docs/03_MIMARI_PLAN.md`.)*

## 8. Veri Alanları
| Varlık | Alan | Tip | Zorunlu | Kaynak |
|---|---|---|---|---|
| User | id | UUID | Evet | Supabase Auth |
| User | email | String | Evet | Kullanıcı girişi |
| User | display_name | String (1–50) | Evet | Kullanıcı |
| User | department | String | Hayır | Kullanıcı |
| User | points_balance | Int (≥0) | Evet | Sistem (hesaplanır) |
| Avatar | user_id | UUID | Evet | User (FK) |
| Avatar | base_id | String | Evet | Sistem (varsayılan) |
| Avatar | equipped_items | JSON | Hayır | Kullanıcı seçimi |
| ShopItem | id | UUID | Evet | Seed |
| ShopItem | name | String | Evet | Seed |
| ShopItem | category | Enum(hat/glasses/outfit/bg) | Evet | Seed |
| ShopItem | price | Int (≥0) | Evet | Seed |
| ShopItem | rarity | Enum(common/rare/epic) | Hayır | Seed |
| UserItem | user_id / item_id | UUID | Evet | Satın alma |
| JiraTask | key | String (örn. GIB-12) | Evet | Mock seed |
| JiraTask | title | String | Evet | Mock seed |
| JiraTask | story_points | Int (0–21) | Evet | Mock seed |
| JiraTask | status | Enum(todo/in_progress/done) | Evet | Kullanıcı |
| JiraTask | points_awarded | Bool | Evet | Sistem (idempotency guard) |
| Event | title | String (1–80) | Evet | Kullanıcı |
| Event | starts_at | Timestamp | Evet | Kullanıcı |
| Event | capacity | Int (1–500) | Evet | Kullanıcı |
| Event | created_by | UUID | Evet | User (FK) |
| GameScore | user_id | UUID | Evet | User (FK) |
| GameScore | score | Int (≥0) | Evet | Oyun |
| PointTx | amount | Int | Evet | Sistem |
| PointTx | source | Enum(jira/game/event/purchase) | Evet | Sistem |
