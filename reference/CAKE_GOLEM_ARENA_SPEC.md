# Cake Golem Arena — 3D Build Spec

A block-by-block, 3D-mapped reconstruction of the **Cake Golem boss arena**
seen at **Day 20 (≈14:40–15:35)** of the source playthrough, distilled into a
faithful, buildable structure for the mod.

> Role (MECHANICS_SPEC §8.2): the Cake Golem is **boss 1**. It "roams; spawns
> near waffle-guy spawners." In the video the fight is staged in a large candy
> **colonnade/colosseum** — a grid of white candy columns under an open beam
> roof, planted with a garden of checkerboard lollipop posts, with **mob-spawner
> cages on the open floor**. The golem is fought in the middle of the arena.
>
> **No central shrine/altar exists.** The brown, ornate, maze-patterned mass at
> centre — with two red "cage/lantern"-looking shapes hanging beside it — is the
> **Cake Golem mob itself** (its torso is chocolate-brown like the intro card;
> the red shapes are its fists). Do not build a shrine.

All reference images live in [`cake_golem_arena/images/`](cake_golem_arena/images/).
Frames were pulled from `reference/source.mp4` at 860–945 s (see EXTRACT.md).

---

## 1. Reference gallery (wide shots)

| view | image | what it shows |
|---|---|---|
| Colonnade (best overview) | `images/arena_wide_colonnade.png` | Full grid of white columns + beam roof, tiered field of lollipop-disc posts, spawner cages, golem. |
| Pergola / roof | `images/arena_wide_pergola.png` | Overhead white beam lattice, candy-cane posts, golem centre, spawner cages at base. |
| Spawner cages + golem | `images/arena_wide_spawners.png` | The Cake Golem (brown maze torso + red fists) mid-arena; two spawner cages on the floor; columns. **The brown "gate" is the golem, not a build.** |
| Golem centre | `images/arena_golem_center.png` | Cake Golem (red maze-textured from this angle) between candy-cane posts, checker discs behind. |
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
| I | Ground / terraces | `images/elem_cookie_frosted_ground.png` | `cookie_surface` top, `cookie_block` / `frosted_cookie_block` body | Sandy-cookie tiered field; frosting-maze tops. |
| J | Dark-oak platform / step | `images/elem_darkoak_platform.png` | `minecraft:dark_oak_planks` | Minor accent — small stepped platforms/paths near the floor. Not a shrine. |
| L | Hedges / bushes | `images/arena_wide_colonnade.png` | `minecraft:oak_leaves` | Green leaf clumps dotting the floor. |
| M | Spawner cages | `images/elem_spawner_cage.png` | `cookiemod:waffle_guy_spawner` (+ golem spawn) | Black cages sitting on the open floor — the "spawns near waffle-guy spawners" beat. |

Confirmed **not** a build — it's the **Cake Golem mob**
(`images/elem_cake_golem_mob.png`, `images/elem_wood_shrine_lanterns.png`),
already modelled: a brown maze-patterned torso (intro-card colour) with red
fists. There is **no shrine, altar, gate, or dais** in the arena.

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
- **Centre:** an **open spawn floor** — flat cookie ground where the golem
  spawns, ringed by four **spawner cages**. No raised dais.
- **Interior floor:** candy-cane posts (§7) and hedges dotted about, leaving a
  clear central circle for the fight. (The checkerboard lollipop discs seen in
  the footage are **cookie-biome flora in the background, not part of the arena**
  — see §6.)

### Top-down plan (y = 1, one cell = 1 block)

```
Legend: C aniseed column   c candy-cane post   h hedge(oak leaves)
        G golem spawn point  x spawner cage
        . cookie_surface floor    # perimeter kerb (frosted/cookie)

  0 1 2 3 4 5 6 7 8 9 ...            20
0 C # # # # C # # # # C # # # # C # # # # C
1 # . . . . # . h . . # . . . . # . . . . #
2 # . . c . # . . . . # . . . . # . h . . #
3 # . . h . # . . . . # . . . . # . . . . #
4 # . . . . # . . . . # . . . . # . . c . #
5 C # # # # C # # # # C # # # # C # # # # C
6 # . h . . # . . . . # . . . . # . . h . #
7 # . . c . # . . x . . . x . . # . c . . #
8 # . . . . # . . . . G . . . . # . . . . #   <- open centre; G = golem spawn
9 # . . . . # . . x . . . x . . # . . . . #
10 C # # # # C # # # # C # # # # C # # # # C
   (mirror rows 6..9 across the centre for rows 11..14, etc.)
```

