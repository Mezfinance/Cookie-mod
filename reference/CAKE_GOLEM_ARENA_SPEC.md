# Cake Golem Arena — 3D Build Spec

A block-by-block, 3D-mapped reconstruction of the **Cake Golem boss arena**
seen at **Day 20 (≈14:40–15:35)** of the source playthrough, distilled into a
faithful, buildable structure for the mod.

> Role (MECHANICS_SPEC §8.2): the Cake Golem is **boss 1**. It "roams; spawns
> near waffle-guy spawners." In the video the fight is staged in a large candy
> **colonnade/colosseum** — a grid of white candy columns under an open beam
> roof, planted with a garden of checkerboard lollipop posts, with a central
> wooden **shrine/altar** flanked by mob-spawner cages where the golem appears.

All reference images live in [`cake_golem_arena/images/`](cake_golem_arena/images/).
Frames were pulled from `reference/source.mp4` at 860–945 s (see EXTRACT.md).

---

## 1. Reference gallery (wide shots)

| view | image | what it shows |
|---|---|---|
| Colonnade (best overview) | `images/arena_wide_colonnade.png` | Full grid of white columns + beam roof, tiered field of lollipop-disc posts, spawner cages, golem. |
| Pergola / roof | `images/arena_wide_pergola.png` | Overhead white beam lattice, candy-cane posts, golem centre, spawner cages at base. |
| Shrine + spawners | `images/arena_wide_spawners.png` | Central wooden shrine with hanging lanterns, two flanking spawner cages, columns. |
| Golem centre | `images/arena_golem_center.png` | Cake Golem (red maze-textured) between candy-cane posts, checker discs behind. |
| Base blocks | `images/arena_base_blocks.png` | Raspberry-candy column, dark-oak platform, cookie + frosted-cookie ground. |
| Fight overview | `images/arena_fight_overview.png` | Player-eye view: columns, discs, spawner, hedges, moat. |

---

## 2. Block palette

Each in-video block mapped to the mod block that already exists (see
`registry/ModBlocks.java`). "Vanilla" = use the vanilla block.

| # | in-video element | ref crop | mod block | notes |
|---|---|---|---|---|
| A | White columns & roof beams | `images/elem_white_column_beam.png` | `cookiemod:aniseed_hard_candy_block` | Smooth glossy white; the arena's structural skeleton. Marshmallow is the flatter-white alt. |
| B | Red/white striped posts | `images/elem_candycane_post.png` | `cookiemod:candy_cane_block` | Vertical candy-cane accent posts + hanging drops. |
| C | Big red flecked column/wall | `images/elem_raspberry_block.png` | `cookiemod:raspberry_hard_candy_block` | Red with white/pink flecks; occasional accent masses. |
| D | Checker disc — purple | `images/elem_disc_purple.png` | `grape_hard_candy` + `aniseed` | Lollipop-disc head, grape/white checker. |
| E | Checker disc — red | `images/elem_disc_red.png` | `raspberry_hard_candy` + `aniseed` | Lollipop-disc head, raspberry/white checker. |
| F | Checker disc — teal | `images/elem_disc_teal.png` | `minty_hard_candy` + `aniseed` | Lollipop-disc head, minty/white checker. |
| G | Checker disc — brown | (see `elem_lollipop_garden.png`) | `chocolate_block` + `aniseed` | Brown/white checker variant seen in the field. |
| H | Disc post stems | `images/elem_lollipop_garden.png` | `cookiemod:marshmallow_block` | Thin white stems the discs sit on (matches biome lollipops). |
| I | Ground / terraces | `images/elem_cookie_frosted_ground.png` | `cookie_surface` top, `cookie_block` / `frosted_cookie_block` body | Sandy-cookie tiered field; frosting-maze tops. |
| J | Shrine wood + platform | `images/elem_darkoak_platform.png` | `minecraft:dark_oak_planks` + `cookiemod:chocolate_brick_block` | Dark-wood altar body with ornate chocolate-brick inlay. |
| K | Shrine hanging lanterns | `images/elem_wood_shrine_lanterns.png` | `minecraft:lantern` (hung on `minecraft:chain`) | Two lanterns flank the shrine face. |
| L | Hedges / bushes | `images/arena_wide_colonnade.png` | `minecraft:oak_leaves` | Green leaf clumps dotting the floor. |
| M | Spawner cages | `images/elem_spawner_cage.png` | `cookiemod:waffle_guy_spawner` (+ golem spawn) | Black cages at the shrine base — the "spawns near waffle-guy spawners" beat. |

