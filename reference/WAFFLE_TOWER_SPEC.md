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

Frames: `t_484`–`t_487` (the "✅ WAFFLE TOWER" card, base-up the corner);
`images/exterior_distant_day10.png` (Day-10 distant silhouette, video `t≈655`);
`images/top_platform_a.png`/`_b.png` (the top, video `t≈604–605`).

- **Form:** a tall **square tower** — taller than wide, but a substantial keep,
  not a thin spire. (The Day-10 distant shot looks narrow only because it's
  foreshortened and half-hidden by candy flora; the **roof deck is the reliable
  measure**.)
- **Wall composition (per face):** a field of **chocolate brick** with
  **waffle-block vertical pilasters** running the full height, plus lighter
  **marshmallow corner columns** at the four edges; waffle-block horizontal
  accent bands. Reads mostly golden from a distance. ✅ pattern, 🟡 exact spacing.
- **Openings:** occasional recessed/glowing wall cells (torch / waffle "window")
  break the chocolate field (`t_485`, left). 🟡
- **Height:** 🟡 tall — the distant shot puts it well above the ~8-tall candy
  flora and the interior is ~5 floors → build **~28–34 blocks** (a bit taller
  than wide).
- **Footprint:** ✅ **~11–14 across** (counted on the top deck in
  `top_platform_b`) → build **~13×13 outer** (odd width → a cleanly centred
  shaft), interior ≈11×11. Wide enough for the columned halls and a central
  shaft. (Earlier ~7 guess retracted.)
- **Roof / top:** ✅ **observed** (`top_platform_a/_b`). A flat **waffle-block
  roof deck** ringed by a **chocolate-brick parapet** with **waffle-block
  battlements** (gapped walls), **white marshmallow columns** rising at the
  corners/edges, loot **chests** on the deck, red-candy accents, and a **central
  opening** in the deck — the top of the interior shaft the player climbs. The
  Waffle Mage perches here / floats up off it once defenders are cleared.
- **Setting:** stands in the candy biome among white marshmallow/lollipop stems
  and candy flora (`t_485`, `exterior_distant_day10`). ✅

```
       exterior massing (one face)  — ~13 wide, taller than wide (schematic)
        ┌───────────────┐  ← flat waffle roof deck: chocolate parapet +
        │ M ▓ W ▓ W ▓ M │      waffle battlements, marshmallow columns,
        │ ▓ ▓ ▓ ▓ ▓ ▓ ▓ │      chests, central shaft opening (Mage perch) ✅
        │ M ▓ ▒ ▓ ▒ ▓ M │   W = waffle pilaster   ▓ = chocolate brick
  ~28-34│ ▓ ▓ ▓ ▓ ▓ ▓ ▓ │   ▒ = window/red-candy  M = marshmallow column
   tall │ M ▓ W ▓ W ▓ M │
        │ ▓ ▓ ▓ ▓ ▓ ▓ ▓ │
        │ M ▓ ▓door▓ ▓ M│  ← ground entrance (🔴 EST)
        └───────────────┘
           ~13 wide (deck counted 11–14)
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
| outer footprint | ~13×13 (deck counted ~11–14 across) | ✅ |
| interior | ~11×11 | ✅ |
| height | ~28–34 (≈5–6 floors × ~5) | 🟡 |
| corner columns | marshmallow, full height | ✅ |
| wall | chocolate brick + waffle pilasters + red-candy accents | ✅ pattern |
| floors | checkered waffle / marshmallow | ✅ |
| circulation | ladders + central shaft | 🟡 |
| roof | flat waffle deck + chocolate parapet/battlements, marshmallow columns, central shaft opening (Mage perch) | ✅ |
| defenders | waffle-guy spawners, several per floor | ✅ concept |
| boss | Waffle Mage at top, spawns when defenders cleared | ✅ |
| loot | chest + double-chest per floor or two | ✅ |

---

## 6. Coverage / what to firm up

- **Interior:** well captured — materials, checkered floors, columns, ladders,
  chests, beds, defenders, and the shaft-to-Mage are all directly observed.
- **Exterior:** now firmed from three angles — base-up wall detail (`t_484-487`),
  the **top deck** (`top_platform_a/_b`, `t≈604-605`), and a **Day-10 distant
  silhouette** (`exterior_distant_day10`, `t≈655`) showing a slender golden
  spire. Only the exact **height, wall-pilaster spacing, and door placement**
  remain soft (🟡) — fine to tune during the build.
- **Optional upgrade:** the distant + rooftop frames are from the 360p scan
  copy. They can be re-pulled at 1080p (needs the cookies re-shared) for crisper
  reference, but the massing is already clear.
```