The central bay is left **open** (clear fight circle). Four **spawner cages (x)**
sit on the floor around the centre; the golem spawns at **G**. Hedges (h) and
candy-cane posts (c) are scattered lightly.

### Side elevation (section through the centre row, x = 0..20)

```
 y6  ====  ====  ====  ====  ====   <- beam lattice (aniseed), gaps between
 y5  C           C           C           C
 y4  C           C           C           C
 y3  C       c   C     . .    C       c   C     c=candy-cane post
 y2  C       c   C    (GOLEM) C       c   C
 y1  C       c   C  x       x C       c   C     x=spawner cage  (open centre)
 y0 ===cookie_surface pad (frosted top)========================
```

Columns 6 tall; beams at y6. The centre is flat open ground — the golem stands
here, no built structure. (Background biome lollipops are not shown.)

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

## 5. Central spawn floor

There is **no shrine, altar, or dais**. The centre is simply **flat open cookie
ground**, kept clear of posts and discs so the golem and the soldier swarm have
room to fight (`arena_wide_spawners.png`, `arena_golem_center.png`).

- **Spawn point:** the centre floor tile (local 10,10), at pad level. The golem
  spawns **on first player approach** (within ~40 blocks), not at world
  generation — driven by a hidden `cake_golem_altar` marker buried one block
  under the centre floor, which random-ticks to spawn one golem then reverts to
  cookie block.
- **Spawner cages:** four `waffle_guy_spawner` blocks on the floor around the
  centre (roughly a 5×5 ring), sitting flush with the ground.
- Optional: a scatter of small `dark_oak_planks` platform/step accents near the
  perimeter (`elem_darkoak_platform.png`) — decorative only.

---

## 6. Checker lollipop discs — NOT part of the arena

The field of checkerboard lollipop-disc posts (`images/elem_lollipop_garden.png`,
`elem_disc_red.png`) is **the surrounding cookie biome's flora showing through**,
not a built element of the arena. The mod already generates these as biome
lollipops. **Do not place them in the arena Feature.** The arena sits *within*
that flora; the biome provides the garden backdrop for free.

---

## 7. Candy-cane posts & accents

- **Posts:** `candy_cane_block` columns, height **3–4**, dotted lightly along the
  interior (`elem_candycane_post.png`). Bare (no disc tops).
- **Ground litter:** occasional short horizontal `candy_cane_block` runs on the
  pad (red/white stripes, `arena_wide_colonnade.png`).
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
2. Place **`waffle_guy_spawner`** blocks in a ring on the floor around the centre
   (the "x" cages) so the arena continuously fields waffle-guy adds.
3. Spawn **one Cake Golem** on the open centre floor **on first player approach**
   (a buried `cake_golem_altar` marker random-ticks once a player is near, spawns
   the golem with its boss bar, then reverts to cookie block). It roams the arena
   and is meant to fall fast to a soldier swarm.

> Implementation options, cheapest → richest: (a) a custom `Feature` that hand-
> places the palette procedurally from this spec; (b) an NBT structure template +
> jigsaw; (c) a hybrid — NBT shrine core + procedural disc garden. Recommend (a)
> for full control over placement and the flatness guard.

---

## 10. Build order (for the Feature)

1. Flatten & lay the 21×21 `cookie_surface` pad (+ perimeter kerb).
2. Raise the 25 `aniseed` columns (y1–y6).
3. Lay the y6 beam lattice.
4. Place the 4 `waffle_guy_spawner` cages in a ring on the open centre floor.
5. Scatter candy-cane posts & litter (§7).
6. Add hedges; optional dark-oak platform accents / terraces / moat.
7. Bury the `cake_golem_altar` marker under the centre — it spawns the golem on
   first player approach.

---

## 11. Open questions / deviations

- **Columns:** aniseed (glossy white) vs marshmallow (flat white) — chose aniseed
  for the structural skeleton; revisit against in-game render.
- **Terraces & moat** are simplified/optional for reliable worldgen.
- **Arena size** distilled to 21×21; the video build is larger and irregular.
  Scale up if generation budget allows.
- **No shrine/dais** — corrected after review; the centre is open ground and the
  golem spawns there. (The "shrine" earlier was a misread of the golem itself.)
