# Cookie / Candy Biome — Block-by-Block Recreation Map

A reconstruction guide for the mod's cookie/candy biome, read off the
playthrough footage. Reference stills are in
[`frames/biome/`](frames/biome/) — index sheet:
[`frames/biome/_BIOME_STILLS.png`](frames/biome/_BIOME_STILLS.png).

> **Source note:** this is inferred from video stills, not a world download or
> schematic. Block counts (stem heights, canopy sizes) are read from on-screen
> pixels and are accurate to ±1 block. Where a texture could be one of two
> custom blocks, the alternatives are listed.

---

## 1. At a glance

| element | what it is |
|---|---|
| Sky | flat **pink** (biome sky tint) |
| Ground surface | **Cookie Block** — golden waffle-pattern top, brown cookie-chip sides (top/side differ like a grass block) |
| Subsurface | more **Cookie Block**, then vanilla stone underneath |
| Ground cover | tan **candy-grass** tufts (wheat-height, cream/tan) scattered on the surface |
| Canopy flora | **candy canes** (tall, hooked) and **lollipop trees** (stem + disc) |
| Undergrowth | low **hard-candy bushes** (raspberry / grape / minty / aniseed clusters) |
| Ambient mobs | gummy bears (green), polar bears (pink/orange), chocolate bunnies |

Palette: pink sky, cream/tan ground, and the four hard-candy accent colors —
**raspberry red**, **grape purple**, **minty teal**, **aniseed white**.

---

## 2. Ground (layers, top → bottom)

```
 y+0  Cookie Block            golden waffle top / brown cookie sides
 y-1  Cookie Block            (brown all faces)
 y-2  Cookie Block
 y-3… vanilla stone           standard underground
      + candy-grass tufts sitting ON TOP of the surface layer
```

- The surface reads as a grass-style block: the **top face is baked golden**
  with a maze/waffle pattern, the **side faces are brown with darker chocolate
  chips**. Treat it as a single custom "Cookie Block" with a distinct top.
- Terrain is gently rolling — low cookie-block mounds and 1–3 block steps, with
  worn cream-colored paths where the ground is trodden flat
  (`biome__terrain_edge.png`, `biome__cookie_ground.png`).

---

## 3. Flora

### 3a. Candy cane (the tall hooked pillars)

Reference: `biome__lollipop_trees_closeup.png` (center).

```
        ___
       /   \        <- hook: 2–3 blocks over, curling down 1–2
  ||  |               vertical shaft, ~8–12 blocks tall
  ||
  ||
  ====   ground (Cookie Block)
```

- Built as a vertical column **~8–12 blocks tall** that **hooks over at the top**
  (the classic candy-cane J), 2–3 blocks across the hook.
- Skin is a **red-and-white diagonal/checker stripe** = **Candy Cane Block**
  (the red/white striped block seen in the castle builds), or equivalently
  **Raspberry Hard Candy Block (red checker) alternated with Aniseed Hard Candy
  Block (white)**. Both read identically at distance.
- They generate in loose clusters, tallest features in the biome.

### 3b. Lollipop tree (stem + candy disc)

Reference: `biome__candy_biome_pinksky.png`, `biome__lollipop_trees_closeup.png`.

```
   [][][]        <- canopy: flat disc/pad of Hard Candy Block, ~3×3×1,
   [][][]           colored: grape / minty / raspberry / aniseed
     ||           <- stem: single white column, 2–8 blocks tall
     ||              (white "peppermint"/aniseed stalk)
    ====  ground
```

- **Stem:** a thin **white single-block column**, height varies **2–8 blocks**
  (short lollipops near ground, tall ones tower over the canes).
- **Canopy:** a **flat disc/pad of one Hard Candy Block color**, roughly
  **3×3 (sometimes 3×2) and 1 block thick**, sitting on the stem top. Colors,
  each = a checker-textured hard candy block:
  - **Grape** — purple checker (`Grape Hard Candy Block`)
  - **Minty** — teal checker (minty hard candy)
  - **Raspberry** — red checker (`Raspberry Hard Candy Block`)
  - **Aniseed** — white (`Aniseed Hard Candy Block`)
- Think giant lollipops of mixed flavors, planted at varied heights.

### 3c. Hard-candy bushes / boulders

- Low **1–3 block clusters** of a single hard-candy color sitting directly on the
  ground between the taller flora — the same four checker blocks as the canopies.
- Act as the biome's "rocks/bushes."

---

## 4. Recreation recipe (quick build order)

1. **Terrain:** lay a rolling field of **Cookie Block** (golden-top variant),
   1–3 block height variation, with flat cream paths winding through.
2. **Sky:** set the biome sky tint to **pink** (or, in a flat build, it's just
   ambience — no block needed).
3. **Ground cover:** scatter **candy-grass** tufts across the surface.
4. **Candy canes:** place ~8–12 tall red/white striped columns hooked at the top,
   in loose stands.
5. **Lollipop trees:** plant white stems of mixed height (2–8), each capped with a
   3×3×1 hard-candy disc — vary grape / mint / raspberry / aniseed.
6. **Bushes:** dot low 1–3 block hard-candy clusters between the trees.
7. **Populate:** gummy bears, polar bears, chocolate bunnies (see the entity
   catalog frames).

---

## 5. Block legend (biome → custom block)

| in biome | custom block | catalog frame |
|---|---|---|
| ground surface/subsurface | Cookie Block | `keyframes/block__cookie_block.png` |
| candy-cane skin | Candy Cane Block (red/white) | `keyframes/block__candy_cane_block.png` |
| red canopy/bush | Raspberry Hard Candy Block | `keyframes/block__raspberry_hard_candy_block.png` |
| purple canopy/bush | Grape Hard Candy Block | `keyframes/block__grape_hard_candy_block.png` |
| white stem/bush | Aniseed Hard Candy Block | `keyframes/block__aniseed_hard_candy_block.png` |
| teal canopy/bush | Minty Hard Candy Block | *(no clean solo frame; see `_JEI_INDEX.png`)* |

For the full custom-block family (all stairs/slabs/fences/doors/walls in each
flavor), see the JEI index crop: `keyframes/_JEI_INDEX.png`.

---

## 6. Reference stills

| file | shows |
|---|---|
| `biome__overview_candycane_forest.png` | macro layout — cane/lollipop forest density |
| `biome__candy_biome_wide.png` | wide candy biome, pink sky |
| `biome__candy_biome_pinksky.png` | ground + mixed lollipop canopies |
| `biome__lollipop_trees_closeup.png` | **best flora construction reference** (cane hook, lollipop discs, stems) |
| `biome__lollipop_candycane.png` | flora at eye level |
| `biome__cookie_ground.png` | cookie-block ground texture |
| `biome__terrain_edge.png` | **ground layering** — golden top vs brown sides, candy grass |
