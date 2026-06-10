# Ekran Envanteri — UI Kit'ten Türetilmiş (Tek Doğruluk Kaynağı)

> Bu liste, `GİB İnteraktif UI Kit (standalone).html` dosyasının **render edilmiş halinden** birebir
> çıkarılmıştır. Uygulamada yapılacak ekranlar = aşağıdaki ekranlardır. Compose ekranları bu metin,
> durum ve yerleşimleri baz alır. Bottom nav: **Ana Sayfa · Görevler · Oyna · Etkinlik · Profil** (5 sekme).

UI Kit toplam **8 bölüm / 31 kart** içerir (her ana ekranın Açık + Koyu varyantı + durum ekranları).

---

## 0. Alt Navigasyon (global component, 2 varyant)
- **A · Material 3 (pill):** alt barda 5 ikon, aktif sekmenin arkasında indigo pill. *(Önerilen — basit, standart.)*
- **B · Floating + Oyna FAB:** yüzen bar + ortada yükseltilmiş dairesel **"Oyna"** FAB.
- **Karar:** Varyant A ile başla; süre kalırsa B'ye yükselt. Tüm ekranlar aynı barı kullanır (Giriş ve tam-ekran oyun hariç).

## 1. Ana Sayfa / Dashboard  *(Açık + Koyu)*
- Üst: "İyi çalışmalar 👋 / **Merhaba, Ahmet**" + avatar thumb + **PointBadge 🪙 1.240**.
- **Hero kart** (indigo gradient): full avatar · "🪙 **1.240** toplam puanın" · "Seviye 4 · Uzman" · XP barı "760 / 1000 XP".
- **Hızlı eylemler** (3 tile): *Görevlerim* (3 açık) · *Shop* (Yeni 5) · *Oyna* (Skor kazan).
- **"Bugün Kazandıkların"** şeridi: +120 puan · 2 görev · 1 etkinlik.
- **"Sıralama · Bu Hafta"** mini leaderboard: Elif Y. 2.150 · Ahmet K. (sen) 1.240 · Mert D. 1.180 + "Tümünü gör →".

## 2. Avatar & Shop  *(Açık + Koyu + Satın alma onayı + Yetersiz bakiye)*
- **Avatar canvas** (sıcak gradient): karakter (silindir şapka + gözlük + takım) + **"Kaydet"** + paylaş ikonu.
- **Kategori pill'leri:** Şapka · Gözlük · Kıyafet · Arka Plan.
- **Item grid** — kart: rarity şeridi (**NADIR** / **EFSANE**), durum rozeti (**Giyili ✓** / **Sahip** / fiyat 🪙).
  Örnek itemlar: Silindir (Nadir·Giyili) · Kep (Sahip) · Taç (Efsane·240) · Parti (80) · Kovboy (Nadir·120) · Mezuniyet (Efsane·1500).
- **Satın alma onayı** (bottom sheet): "Kovboy · Nadir aksesuar / Bu aksesuarı al? **🪙 120** / Vazgeç · **Satın Al**".
- **Yetersiz bakiye:** kırmızı banner "⚠️ Yetersiz bakiye · 260 puan eksik".

## 3. Jira Görevlerim  *(🎉 Ödül anı + Liste Açık + Liste Koyu + Boş durum)*
- Header "**Görevlerim**" + PointBadge + filtre çipleri: **Tümü · Yapılacak · Devam · Tamamlanan**.
- **Görev kartı:** `GIB-12` anahtarı · `5 SP` çipi · başlık · segment kontrol **To Do · Devam · Done** · "Tamamlayınca **+50**".
  - Seed görevler: GIB-12 (5SP, "Ödeme servisini yeni API'ye taşı", +50) · GIB-19 (3SP, "Onboarding ekranı QA testleri", +30) · GIB-07 (8SP, "Dashboard grafik performansı", +80) · GIB-21 (2SP, "Push bildirim altyapısı", +20).
- **Ödül anı (canlı döngü):** görev "Done" → konfeti + bakiye **1.240 → 1.290** animasyonu (HERO an).
- **Boş durum:** 🎯 "Tüm görevlerin bitti!" + "Bugün 4 görev tamamladın ve **+180 puan** kazandın" + **"Oyna & puan kazan"**.

