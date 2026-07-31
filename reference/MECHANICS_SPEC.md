# Cookie Mod — Custom Mechanics Spec

A full specification of the mod's custom mechanics, reconstructed from the
playthrough transcript and video. Timestamps in `(m:ss)` cite the moment in the
source video the behaviour is shown or stated.

> **How to read the confidence tags**
> - `(m:ss)` — **observed**: shown or stated on screen; quoted directly.
> - **[EST]** — **estimate/design-fill**: not shown in the video. A best guess
>   tuned to be internally consistent and to reproduce what happens on screen
>   (e.g. the Cake Golem must fall quickly to ~100 soldiers; the Milkman must
>   survive 235). Treat every `[EST]` number as a **starting value to playtest**,
>   not canon.
>
> **Is there a better source?** Yes — the mod's own files (item/loot-table JSON,
> entity attribute configs) would give real HP, durations, and thresholds. This
> appears to be a private/commissioned mod that isn't publicly available, so we
> have only the video + transcript. Every gap below is filled with an `[EST]`
> rather than left blank, so the spec is buildable end-to-end.

---

## 1. Core loop

The player advances through a rank ladder from **Cookie Noob** to **Cookie God**
over 100 days by three intertwined activities `(0:08)`:

1. **Build an army** of cookie soldiers (recruit → grow → replace losses).
2. **Build settlements** — outpost → village → kingdom/castle.
3. **Defeat the three bosses** — Cake Golem → Waffle Mage → Milkman.

Each feeds the **progression/rank system** (§2). The economy that powers it is
**cookies** (§4), harvested from the cookie biome and spent to recruit soldiers.

---

## 2. Progression / rank system

- The player has a **level** that rises through named ranks. The narrator states
  there are **10 levels above Noob, the last being Cookie God** `(0:31)` — ~11
  ranks total. Defeating the Cake Golem put the player at **"level three … seven
  more to go"** `(15:39)`, confirming a 10-step numeric track under the names.
- **Rank-ups are milestone-driven, not XP-driven.** Stated triggers `(2:54)`:
  growing the army, and building villages, armories, and a kingdom. Boss kills
  also grant levels `(15:39)`.

### 2.1 Full rank ladder (observed + estimated)

11 tiers to match the "10 levels + Noob" line. 9 ranks are named on screen; the
video shows **one unnamed rank-up at "level 3"** and the count implies **one more
unnamed tier** — those two are marked `[EST name]` (suggested labels only). The
**army-size column is `[EST]`** because the real trigger is a milestone *combo*,
not a pure count (the player is often below a "threshold" after losses).

| tier | rank | trigger (observed) | est. army size | ref |
|---|---|---|---|---|
| 1 | Cookie Noob | start | 0 | `(0:23)` |
| 2 | Cookie Dud | first Outpost built | **35** (stated) | `(13:16, 14:28)` |
| 3 | *Cookie Cadet* `[EST name]` | **Cake Golem** killed | 60 `[EST]` | `(15:39)` |
| 4 | Cookie Soldier | — | ~100 | `(20:17)` |
| 5 | Cookie Mid | Village/settlement built | ~150 (obs. 157) | `(25:12)` |
| 6 | Cookie Peasant | **Waffle Mage** killed | boss-gated (obs. ~110) | `(26:37)` |
| 7 | *Cookie Baron* `[EST name]` | 2nd settlement / 300 army `[EST]` | 300 `[EST]` | — |
| 8 | Cookie Queen | Castle **+** Armory built | 400 `[EST]` | `(40:39)` |
| 9 | Cookie Commander | (second rank-up, same day) | 500 `[EST]` | `(40:47)` |
| 10 | Cookie King | ~1,000 army | ~1,000 | `(43:29)` |
| 11 | Cookie God | **all 3 bosses dead** + Gingerbread Furnace + 1,000+ army | 1,000+ | `(43:39)` |

> `[EST]` The tiers 3 and 7 names (*Cadet*, *Baron*) are invented placeholders —
> the video never names them. Everything in the "est. army size" column is a
> tuning guess. A clean rule that fits the footage: **rank up when you hit the
> tier's army size OR complete its milestone (building / boss), whichever the
> designer prefers to gate on.**

---

## 3. Cookie soldiers (recruiting / taming / command)

The army is the central mechanic. Soldiers are humanoid, non-villager allies
that follow and protect the player `(1:04)`.

### 3.1 Recruitment ("taming")

