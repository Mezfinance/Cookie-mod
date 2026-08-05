# Waffle Tower — 3D build spec

Reconstruction spec for the **Waffle Tower** (a.k.a. Waffle/Cookie Tower),
the raid dungeon that fields the waffle-guy defenders and the **Waffle Mage**
boss on top (MECHANICS_SPEC §8.3, §10).

**Source:** video `sgEkKc10RlI`, Day 8–9 raid, `t≈468–564 s` (7:48–9:24).
Per-second stills in [`waffle_tower/frames/`](waffle_tower/frames) and the
timeline in [`waffle_tower/INDEX.md`](waffle_tower/INDEX.md). Frame citations
below are `t_<sec>`.

**Confidence key:** ✅ observed clearly · 🟡 partially observed / inferred ·
🔴 [EST] not captured, estimated for a buildable structure.

---

## 1. Palette (block list)

All observed in the raid stills. Blocks already exist in the mod unless noted.

| role | block | confidence | notes |
|---|---|---|---|
| main wall infill | **Chocolate Brick Block** | ✅ | dark-brown gridded field, the bulk of the walls (`t_485`, `t_502`) |
| wall pilasters / bands | **Waffle Block** | ✅ | golden gridded; vertical stripes up the faces + accent bands (`t_485`) |
| corner columns | **Marshmallow Block** (white) | 🟡 | light columns framing the tower edges & interior pillars (`t_485`, `t_522`, `t_532`) |
| floor tile A | **Waffle Block** | ✅ | golden half of the checkered floor (`t_522`, `t_532`) |
| floor tile B | **Marshmallow Block** (white) | 🟡 | white half of the checkered floor |
| accents / trim | **Raspberry Hard Candy Block** (red) | ✅ | red gridded blocks dotted through walls/floors (`t_512`, `t_542`) |
| ladders | vanilla **ladder** (or waffle-themed) | 🟡 | brown ladders climb between floors (`t_532`) |
| loot | **Chest** + **double Chest** | ✅ | "Chest" and "Large Chest" with diamonds/potions/lollipop (`t_507`, `t_531`) |
| spawn markers | **purple bed** | ✅ | purple beds on the floors (`t_540`) |

---

## 2. Exterior

Best frames: `t_484`–`t_487` (the "✅ WAFFLE TOWER" discovery card, looking
straight up the corner from the base).

- **Form:** a tall, square **tower** — not a pyramid. Looking straight up from
  the base, the walls recede far into the distance. ✅ tall / square.
- **Wall composition (per face):** a field of **chocolate brick** with
  **waffle-block vertical pilasters** running the full height, spaced along the
  face, plus lighter **marshmallow corner columns** at the four edges. Waffle
  blocks also appear as horizontal accent bands. ✅ pattern, 🟡 exact spacing.
- **Openings:** occasional recessed/glowing wall cells (a torch or waffle
  "window") break the chocolate field (`t_485`, left). 🟡
- **Height:** 🔴 [EST] the interior is clearly multi-storey and the exterior
  recedes steeply out of frame → build **~28 blocks** (≈5 interior floors).
- **Footprint:** 🔴 [EST] **~11×11** outer (≈9×9 interior) — wide enough for the
  columned interior halls seen in `t_522`/`t_532`.
- **Roof / top:** 🔴 [EST] never shown. The Mage floats free at the top once
  defenders are cleared, so build an **open/battlemented top platform** (flat
  waffle-block roof ringed by a 1-block chocolate-brick parapet) as the Mage's
  perch.
- **Setting:** stands in the candy biome among white marshmallow/lollipop stems
  and candy flora (`t_485`). ✅

```
        [EST] exterior massing (one face)
        ┌───────────────┐  ← flat waffle roof + parapet (Mage perch)
        │ W ▓ ▓ W ▓ ▓ W │     W = waffle pilaster (full height)
        │ ▓ ▓ ▓ ▓ ▓ ▓ ▓ │     ▓ = chocolate brick
   ~28  │ W ▓ ▒ ▓ ▒ ▓ W │     ▒ = window / red-candy accent
   tall │ ▓ ▓ ▓ ▓ ▓ ▓ ▓ │     M = marshmallow corner column (both ends)
        │ W ▓ ▓ W ▓ ▓ W │
        │ ▓ ▓ [door] ▓ ▓│  ← ground entrance (🔴 EST)
        M───────────────M
              ~11 wide
```

