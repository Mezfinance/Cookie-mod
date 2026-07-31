# Cookie Mod — Custom Mechanics Spec

A full specification of the mod's custom mechanics, reconstructed from the
playthrough transcript and video. Timestamps in `(m:ss)` cite the moment in the
source video the behaviour is shown or stated.

> **Source & confidence.** Everything here is observed from one playthrough, not
> from mod source. Values that are stated on-screen (attack numbers, armor
> bonuses, effects) are quoted directly. Values that are *inferred* from
> behaviour are marked **[inferred]**. Where the video is ambiguous it's marked
> **[unclear]**.

---

## 1. Core loop

The player advances through a rank ladder from **Cookie Noob** to **Cookie God**
over 100 days by three intertwined activities `(0:08)`:

1. **Build an army** of cookie soldiers (recruit → grow → replace losses).
2. **Build settlements** — outpost → village → kingdom/castle.
3. **Defeat the three bosses** — Cake Golem → Waffle Mage → Milkman.

Each of these feeds the **progression/rank system** (§2). The economy that
powers it is **cookies** (§4), harvested from the cookie biome and spent to
recruit soldiers.

---

## 2. Progression / rank system

- The player has a **level** that rises through named ranks. At the start the
  narrator states there are **10 levels above Noob, the last being Cookie God**
  `(0:31)` — i.e. ~11 ranks total.
- **Rank-ups are milestone-driven, not XP-driven.** Stated triggers `(2:54)`:
  growing the army, and building villages, armories, and a kingdom. Boss kills
  also grant levels `(15:39)`.

### Observed rank ladder (in order)

| # | rank | trigger shown | ref |
|---|---|---|---|
| 1 | Cookie Noob | start | `(0:23)` |
| 2 | Cookie Dud | 35 soldiers **and** first outpost built | `(14:28)` |
| — | *(level 3)* | defeating the Cake Golem — "we are level three now… seven more to go" (name not shown) | `(15:39)` |
| 3? | Cookie Soldier | ~100+ soldiers | `(20:17)` |
| 4? | Cookie Mid | ~157 soldiers | `(25:12)` |
| 5? | Cookie Peasant | defeating the Waffle Mage (2nd boss) | `(26:37)` |
| 6? | Cookie Queen | castle + armory built | `(40:39)` |
| 7? | Cookie Commander | immediately after (a second rank-up) | `(40:47)` |
| 8? | Cookie King | ~1,000 soldiers | `(43:29)` |
| 9 | Cookie God | 1,000+ soldiers **and** all three bosses dead | `(43:39)` |

> **[unclear]** The video frames it as ~10–11 numbered levels but only names 9
> ranks; the exact level-number ↔ name mapping and 1–2 intermediate ranks are
> never shown cleanly. "We are level three now, seven more to go" `(15:39)`
> confirms a 10-step numeric track underneath the names.
>
> Rank thresholds appear to be a **combination** of (army size, buildings, boss
> kills) rather than a single stat. The stated hard requirement for the first
> rank-up is **35 soldiers + an outpost** `(13:16, 14:28)`.

---

## 3. Cookie soldiers (recruiting / taming / command)

The army is the central mechanic. Soldiers are humanoid, non-villager allies
that follow and protect the player `(1:04)`.

### 3.1 Recruitment ("taming")

- **Cookie dudes** spawn wild in the world. **Feed one a cookie** (right-click /
  punch-with-cookie) → it converts into a **Cookie Soldier**, shown by a smile
  `(2:31–2:38)`. Cookies are the recruitment currency (§4).
- **Gingerbread men**: a separate recruitable. Feeding gingerbread men converts
  them into **Cookie Knights** `(6:18)`. Dead gingerbread-man bodies can be
  collected and **spawned/claimed** as soldiers in bulk — "15 dead bodies …
  gives me 32 gingerbread men, spawn them all in" `(24:14)`.
- Freshly spawned gingerbread soldiers start **unclaimed** and scatter until
  claimed to the player `(24:14)`.

### 3.2 Commands

- **Right-click a soldier** toggles its order between **Go** and **Stay**
  `(2:46)`.