- **Cookie dudes** spawn wild. **Feed one a cookie** → it converts into a
  **Cookie Soldier**, shown by a smile `(2:31–2:38)`. **[EST] cost: 1 cookie per
  soldier.**
- **Gingerbread men**: feeding them makes **Cookie Knights** `(6:18)`. Dead
  gingerbread bodies are collected and **spawned/claimed** in bulk — "15 bodies →
  32 gingerbread men, spawn them all in" `(24:14)`. **[EST]** conversion ≈ 1 body
  → 2 gingerbread men (matches 15→32).
- Freshly spawned gingerbread soldiers start **unclaimed** and scatter until
  claimed `(24:14)`.

### 3.2 Commands

- **Right-click a soldier** toggles **Go / Stay** `(2:46)`.
- A **mod command** orders **all** soldiers at once `(19:53)`. **[EST]** likely a
  keybind or chat command (e.g. `/cookiearmy follow|stay`).

### 3.3 Friendly fire

- Soldiers take **player damage** and can be killed — "do not punch your
  soldiers" `(6:32)`; the Milky Sword AoE wiped ~10 `(26:21)` and later the whole
  1,000 army in one click `(44:32)`. **[EST]** friendly fire is **always on** (no
  team-immunity flag), which is why the Milky Sword is so dangerous.

### 3.4 Unit types & gear

| unit | source | melee dmg `[EST]` | HP `[EST]` | notes |
|---|---|---|---|---|
| Cookie Soldier | cookie-fed cookie dude | 3 | 20 (10 hearts) | "plastic knife/fork" weapon `(3:02)` |
| Cookie Knight | cookie-fed gingerbread man | 5 | 30 | stronger; some with diamond armor `(7:43)` |

> `[EST]` values chosen so a ~100-soldier swarm out-DPSes the Cake Golem in
> seconds `(15:23)` but a ~25-soldier remnant loses to scaled waffle guys
> `(23:37)`.

### 3.5 Losses & attrition

- Soldiers die permanently to lava `(5:32)`, enemies (waffle guys), bosses, and
  friendly fire. Counts swing wildly; the player **re-recruits constantly**.
- **[EST]** Soldiers do **not** self-heal or regen; a lost soldier is gone. This,
  plus §8.1 day-scaling, forces perpetual growth.

### 3.6 Gingerbread Furnace (end-game auto-spawner)

- Looted from the Milkman's house; **continuously spawns soldiers for free** —
  "it just spawns soldiers … keeps going … a cookie Army Factory" `(43:12)`.
- **[EST] spawn rate: 1 soldier every 3–5 s**, no fuel/input, capped only by the
  world (the player hits 1,000+ and lags) `(43:29)`.

---

## 4. Cookie economy

- **Cookie Block** → mine → **craft into cookies** `(1:44, 7:28)`. **[EST]** 1
  cookie block → **9 cookies** (3×3 shapeless, mirrors vanilla), i.e. **~9
  recruits per block**.
- Cookies craft back into a **cookie block** for building `(14:03)`.
- **Cookie blocks/cookies grant status effects** — Speed + Jump Boost observed
  when mining `(16:08)`. **[EST]** eating 1 cookie: **+2 hunger, + Speed I & Jump
  Boost I for 15 s.**
- Ordinary food (mutton, beef, potatoes) is scarce; the player potato-farms and
  raids villages `(9:35, 13:40)`.

---

## 5. Consumables & effects

### 5.1 Gummy bears (dropped by polar bears)

Polar bears drop **colored gummy bears**; **eating a color grants an effect**.
Observed effects are cited; unshown ones are `[EST]` (durations all `[EST]`):

| gummy / source | effect | duration | ref |
|---|---|---|---|
| Apple Gum (radioactive/"Chernobyl" bear) | Jump Boost II | 60 s | `(2:06, 2:14)` |
| Blue polar bear gummy | Speed II | 60 s | `(6:11)` |
| Peach gummy | Levitation I | 8 s `[EST]` (short, observed brief) | `(7:12)` |
| Raspberry gummy | Regeneration II | 30 s | `(7:20)` |
| Orange gummy | **no buff** (observed nothing) → `[EST]` treat as a filler drop, or minor **Saturation** | — | `(6:03)` |
| Minty gummy | `[EST]` **Resistance II** (defensive) | 30 s | `(6:48)` |
| Grape gummy | `[EST]` **Strength II** (offensive) | 30 s | `(6:48)` |