## 4. Oyna · Mini Oyun & Sıralama  *(Landing Açık/Koyu + Oyun içi + Sonuç + Sıralama Açık/Koyu)*
- **Oyun:** **"Puan Yağmuru 🪙"** — *Düşen coinleri yakala, 30 saniyede en yüksek skoru yap.*
- **Landing:** "GÜNÜN OYUNU" kartı · "Bugünkü en iyi skorun **1.840**" · "Skor başına 🪙 kazan · 💡 1 puan" · **"Başla"**.
- **Oyun içi (tam ekran):** üstte **SKOR 1.420** · "**x3 Combo!**" · düşen coin'ler · **12 sn** timer halkası.
- **Sonuç · +puan:** kupa + konfeti · skor · "🪙 **+N** kazandın" · "Tekrar Oyna" / "Leaderboard'a bak".
- **Sıralama:** podyum top 3 (Elif Y. 1.840 · Mert D. 1.720 · Burak S. 1.690) + **Hafta / Tüm** toggle + sıralı liste (kullanıcı satırı vurgulu).

## 5. Etkinlikler  *(Liste Açık/Koyu + Detay + Oluştur·validasyon + Kapasite dolu)*
- Header "**Etkinlikler**" + **"+ Oluştur"**.
- **Etkinlik kartı:** kapak (emoji + renk) · saat çipi · başlık · 📍konum · katılımcı avatar yığını + "**N/M katılıyor**" · kapasite barı.
  - Seed: *Cuma Kahve Molası* (Teras Kafe 4.kat · 8/12 · Cum 15:00) · *Ofis Oyun Turnuvası* (Sosyal Alan · 5/16 · Çar 18:00) · *Sabah Koşusu* (Maslak Parkı · 6/10 · Pzt 07:30).
- **Detay:** büyük kapak · başlık · tarih/konum · açıklama · "**Katılımcılar 8/12 katılıyor**" avatar grid · "+15 puan" · büyük **"Katıl"**.
- **Oluştur · validasyon:** form — *Başlık* (hata: "Başlık boş olamaz") · *Açıklama* · *Konum* · *Tarih* (hata: "Geçmiş tarih") · *Saat* · *Kapasite* stepper ("Maksimum kişi − 12 +") · **"Etkinliği Yayınla"**.
- **Kapasite dolu:** *Pizza Cuması* 10/10 · **"Kapasite Dolu"** (disabled) + banner "Bu etkinlik dolu — bekleme listesine yazıl".

## 6. Giriş  *(Açık + Koyu)*
- **GİB İnteraktif** logosu + maskot avatar + tagline "**Çalış. Kazan. Eğlen.**".
- "**Kurumsal e-posta**" alanı (ör. ahmet.kaya@gib.com.tr) · **"Giriş Yap"** · "veya" · **"Face ID ile gir"** (dairesel).

## 7. Profil  *(Açık + Koyu)*
- Avatar + "**Ahmet Kaya**" + "Mühendislik · Backend" + PointBadge 1.240 + **Seviye 4** çipi.
- **İstatistik:** 48 görev ✅ · 12 etkinlik 🎉 · 1.840 oyun skoru 🎮.
- **Rozetler:** Şampiyon 🏆 · 7 gün 🔥 · Hızlı ⚡ · Nişancı 🎯 · kilitli 🔒 + "Tümü →".
- **Puan Geçmişi:** "GIB-12 tamamlandı **+50**" (Jira · 2 saat önce) · "Silindir şapka **−80**" (Shop · dün) · "Puan Yağmuru **+18**" (Oyun · dün) + "Tümü →".

## 8. Global Durumlar (her ekranda yeniden kullanılır)
- **Yükleme · skeleton:** shimmer placeholder kartlar.
- **Başarı toast'ı:** altın toast "**+50 puan kazandın!** GIB-12 tamamlandı · bakiye 1.290".
- **Hata:** kırmızı banner "Bir şeyler ters gitti, tekrar dene · **Yinele**".
- **Çevrimdışı:** üst bar "Çevrimdışısın · değişiklikler kaydedilecek".

---

## Uygulama → UI Kit eşlemesi (feature modülleri)
| Bottom nav sekmesi | Ekran(lar) | Feature modülü |
|---|---|---|
| Ana Sayfa | §1 Dashboard | `:feature:home` |
| Görevler | §3 Jira Görevlerim | `:feature:tasks` *(referans)* |
| Oyna | §4 Oyun + Sıralama | `:feature:game` |
| Etkinlik | §5 Etkinlikler | `:feature:events` |
| Profil | §7 Profil | `:feature:profile` |
| (sekme dışı) | §2 Avatar & Shop | `:feature:shop` |
| (sekme dışı) | §6 Giriş | `:feature:auth` |
| (ortak) | §0 nav · §8 durumlar | `:core:designsystem` |