- A **mod command** lets the player order **all** soldiers at once, instead of
  right-clicking each — added specifically because doing it per-unit was tedious
  `(19:53)`.

### 3.3 Friendly fire

- Soldiers can be **damaged and killed by the player**. Hitting one turns bad
  fast — "do not punch one of your soldiers" `(6:32)`; the Milky Sword's
  right-click AoE wiped ~10 at once `(26:21)` and later the whole 1,000-strong
  army in one click `(44:32)`.

### 3.4 Unit types & gear

| unit | source | notes |
|---|---|---|
| Cookie Soldier | cookie-fed cookie dude | basic melee; carries "plastic knife / shovel / fork" weapons `(3:02, 4:08)` |
| Cookie Knight | cookie-fed gingerbread man | stronger; some seen with diamond armor `(7:43)` |

### 3.5 Losses & attrition

- Soldiers die permanently to lava `(5:32)`, enemies (waffle guys especially),
  bosses, and friendly fire. The player continually **re-recruits to replace
  losses** — army counts swing from 100s down to single digits and back
  repeatedly.

### 3.6 Gingerbread Furnace (end-game auto-spawner)

- A **Gingerbread Furnace** (looted from the Milkman's house) **continuously
  spawns soldiers for free**, no input required — "it just spawns soldiers …
  keeps going … a cookie Army Factory" `(43:12)`. This is what lets the count
  reach 1,000+ and pushes the final ranks.

---

## 4. Cookie economy

- **Cookie Block** → mine → **craft into cookies** ("unlimited cookies")
  `(1:44, 7:28)`. Cookies are consumed to recruit soldiers (§3.1).
- Cookies (from cookie blocks) can also be crafted back into a **cookie block**
  for building `(14:03)`.
- **Cookie blocks grant status effects** when mined/eaten — speed and jump boost
  observed `(16:08)`. **[inferred]** eating cookies feeds/heals and may grant the
  same effects.
- Ordinary food (mutton, beef, potatoes) is scarce; the player farms potatoes
  and raids villages for food `(9:35, 13:40)`.

---

## 5. Consumables & effects

### 5.1 Gummy bears (dropped by polar bears)

Polar bears drop **colored gummy bears**; **eating a color grants an effect**.
There are several polar-bear/gummy variants:

| gummy / source | effect on eat | ref |
|---|---|---|
| Apple Gum (radioactive/"Chernobyl" polar bear) | "stat boost" → jump boost | `(2:06, 2:14)` |
| Orange gummy bear | none observed | `(6:03)` |
| Blue polar bear gummy | Speed | `(6:11)` |
| Peach gummy bear | Levitation | `(7:12)` |
| Raspberry gummy bear | Regeneration | `(7:20)` |
| Minty + Grape gummy | "go well together" — **[unclear]** combo effect | `(6:48)` |

- Gummy bears are eaten as **combat power-ups** before/inside boss fights
  `(41:38, 23:37)`.
- Gummy bears are also **crafting ingredients for jelly armor** (§7).

### 5.2 Milk (fluid)

- **Milk is a placeable fluid** found in world pools (looks like lava). Drinking
  / touching grants **Saturation + Regeneration + Nausea** `(5:48)`.
- **Bucketable** as a **Cookie Milk Bucket** `(5:55)`; a **Healing Milk Bucket**
  variant also exists (advancement seen ~`6:00`).
- Milk is **thick/opaque** — you can't see through it — and **climbable/swimmable
  upward**, used as a fast vertical elevator up mountains `(16:50, 17:04, 29:01)`.
- **[inferred]** Milk placed from the Cookie Milk Bucket becomes a **Milk Block**
  source (see `keyframes/block__milk_block.png`).

### 5.3 Holy Cookie (totem)

- Functions as a **Totem of Undying**: held in the **off-hand**, it **prevents
  death** and is consumed on save — "holy cookies are basically totems … got
  saved by the holy cookie" `(24:00, 28:04)`. Found in chests (stacks of 5).

### 5.4 Super Golden Apple

- Strong healing consumable found in boss/structure chests `(10:34)`.

---

## 6. Weapons

| item | effect | ref |
|---|---|---|
| **Lollipop** | melee weapon, **5 attack damage, 1.6 attack speed**; dropped/held by waffle guys and found on candy flora | `(6:25, 6:33 tooltip)` |
| **Milky Sword** | **right-click to drain/"suck the life out of" mobs** — an instant-kill / AoE life-drain. Extremely strong (one-shots the Milkman). **AoE hits allies too** — one click killed the entire 1,000 soldier army | `(42:55, 44:32)` |
| Insta-health (splash) | found in tower chests | `(8:22)` |

---

## 7. Jelly / Gummy armor set

Armor **crafted from gummy bears**, granting knockback resistance. Full set
`(26:45, 37:20, 39:17–39:42)`:

| piece | crafted from | stated stats | ref frame |
|---|---|---|---|
| **Raspberry-Peach Jelly Helmet** | 3 peach + 2 raspberry gummy → yields 11 | +20 Knockback Resistance, +4 Armor | `item__raspberry_peach_jelly_helmet.png` |
| **Minty Jelly Chestplate** | minty gummy bears → yields 11 | +20 Knockback Resistance, +8 Armor | `item__minty_jelly_chestplate.png` |
| **Grape Jelly Leggings** | grape gummy bears (crafted 3) | +20 Knockback Resistance, +6 Armor | *(inventory only)* |
| **(Apple) Gum Boots** | apple gum (crafted 8) | **[inferred]** +20 KB Resist, +armor | — |

> Cosmetic note: the helmet "covers up the forehead / makes you look bald"
> `(37:20)`. The recipes are gummy-count based, so gummy farming (killing polar
> bears) gates the armor.

---

## 8. Bosses

Three bosses, escalating strength: **Cake Golem → Waffle Mage → Milkman**
`(0:16)`. Each has a boss health bar and unique behaviour.

### 8.1 Difficulty scaling (global)

- **Bosses scale up over days**: their **health and damage increase** as days
  pass, while **soldiers get weaker** over time — so the player must **keep
  recruiting more soldiers** to keep pace `(24:40)`. This is the core reason the
  army must constantly grow.

### 8.2 Cake Golem (boss 1)

- Roams the world; associated with **waffle-guy spawners** nearby `(10:58)`.
- **Huge, high damage**, but low effective threat to a large army — the swarm
  killed it fast `(14:51–15:31)`. Defeat granted a level `(15:39)`.

### 8.3 Waffle Mage (boss 2)

- **"Made of cakes,"** wither-like; lives at the **top of the Waffle/Cookie
  Tower** and **spawns when its tower defenders are cleared** `(9:03, 25:20)`.
- **Attacks:** spits/shoots projectiles, shoots a beam, and "shoots its tongue"
  `(25:27–25:51)`.
- Low damage, **hard to hit — crit (jump) damage lands best** `(36:27)`. Killable
  solo late-game.

### 8.4 Milkman (boss 3, strongest)

- Lives **hidden inside a mountain in the Milkman's House** `(41:30)`. Giant.
- **Attacks:** **flings the player away** and **shoots fireballs** `(42:00)`.
- **Milkman Aura mechanic:** the Milkman has an **"aura" point meter** (on-screen
  "milkman aura 1300 pts", "-400 pts"). **Killing cows reduces the Milkman's
  aura**, weakening him before the fight — "kill every cow … you single-handedly
  drop the milkman's aura" `(33:19)`. A pre-fight prep/attrition system unique to
  this boss.
- Drops / guards: gold, fortune-3 golden apples, diamonds, chests of gingerbread
  men, the **Milky Sword**, and the **Gingerbread Furnace** `(42:48–43:12)`.

---

## 9. Enemies & spawners

### 9.1 Waffle guys ("waffle dudes")

- Common **strong** enemy; the main soldier-killer. Carry lollipops `(4:00)`.
- Spawn from **Waffle Guy Spawners** found in towers, houses, and jails
  `(8:31, 11:06)`. Spawn **fast**; destroying the spawner/source stops them
  `(8:22, 15:31)`.
- Can be **jailed/caged** in structures; freeing one aggros it `(8:31)`.

### 9.2 Chocolate bunnies

- **Passive/harmless**, spawn from **chocolate-bunny spawners**, **drop chocolate
  bars** `(6:48, 8:46)`. Infest the world in large numbers `(29:26)`.
- **Take fall damage** (enabled) — used for a drop-farm `(35:30)`.

### 9.3 Spawner interaction

- Spawners can be **lit up (torches) to disable** `(27:48)`, **destroyed**, or
  **capped with a block** to control spawn positioning `(34:37)`, and are the
  basis of farms (§10).

---

## 10. Farms

- **Waffle / XP farm** — built from the tower's dense waffle-guy spawners
  `(26:06)`.
- **Chocolate-bar farm** — a chocolate-bunny drop/kill farm (fall damage or
  slaughter) yielding chocolate bars → chocolate `(29:34, 35:30)`.
- **Cookie Army Factory** — the Gingerbread Furnace as an infinite soldier farm
  (§3.6).

---

## 11. Blocks, crafting & edibility

**Everything in the cookie world is edible** — most custom blocks convert to a
food item `(8:13)`.

| block / item | how obtained | converts to | ref |
|---|---|---|---|
| Cookie Block | mine cookie biome | Cookies (and back to block) | `(1:44, 14:03)` |
| Frosted Cookie Block | cookie biome (looks like sand) | — | `(16:17)` |
| Waffle Block | **raiding towers only — not craftable** | — | `(35:55)` |
| Chocolate Bricks | raiding towers | Chocolate Bars → Chocolate (blocks) | `(8:13)` |
| Chocolate Bar | chocolate bunnies / chocolate bricks | Chocolate Block | `(6:48, 35:46)` |
| Candy Cane | mine candy-cane flora | Candy (food) | `(16:33)` |
| Hard Candy Blocks (raspberry / aniseed / grape / minty) | candy biome | jelly armor ingredients / building | `(2:22, 39:17)` |
| Cookie (item) | craft from cookie block | recruit soldiers / eat | `(1:52)` |

- **Waffle Blocks are uncraftable** and only obtainable by **demolishing the
  Waffle Tower** — a deliberate scarcity gate on the best building block
  `(35:55)`. Chocolate, by contrast, is farmable to unlimited via bunnies.
- The full custom **block families** (each flavor has block + stairs + slab +
  fence + door + wall) are visible in `keyframes/_JEI_INDEX.png`.

---

## 12. Structures

| structure | contains / role | ref |
|---|---|---|
| **Waffle / Cookie Tower** | Waffle Mage boss at top; waffle-guy + chocolate-bunny spawners; chests (diamonds, gummy bears, lollipops, insta-health); a jailed waffle guy. **Only source of Waffle Blocks** (mine it down). | `(7:57–9:03, 35:55)` |
| **Milkman's House** | Milkman boss, hidden in a mountain; gold, fortune-3 golden apples, diamonds, chests of gingerbread men, the **Milky Sword**, the **Gingerbread Furnace**, spawners. | `(36:43, 42:48)` |
| **Villages** | villager settlements — raidable for food/beds; also the template for the player's own builds. | `(0:48, 9:35)` |
| **Player builds** (Outpost → Village → Castle/Kingdom) | progression requirements; built from cookie/chocolate/waffle/candy blocks; include watchtowers, armories, throne, armor-stand armory. | `(11:14, 20:34, 29:58)` |

---

## 13. Open questions / unconfirmed

- Exact **level-number ↔ rank-name** mapping and the 1–2 unnamed intermediate
  ranks (§2).
- Precise **rank-up thresholds** beyond the first (35 soldiers + outpost).
- Effects of **orange**, **minty**, and **grape** gummies (only some colors
  showed a clear effect) (§5.1).
- Whether **milk** placed from the bucket is a true block or a flowing fluid
  source, and its exact effect radius (§5.2).
- **Gum Boots** stats and any set bonus for full jelly armor (§7).
- The **numeric formula** behind boss day-scaling and the Milkman aura
  (points per cow, starting value) (§8).