Confirmed **not** a build: the large red maze-textured mass at centre is the
**Cake Golem mob itself** (`images/elem_cake_golem_mob.png`), already modelled.

---

## 3. Footprint & overall form

A square **colonnade** on a flattened cookie pad, open to the sky between roof
beams. Distilled to a clean, tileable grid (video build is looser/organic):

- **Pad:** 21 × 21 blocks of `cookie_surface` (frosted top), y = 0 (ground).
- **Column grid:** 5 × 5 columns on a **5-block pitch** at local coords
  x,z ∈ {0, 5, 10, 15, 20}. 25 columns total.
- **Column:** 1×1 `aniseed_hard_candy_block`, **height 6** (y = 1..6).
  Corner columns may be 2×2 for heft (optional).
- **Roof beams:** `aniseed_hard_candy_block` laid along the tops (y = 6)
  connecting adjacent column heads in **both** directions, leaving the 4 inner
  cells of each 5×5 bay **open to sky** (a lattice, not a solid roof).
- **Centre (10,10):** the **shrine dais** (see §5).
- **Interior floor:** planted with the **lollipop-disc garden** (§6), candy-cane
  posts (§7), and hedges, leaving a clear ring around the dais for the fight.

### Top-down plan (y = 1, one cell = 1 block)

```
Legend: C aniseed column   c candy-cane post   o lollipop-disc post
        S shrine dais(5x5)  x spawner cage      h hedge(oak leaves)
        . cookie_surface floor    # perimeter kerb (frosted/cookie)

  0 1 2 3 4 5 6 7 8 9 ...            20
0 C # # # # C # # # # C # # # # C # # # # C
1 # . o . . # . h . . # . . o . # . . . . #
2 # . . . . # . . . . # . o . . # . h . . #
3 # o . h . # . o . . # . . . . # . . o . #
4 # . . . . # . . . . # . . . . # . . . . #
5 C # # # # C # # # # C # # # # C # # # # C
6 # . h . . # . . o . # . o . . # . . h . #
7 # . . o . # . . . x S S S x . # o . . . #
8 # . . . . # . o . S S S S S . # . . o . #   <- shrine dais 5x5 centred (8..12)
9 # o . . . # . . . x S S S x . # . . . . #
10 C # # # # C # # # # C # # # # C # # # # C
   (mirror rows 6..9 across the centre for rows 11..14, etc.)
```

The dais occupies the central 5×5 (local 8..12 in x and z). Four **spawner
cages (x)** sit at the dais corners at floor level; the **shrine** rises from
the dais centre.

### Side elevation (section through the centre row, x = 0..20)

```
 y6  ====  ====  ====  ====  ====   <- beam lattice (aniseed), gaps between
 y5  C           C     ^     C           C
 y4  C          C   [shrine roof]  C      C
 y3  C   o   c   C   [ | ~ | ]     C   o   C     o=disc head  c=candy-cane
 y2  C   |   c   C   [ARCH+lant]   C   |   C     |=marshmallow stem
 y1  C   |   c   C   x[dais+1]x    C   |   C     x=spawner cage
 y0 ===cookie_surface pad (frosted top)========================
```

Columns 6 tall; beams at y6; disc heads float ~y3 on 2-tall stems; shrine peak
reaches ~y5 on a 1-high dais.

---

## 4. Roof lattice (columns + beams)

- Columns: `aniseed_hard_candy_block`, y1–y6, at the 5-pitch grid nodes.
- Beams: at **y6**, run `aniseed` between every orthogonally-adjacent column
  head (so each 5-length span gets a 4-block beam bridging two columns).
- Result: a square open pergola — solid over the column lines, **open sky** over
  the bay interiors. Matches `arena_wide_pergola.png` (you can see blue between
  beams).
- Optional: hang a **candy-cane drop** (1–2 `candy_cane_block`) from a beam
  midpoint here and there (seen dangling in `arena_wide_spawners.png`).

---

## 5. Central shrine / spawn altar

From `images/elem_wood_shrine_lanterns.png` + `arena_wide_spawners.png`.

- **Dais:** 5×5 raised platform, **+1** above the pad, top of
  `frosted_cookie_block` (or `cookie_surface`), edged with `chocolate_brick_block`.
- **Altar body:** a **3-wide, 4-tall** face centred on the dais:
  - Body: `dark_oak_planks` framing an ornate **`chocolate_brick_block`** inlay
    panel (the maze look).
  - Roof: a small **peaked** cap using `spruce_stairs`/`dark_oak_stairs` (two
    steps to a point), matching the gabled top in the crop.
- **Lanterns:** one `minecraft:lantern` on a 1–2 `minecraft:chain` hung off each
  front corner of the roof (two total).