---

## 3. Interior

Best frames: `t_507`, `t_512`, `t_522`, `t_531`, `t_532`, `t_540`, `t_552`.

- **Layout:** multiple stacked floors around a **central open vertical shaft**;
  the player climbs floor-to-floor. ✅ multi-floor + shaft.
- **Floors:** **checkered waffle-floor** — golden **Waffle Block** alternating
  with white **Marshmallow Block** in a checkerboard (`t_522`, `t_532`). ✅
- **Columns:** white **marshmallow pillars** stand on the floors, supporting the
  ceilings and lining the shaft (`t_522`, `t_532`). 🟡
- **Walls (inner):** same chocolate-brick + waffle-block mix as outside, with
  **red hard-candy** blocks dotted in as decor (`t_512`, `t_542`). ✅
- **Vertical circulation:** **ladders** up the inner walls between floors
  (`t_532`); 🔴 [EST] one ladder run per floor near the shaft.
- **Ceiling height:** 🔴 [EST] ~5 blocks per floor.

```
   [EST] interior section (side view)
   ═══════════════  roof / Mage perch (top)
   │ c   ▓▓   c │   floor 5  ┐
   │  [chest]   │           │ each floor:
   │ c   ║║   c │   floor 4  │  - checkered waffle/marshmallow tiles
   │  [bed][bed]│           │  - marshmallow columns (c)
   │ c   ║║   c │   floor 3  │  - ladder (║) by the central shaft
   │  [chest]   │           │  - waffle-guy defenders
   │ c   ║║   c │   floor 2  │  - red-candy accents
   │  [entrance]│   floor 1  ┘
   ═══════════════  ground (candy biome)
```

---

## 4. Contents & mobs

- **Defenders — waffle guys:** dense throughout every floor (`t_512`, `t_522`,
  `t_542`); this is the "tower's dense waffle-guy spawners" of the waffle/XP
  farm (§10). ✅ Build with **waffle-guy spawners** (works day or night, like
  the arena spawners) — a few per floor.
- **Waffle Mage:** ✅ boss bar appears at `t_548` as the player looks up the
  shaft ("is that his … up there?"). Sits/spawns at the **top**; per §8.3 it
  **spawns once the defenders are cleared**. Build: Mage spawn point on the roof
  platform, gated on the tower's spawners/defenders being dead.
- **Loot chests:** ✅ regular **Chest** (Splash Potion of Healing, Lollipop —
  `t_507`) and a **Large/double Chest** ("DIAMONDS!" — `t_531`). One or two per
  floor with waffle-blocks, chocolate, candy, potions, a lollipop, diamonds.
- **Purple beds:** ✅ on the floors (`t_540`) — decor / waffle-guy "homes".

---

## 5. Build parameters (for the worldgen Feature)

Mirrors the Cake Golem arena approach (grid-spread placement in the candy biome,
mob-spawner defenders, boss spawns on approach/clear).

| parameter | value | conf |
|---|---|---|
| biome | candy/cookie biome | ✅ |
| outer footprint | ~11×11 | 🔴 EST |
| interior | ~9×9 | 🔴 EST |
| height | ~28 (≈5 floors × ~5) | 🔴 EST |
| corner columns | marshmallow, full height | 🟡 |
| wall | chocolate brick + waffle pilasters + red-candy accents | ✅ pattern |
| floors | checkered waffle / marshmallow | ✅ |
| circulation | ladders + central shaft | 🟡 |
| roof | flat waffle + chocolate parapet, open (Mage perch) | 🔴 EST |
| defenders | waffle-guy spawners, several per floor | ✅ concept |
| boss | Waffle Mage at top, spawns when defenders cleared | ✅ |
| loot | chest + double-chest per floor or two | ✅ |

---

## 6. Coverage / what to firm up

- **Interior:** well captured — materials, checkered floors, columns, ladders,
  chests, beds, defenders, and the shaft-to-Mage are all directly observed.
- **Exterior silhouette (🔴):** the discovery frames are all steep base-up
  angles; there is **no wide establishing shot** of the whole tower in this
  raid, so **height, footprint, and roof are estimates**. To nail them, the best
  options are a distant in-game screenshot of a tower, or pulling a later-raid
  establishing shot from the video (the player re-raids towers for waffle blocks
  later on). Everything else can be built now and the massing tuned after.
```
