# Claude Design — Tam Ekran Tasarım Promptu

> Aşağıdaki bloğun tamamını kopyalayıp Claude Design'a ver. Tüm ekranları, durumları ve tasarım sistemini içerir.

---

```
Design a complete, modern mobile app UI for an Android (Material 3 Expressive) application
called "GİB İnteraktif" — a GAMIFIED CORPORATE SOCIAL app for company employees. Generate
ALL screens listed below, in BOTH light and dark mode, as high-fidelity mobile mockups
(390×844, rounded device frame). The tone is PLAYFUL but PROFESSIONAL: it makes work feel
like a game, but it is still a corporate product. Think Duolingo's joy + Linear's polish.

CORE CONCEPT / EMOTIONAL GOAL:
Employees EARN points by finishing work (Jira tasks), joining events, and playing a mini-game,
then SPEND points to customize their AVATAR with accessories. Every screen should reinforce the
loop: EARN → SPEND → SHOW OFF → COMPETE. The hero emotion is the satisfying "+points" reward moment.

═══════════════════ DESIGN SYSTEM ═══════════════════
COLOR (light mode):
- Primary: #5B5BD6 (vivid indigo) — main actions, nav highlight
- Primary container: #E6E6FB
- Accent / POINTS: #FFB020 (warm gold) — always used for point values, coins, rewards
- Success: #1FB873 (green) — "Done", earned, available
- Danger: #E5484D (red) — errors, insufficient balance, full capacity
- Surface: #FFFFFF, Surface-2 (cards): #F6F6FB, Background: #FAFAFD
- Text primary: #16161D, Text secondary: #6B6B7B, Outline: #E2E2EC
DARK mode: Background #0E0E14, Surface #181820, Surface-2 #22222E, Primary #8E8EF5,
Accent #FFC24D, text #F2F2F7 / #A0A0B0. Keep accent gold and success green vivid in dark.

TYPOGRAPHY (Inter / Plus Jakarta Sans):
- Display 28/700 (screen titles & point balances), Title 20/600, Body 15/400,
  Label 13/500, Caption 11/500. Point values are ALWAYS bold + gold + coin icon.

SHAPE & SPACING:
- Corner radius: cards 20, buttons 16, chips/pills 999 (fully rounded), avatar canvas 28.
- Spacing scale 4/8/12/16/24. Generous padding, airy. Soft shadow (y4, blur16, 8% black).
- Cards have a subtle 1px outline in light, elevated surface in dark.

SIGNATURE COMPONENTS (reuse everywhere):
- POINT BADGE: pill, gold coin icon + bold number, e.g. "🪙 1,240". Top-right of most screens.
- AVATAR VIEW: layered character (base body + equipped hat/glasses/outfit/background),
  circular or rounded-square, with a soft glow ring matching rarity.
- BOTTOM NAV (5 tabs): Home, Görevler, Oyna, Etkinlik, Profil — icon + label, indigo active
  pill behind active icon (Material 3 style). Center "Oyna" can be slightly emphasized.
- Reward toast/overlay: a celebratory "+50 🪙" burst with confetti micro-particles.

═══════════════════ SCREENS TO GENERATE ═══════════════════

1) LOGIN
- Brand logo + playful mascot/avatar illustration up top. Tagline: "Çalış. Kazan. Eğlen."
- Email field, primary "Giriş Yap" button (full-width, indigo).
- Secondary big circular "Face ID ile gir" button with fingerprint/face icon.
- Clean, centered, lots of whitespace, subtle gradient background (indigo→transparent).

2) HOME / DASHBOARD (most important — show richest version)
- Greeting "Merhaba, Ahmet 👋" + small avatar thumbnail, POINT BADGE top-right.
- HERO CARD: large current AVATAR (full body) on a colorful gradient/background scene,
  with the point balance "🪙 1,240" big and bold, plus a thin XP/level progress bar ("Seviye 4").
- QUICK ACTION row: 3–4 rounded tiles — "Görevlerim (3 açık)", "Shop", "Oyna", "Etkinlik".
- "Bugün Kazandıkların" mini stat strip: +120 bugün, 2 görev, 1 etkinlik.
- MINI LEADERBOARD card: top 3 colleagues with avatars + scores, "Tümünü gör →".
- Upcoming event card preview. Scrollable feed feel.

3) AVATAR & SHOP (the visual showcase — make it beautiful)
- TOP HALF: big avatar canvas, the character centered, "Kaydet" + share button.
- Category tabs (pills): Şapka, Gözlük, Kıyafet, Arka Plan.
- GRID of item cards: each card shows item thumbnail, name, and either price "🪙 80"
  or "Sahip" (owned) badge or "Giyili" (equipped, with green check). Locked/unaffordable
  items slightly dimmed with the price in gold.
- Tapping an owned item = equip instantly (avatar updates live). Show rarity glow
  (common gray, rare blue, epic purple) on item cards.
- States to show: (a) buying an affordable item → confirm sheet "🪙 80 karşılığında al?"
  → success; (b) INSUFFICIENT BALANCE → red inline message "Yetersiz bakiye, 30 point eksik".

4) JIRA GÖREVLERİM (the hero reward moment)
- Header "Görevlerim" + filter chips: Tümü / Yapılacak / Devam / Tamamlanan.
- TASK CARDS: each shows task key (GIB-12), title, a story-point chip ("5 SP" in gold-tinted
  pill), assignee avatar, and a status segmented control (To Do · Devam · Done) OR a swipe-to-Done.
- Below each task show the reward it will give: "Tamamlayınca +50 🪙".
- KEY SCENARIO — show the moment a task is dragged/marked DONE: a celebratory overlay
  "🎉 +50 🪙 kazandın!" with confetti, the task animating into a green "Tamamlandı" state,
  and the top point badge ticking up.
- Empty state: friendly illustration "Tüm görevlerin bitti! 🎯".

5) OYNA (MINI GAME + LEADERBOARD)
- Game landing: bold game title card with a "Başla" CTA, "Bugünkü en iyi skorun: 1,840",
  and "Skor başına 🪙 kazan" hint.
- IN-GAME screen (one frame): a simple, colorful single-player mini-game (e.g. tap/reflex or
  quick quiz) — score counter top, timer, playful visuals.
- GAME OVER / RESULT: big score, "🪙 +18 kazandın", "Tekrar Oyna" + "Leaderboard'a bak".
- LEADERBOARD screen: ranked list, top 3 on a podium with avatars + crowns, then rows
  (rank, avatar, name, score). Current user's row highlighted/sticky with indigo accent.

6) ETKİNLİKLER (EVENTS)
- List of event cards: cover color/emoji, title ("Cuma Kahve Molası ☕"), date/time,
  location, participant avatars stack ("+8 katılıyor"), capacity bar (e.g. 8/10),
  and a "Katıl" button (turns into "Katıldın ✓" green when joined).
- EVENT DETAIL screen: large header, description, full participant list, big "Katıl" CTA.
- CREATE EVENT screen (form): title, description, location, date & time pickers, capacity
  stepper. Validation states visible (empty title = red error, past date = red error).
- SCENARIO — FULL EVENT: capacity bar full/red, button disabled showing "Kapasite Dolu".

7) PROFİL
- Big avatar + name + department, total points, level, stats (görev sayısı, etkinlik, oyun skoru),
  badges/achievements row, point transaction history list ("+50 Jira • -80 Shop • +18 Oyun"
  with colored signs), settings entry, dark-mode toggle, "Çıkış".

═══════════════════ GLOBAL STATES TO INCLUDE ═══════════════════
For the app, also show these reusable states (as small frames):
- LOADING (skeleton shimmer cards), EMPTY (friendly illustration + 1 line),
  ERROR (red banner "Bir şeyler ters gitti, tekrar dene"), OFFLINE (subtle top bar),
  SUCCESS TOAST (gold "+point" celebration), CONFIRM bottom sheet.

CONSISTENCY RULES:
- Every screen uses the same bottom nav (except Login & full-screen game).
- Point values are ALWAYS gold + coin icon + bold. Primary actions always indigo.
- Use real-feeling Turkish copy throughout. Friendly, encouraging microcopy.
- Provide both LIGHT and DARK versions of each main screen.
- Modern, rounded, joyful, but enterprise-trustworthy. No clutter; strong visual hierarchy.

Deliver each screen as a clean labeled mockup. Start with Home (light+dark), then Avatar/Shop,
then Jira reward moment, then Game+Leaderboard, then Events, then Login & Profile.
```