- **Golem spawn point:** on the dais top, directly in front of the altar face.

---

## 6. Lollipop-disc garden (the signature motif)

Dozens of short posts each topped with a small checkerboard disc fill the field
(`images/elem_lollipop_garden.png`, `elem_disc_red.png`). These are **miniature
versions of the tall biome lollipops**, planted like a flower bed.

Per post:
- **Stem:** `marshmallow_block`, **2–3** tall (vary for a natural look).
- **Disc head:** a **3×3 vertical square** (a flat panel facing a cardinal
  direction) in a **checker** of white + one colour, sitting on the stem top:
  ```
    W X W        W = aniseed_hard_candy_block (white)
    X W X        X = the colour block for this disc
    W X W
  ```
- **Colour cycle** (distribute roughly evenly across the field):
  - purple → `grape_hard_candy_block`  (`elem_disc_purple.png`)
  - red → `raspberry_hard_candy_block`  (`elem_disc_red.png`)
  - teal → `minty_hard_candy_block`  (`elem_disc_teal.png`)
  - brown → `chocolate_block`
- **Placement:** scatter 1 post every ~2–3 floor cells inside the bays, jittered
  off-grid; keep the dais ring and walkways clearer. ~30–50 posts total.

---

## 7. Candy-cane posts & accents

- **Posts:** `candy_cane_block` columns, height **3–4**, dotted along the
  interior and near the dais corners (`elem_candycane_post.png`). Some are topped
  with a single checker disc; most are bare.
- **Ground litter:** short horizontal `candy_cane_block` runs lying on the pad
  (seen as red/white stripes on the floor in `arena_wide_colonnade.png`).
- **Raspberry accents:** occasional 1–2 wide `raspberry_hard_candy_block` masses
  at the perimeter (`elem_raspberry_block.png`) — used sparingly.

---

## 8. Ground, terraces & surrounds

- **Pad & terraces:** the field steps up in low **terraces** toward the back
  (`elem_tiered_terrace.png`) — model as 1-block steps of
  `cookie_surface`/`frosted_cookie_block`. On flat generation, a single-level pad
  is acceptable; terraces are a "nice to have."
- **Perimeter kerb:** a 1-high border of alternating `cookie_block` /
  `frosted_cookie_block` around the 21×21 pad.
- **Hedges:** small `oak_leaves` clumps (1×1×1 to 2×2×1) scattered on the floor.
- **Moat (optional):** a strip of water along one edge (a river is visible behind
  the arena in `arena_wide_pergola.png`). Skip if it complicates generation.

---

## 9. Spawn logic (design intent)

Ties the structure to MECHANICS_SPEC §8.2 ("spawns near waffle-guy spawners"):

1. Structure generates **rarely** in / at the edge of the **cookie biome**
   (jigsaw or a single-piece NBT/`Feature`), on flat-ish ground.
2. Place **`waffle_guy_spawner`** blocks at the dais corners (the "x" cages) so
   the arena continuously fields waffle-guy adds — the in-video cages.
3. Spawn **one Cake Golem** on the dais when the structure generates (or on first
   player approach), with its boss bar. It roams the arena and is meant to fall
   fast to a soldier swarm.

> Implementation options, cheapest → richest: (a) a custom `Feature` that hand-
> places the palette procedurally from this spec; (b) an NBT structure template +
> jigsaw; (c) a hybrid — NBT shrine core + procedural disc garden. Recommend (a)
> for full control over the disc scatter and to reuse the existing lollipop/disc
> helpers in `LollipopTreeFeature`.

---

## 10. Build order (for the Feature)

1. Flatten & lay the 21×21 `cookie_surface` pad (+ perimeter kerb).
2. Raise the 25 `aniseed` columns (y1–y6).
3. Lay the y6 beam lattice.
4. Build the central dais (+1) and the dark-oak/chocolate-brick shrine + lanterns.
5. Place the 4 `waffle_guy_spawner` cages at the dais corners.
6. Scatter the lollipop-disc garden (§6), then candy-cane posts & litter (§7).
7. Add hedges; optional terraces / moat.
8. Spawn the Cake Golem on the dais.

---

## 11. Open questions / deviations

- **Columns:** aniseed (glossy white) vs marshmallow (flat white) — chose aniseed
  for the structural skeleton; revisit against in-game render.
- **Terraces & moat** are simplified/optional for reliable worldgen.
- **Arena size** distilled to 21×21; the video build is larger and irregular.
  Scale up if generation budget allows.
- **Golem-in-front vs on-dais** spawn offset — tune so it doesn't clip the shrine.