- **[EST]** Minty + Grape "go so well together" `(6:48)` = the intended
  **boss-fight combo**: Resistance (survive) + Strength (deal damage). Eaten as
  power-ups before fights `(41:38)`.
- Gummy bears are also **jelly-armor ingredients** (§7).

### 5.2 Milk (fluid)

- A **placeable fluid** in world pools (looks like lava). Contact grants
  **Saturation + Regeneration + Nausea** `(5:48)`.
- **Bucketable** as **Cookie Milk Bucket** `(5:55)`; a **Healing Milk Bucket**
  variant exists `(~6:00)`. **[EST]** Healing Milk Bucket = a drinkable that gives
  Regeneration II / Instant Health without the Nausea.
- **Thick/opaque** (can't see through) and **climbable upward** — a fast vertical
  elevator `(16:50, 17:04, 29:01)`.
- **[EST] Milk Block behaviour:** placed source flows like water (7 flow levels),
  full-block opaque white, **upward-swimmable**; applies **Saturation I +
  Regeneration I every second while touching**, adds **Nausea while fully
  submerged**. Effect radius = contact only.

### 5.3 Holy Cookie (totem)

- A **Totem of Undying**: held **off-hand**, prevents death, consumed on save —
  "basically totems … saved by the holy cookie" `(24:00, 28:04)`. Found in chests
  in **stacks of ~5**. **[EST]** identical to vanilla totem (pops to ~1 heart +
  Regen II/Absorption/Fire-Res on trigger).

### 5.4 Super Golden Apple

- Strong healing consumable in boss/structure chests `(10:34)`. **[EST]** =
  vanilla Enchanted Golden Apple (Absorption IV, Regen II, Fire-Res, Resistance).

---

## 6. Weapons

| item | effect | ref |
|---|---|---|
| **Lollipop** | melee, **5 attack damage, 1.6 attack speed** | `(6:25, 6:33 tooltip)` |
| **Milky Sword** | **right-click to drain/"suck the life out of" mobs** — instant-kill **AoE** life-drain; **hits allies too** (one click killed the 1,000-soldier army). **[EST]** radius ~8–10 blocks, no cooldown, ignores armor | `(42:55, 44:32)` |
| Insta-health (splash potion) | tower-chest loot | `(8:22)` |

---

## 7. Jelly / Gummy armor set

Armor **crafted from gummy bears**, granting knockback resistance `(26:45,
39:17–39:42)`:

| piece | crafted from | stated stats | ref frame |
|---|---|---|---|
| **Raspberry-Peach Jelly Helmet** | 3 peach + 2 raspberry → yields 11 | +20 Knockback Resistance, **+4 Armor** | `item__raspberry_peach_jelly_helmet.png` |
| **Minty Jelly Chestplate** | minty gummies → yields 11 | +20 Knockback Resistance, **+8 Armor** | `item__minty_jelly_chestplate.png` |
| **Grape Jelly Leggings** | grape gummies (crafted 3) | +20 Knockback Resistance, **+6 Armor** | *(inventory only)* |
| **(Apple) Gum Boots** | apple gum (crafted 8) | `[EST]` +20 Knockback Resistance, **+3 Armor** | — |

- **[EST] Full-set totals:** **21 Armor** (≈ full diamond) and **+80 Knockback
  Resistance → effectively knockback-immune** (matters vs the Milkman's fling
  `(42:00)`).
- **[EST] Set bonus** (all four worn): permanent **Regeneration I** *or* **no fall
  damage** ("jelly bounce") — a soft late-game reward, tune to taste.

---

## 8. Bosses

Three bosses, escalating strength: **Cake Golem → Waffle Mage → Milkman**
`(0:16)`. Each has a boss health bar and unique behaviour. All base stats below
are `[EST]`, anchored to the one hard number the game gives us: the intro card
lists the **Milkman at 9999 HP / 9999 ATK / 9999 DEF** `(0:21)` (ATK/DEF read as
flavor — the army still kills him).

| boss | base HP `[EST]` | attack `[EST]` | anchor |
|---|---|---|---|
| Cake Golem | 1,500 | 8 melee, slow | dies in seconds to ~100 soldiers `(15:23)` |
| Waffle Mage | 4,000 | 6 projectile / 10 beam, ~2 melee | low damage, hard to hit, **crit-weak** `(36:27)` |
| Milkman | **9,999** (card) | 15 melee + 12 fireball + fling | survives a 235-soldier swarm briefly `(41:22)` |

### 8.1 Difficulty scaling (global) `[EST]`

Observed: bosses gain **health and damage** over days while **soldiers get
weaker**, so you must keep recruiting `(24:40)`. A formula that fits:

```
BossHP(day)     = BaseHP     × 1.03^(day − dayBossAvailable)   # +3%/day, compounding
BossDamage(day) = BaseDamage × 1.02^(day − dayBossAvailable)   # +2%/day
SoldierPower(day) = 1.00 × 0.995^day                           # soldiers lose ~0.5%/day
```

- Net effect: by ~day 40 a boss is **~2–3× base**, and each soldier is **~80%**
  as effective — so the army must grow several-fold to keep pace (matches the
  100 → 1,000 arc). Tune the three rates to taste; keep `BossHP` growth the
  steepest.

### 8.2 Cake Golem (boss 1)

- Roams; spawns near **waffle-guy spawners** `(10:58)`. Huge, high per-hit damage
  but low threat to a swarm — killed fast `(14:51–15:31)`. Grants a level
  `(15:39)`. **[EST]** telegraphed slow ground-pound (AoE ~4 blocks).

### 8.3 Waffle Mage (boss 2)

- **"Made of cakes,"** wither-like; sits at the **top of the Waffle/Cookie
  Tower** and **spawns once the tower's defenders are cleared** `(9:03, 25:20)`.
- Attacks: projectile spit, a beam, and a "tongue" lash `(25:27–25:51)`.
- Low damage, hard to melee — **crit/jump hits land best** `(36:27)`. **[EST]**
  hovers/floats; take ~1.5× damage from critical hits.

### 8.4 Milkman (boss 3, strongest)

- Hidden **inside a mountain in the Milkman's House** `(41:30)`. Giant.
- Attacks: **flings the player** and **shoots fireballs** `(42:00)`.
- **Milkman Aura mechanic** (on-screen "milkman aura … 1300 pts", "-400 pts"):
  a point meter that **buffs the Milkman**; **killing cows lowers it** to weaken
  him pre-fight — "kill every cow … you single-handedly drop the milkman's aura"
  `(33:19)`.
  - **[EST] formula:** Aura starts at **5,000 pts**; it acts as a multiplier
    `MilkmanHP&Damage = base × (1 + Aura/5000)` → **2× at full aura, 1× at 0**.
    Each cow killed: **−100 pts** (the on-screen "−400" = a ~4-cow batch popup).
    Grinding cows to 0 makes him beatable. Aura does **not** regenerate `[EST]`.
- Guards/drops: gold, fortune-3 golden apples, diamonds, chests of gingerbread
  men, the **Milky Sword**, and the **Gingerbread Furnace** `(42:48–43:12)`.

---

## 9. Enemies & spawners

### 9.1 Waffle guys ("waffle dudes")

- Common **strong** enemy and main soldier-killer; carry lollipops `(4:00)`.
  **[EST]** HP 24, damage 6 (scales with §8.1 alongside bosses — "getting
  stronger" `(23:30)`).
- Spawn **fast** from **Waffle Guy Spawners** in towers/houses/jails
  `(8:31, 11:06)`; destroying the source stops them `(15:31)`. **[EST]** spawner
  ≈ every 2–4 s in the dark.
- Can be **jailed/caged**; freeing one aggros it `(8:31)`.

### 9.2 Chocolate bunnies

- **Passive/harmless**, spawn from **chocolate-bunny spawners**, **drop chocolate
  bars** `(6:48, 8:46)`; infest the world `(29:26)`. **Take fall damage** — farmable
  `(35:30)`. **[EST]** 3 HP, drops 1–2 chocolate bars.

### 9.3 Spawner interaction

- Spawners can be **lit (torches) to disable** `(27:48)`, **destroyed**, or
  **capped with a block** to control spawn spots `(34:37)` — the basis of farms
  (§10). **[EST]** vanilla spawner rules (16-block player range, light-gated).

---

## 10. Farms

- **Waffle / XP farm** — from the tower's dense waffle-guy spawners `(26:06)`.
- **Chocolate-bar farm** — chocolate-bunny drop/kill farm (fall damage) → bars →
  chocolate `(29:34, 35:30)`. **[EST]** ~1–2 stacks of bars per 5 min `(35:46)`.
- **Cookie Army Factory** — the Gingerbread Furnace as an infinite soldier farm
  (§3.6).

---

## 11. Blocks, crafting & edibility

**Everything in the cookie world is edible** — most custom blocks convert to a
food item `(8:13)`.

| block / item | obtained | converts to | ref |
|---|---|---|---|
| Cookie Block | mine cookie biome | Cookies (×9 `[EST]`) ⇄ block | `(1:44, 14:03)` |
| Frosted Cookie Block | cookie biome (looks like sand) | — | `(16:17)` |
| Waffle Block | **raiding towers only — uncraftable** | — | `(35:55)` |
| Chocolate Bricks | raiding towers | Chocolate Bars → Chocolate block | `(8:13)` |
| Chocolate Bar | bunnies / bricks | Chocolate Block (`[EST]` 4 bars → 1 block) | `(6:48, 35:46)` |
| Candy Cane | candy-cane flora | Candy (food) | `(16:33)` |
| Hard Candy Blocks (raspberry/aniseed/grape/minty) | candy biome | jelly-armor mats / building | `(2:22, 39:17)` |
| Cookie (item) | craft from cookie block | recruit soldiers / eat | `(1:52)` |

- **Waffle Blocks are uncraftable** — only from **demolishing the Waffle Tower**
  `(35:55)`: a deliberate scarcity gate on the best building block. Chocolate is
  farmable to unlimited via bunnies.
- Full custom **block families** (block + stairs + slab + fence + door + wall per
  flavor) are visible in `keyframes/_JEI_INDEX.png`.

---

## 12. Structures

| structure | contains / role | ref |
|---|---|---|
| **Waffle / Cookie Tower** | Waffle Mage at top; waffle-guy + bunny spawners; chests (diamonds, gummies, lollipops, insta-health); a jailed waffle guy. **Only source of Waffle Blocks.** | `(7:57–9:03, 35:55)` |
| **Milkman's House** | Milkman, hidden in a mountain; gold, fortune-3 golden apples, diamonds, gingerbread-men chests, the **Milky Sword**, the **Gingerbread Furnace**, spawners. | `(36:43, 42:48)` |
| **Villages** | villager settlements — raidable for food/beds; template for the player's own builds. | `(0:48, 9:35)` |
| **Player builds** (Outpost → Village → Castle/Kingdom) | progression requirements; cookie/chocolate/waffle/candy blocks; watchtowers, armories, throne, armor-stand armory. | `(11:14, 20:34, 29:58)` |

---

## 13. Estimate summary (quick-tune sheet)

Every `[EST]` in one place, so a designer can tune from a single table. **None of
these are shown in the video** — they are best guesses tuned to reproduce it.

| system | estimated value(s) |
|---|---|
| Recruit cost | 1 cookie / soldier; cookie block → 9 cookies |
| Gingerbread claim | 1 body → 2 gingerbread men; furnace ≈ 1 soldier / 3–5 s |
| Soldier stats | Soldier 20 HP / 3 dmg; Knight 30 HP / 5 dmg; no self-heal; friendly fire always on |
| Cookie eat | +2 hunger, Speed I + Jump I 15 s |
| Gummy effects | Peach = Levitation 8 s; Orange = none/Saturation; Minty = Resistance II 30 s; Grape = Strength II 30 s; durations 30–60 s |
| Milk block | water-like source, opaque, upward-swimmable, Saturation+Regen on contact, Nausea if submerged |
| Milky Sword | AoE ~8–10 blocks, no cooldown, hits allies, ignores armor |
| Jelly set | Boots +20 KB / +3 armor; set = 21 armor + knockback-immune; set bonus = Regen I or no fall damage |
| Boss base HP | Cake Golem 1,500; Waffle Mage 4,000; Milkman 9,999 |
| Boss scaling | HP ×1.03/day, damage ×1.02/day, soldier power ×0.995/day (from boss-available day) |
| Milkman Aura | start 5,000; multiplier = 1 + Aura/5000 (2×→1×); cow = −100 pts; no regen |
| Waffle guy | 24 HP / 6 dmg, scales with bosses; spawner ≈ every 2–4 s |
| Chocolate bunny | 3 HP, 1–2 bars, fall-damage farmable |
| Rank gates | tier army sizes 0/35/60/100/150/200/300/400/500/1000/1000+, OR the tier's building/boss milestone |

> Suggested next step to firm these up: a short **in-game measurement pass** —
> time one Gingerbread-Furnace minute (spawn rate), count cookies from one block,
> read one gummy effect duration off the HUD, and note the starting Milkman-aura
> number. Those five readings would replace the softest guesses above with real
> values.
