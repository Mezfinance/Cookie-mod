# Reference Extraction Pipeline

How the reference frames in `frames/keyframes/` were produced from the source
playthrough, and how to regenerate or extend them.

## Source

- A single Cookie-Mod Minecraft playthrough video.
- Format: **1920×1080, 60 fps, ~44:55** (2694.9 s).
- Downloaded with `yt-dlp` to `reference/source.mp4` (gitignored — ~1.4 GB).
  Authenticated download uses `cookies.txt` (gitignored secret — never commit).

```bash
# reference/source.mp4  (not tracked)
yt-dlp --cookies cookies.txt -f "bestvideo[height<=1080]+bestaudio/best" \
  -o reference/source.mp4 "<VIDEO_URL>"
```

## Stage 1 — Scene survey (exploratory, gitignored)

A coarse pass to see everything the video contains, so entities and their
timestamps could be catalogued.

- Scene-cut detection dumped ~2328 representative frames to
  `reference/frames/scenes/` (gitignored — ~2.4 GB).
- Those were tiled into 8 contact sheets in
  `reference/frames/contact_sheets/` (gitignored) for quick eyeballing.

```bash
# per-scene frames on scene-change
ffmpeg -nostdin -i reference/source.mp4 \
  -vf "select='gt(scene,0.30)',showinfo" -vsync vfr -q:v 2 \
  reference/frames/scenes/scene_%04d.png
```

Both outputs are regenerable and intentionally excluded from git.

## Stage 2 — Entity catalog (the deliverable, tracked)

From the transcript + scene survey, each distinct block / boss / mob (plus key
items and structures) was mapped to the timestamp where it is most clearly on
screen. One full-resolution frame per entity is extracted with input-seek:

```bash
# one frame at an exact second, full 1920x1080, high quality
ffmpeg -nostdin -ss <SECONDS> -i reference/source.mp4 \
  -frames:v 1 -q:v 1 reference/frames/keyframes/<category>__<name>.png
```

Naming: `frames/keyframes/<category>__<name>.png`, where `category` ∈
`{boss, mob, block, item, structure}`.

### Verification loop

Extraction alone is not enough for recreation reference — a frame where the
entity is occluded or off-centre is useless. Each candidate was **visually
inspected**; frames that didn't cleanly show their entity were re-extracted from
alternate timestamps. Candidate timestamps that were tried per entity:

| entity | tried (s) | chosen (s) |
|---|---|---|
| boss__cake_golem | 899, 903, 912, 921 | 921 |
| boss__waffle_mage | 550, 1531, 1540, 1548 | 1540 |
| boss__milkman | 642, 2212, 2512, 2516, 2520 | 2520 |
| mob__chocolate_bunny | 526, 528, 534, 1766 | 1766 |
| mob__gingerbread_man | 232, 593, 596, 1456 | 1456 |
| mob__polar_bear_blue | 369, 371, 373, 377 | 369 |
| mob__polar_bear_radioactive | 115, 118, 121, 124 | 118 |
| block__candy_lollipop_block | 130, 136, 138, 2400 | 138 |
| block__chocolate_block | 1668, 1840, 1846, 2125 | 1846 |
| block__frosted_cookie_block | 977, 981, 984, 990 | 990 |
| block__milk_block | 1013, 1017, 1024, 1030 | 1017 |
| block__aniseed_hard_candy_block | 140, 143, 144, 2402 | 140 |
| block__raspberry_hard_candy_block | 139, 141, 142, 2404 | 139 |
| structure__waffle_tower | 478, 480, 483, 486 | 483 |
| structure__milkman_house | 2194, 2200, 2203, 2490 | 2200 |
| structure__gingerbread_furnace | 2592, 2596, 2600, 2642 | 2642 |

Entities not listed were clean on the first grab.

### Intro title cards (bosses)

The video's intro montage (~14–23 s) flashes an isolated title card for each
boss — a clean, front-facing render on a solid background. These beat in-world
action shots for recreation reference. Captured by fine-sampling the intro at
0.5 s steps to catch each card centred and sharp, then centre-cropping the card:

```bash
ffmpeg -nostdin -ss 17.0 -i reference/source.mp4 -frames:v 1 -q:v 1 \
  -vf "crop=860:1080:555:0" reference/frames/keyframes/boss__cake_golem.png
# waffle_mage @18.5s, milkman @21.0s
```

### Items, blocks & recipes (tooltips / crafting / JEI)

A coarse whole-video scan (one labelled thumb every 12 s → 8 montage sheets) was
used to locate every menu-open, item-tooltip, and crafting moment. Item/block
references come from those frames — a tooltip gives a clean name + icon, a
crafting UI gives the recipe. The survival inventory's JEI side-panel (page 1/12)
was cropped as a master index of the custom block families (block + stairs + slab
+ fence + door + wall in each flavour):

```bash
ffmpeg -nostdin -ss 1440 -i reference/source.mp4 -frames:v 1 -q:v 1 \
  -vf "crop=345:895:1575:80" reference/frames/keyframes/_JEI_INDEX.png
```

### Cookie-biome map

The same coarse scan located the cookie/candy biome (~1:48–4:00 and the pink-sky
candy biome throughout). Establishing, ground-detail, and flora-construction
stills were extracted to `frames/biome/` and read block-by-block into
[`BIOME_MAP.md`](BIOME_MAP.md).

### Contact sheet

A labelled 4×7 overview of all chosen frames:

```bash
# scale + label each frame, then tile
# (see git history for the exact drawtext/tile invocation)
ffmpeg -nostdin -pattern_type glob -i '<labelled thumbs>/*.png' \
  -vf "tile=4x7:padding=5:color=gray" -frames:v 1 \
  reference/frames/keyframes/_CONTACT_SHEET.png
```

## Extending / re-doing a frame

1. Pick the second you want from the source video.
2. Run the Stage-2 `ffmpeg` command with that `-ss` value and an
   `<category>__<name>.png` output.
3. Eyeball the result; nudge the timestamp ±a few seconds until the entity is
   clean.
4. Update the row in [`ENTITY_CATALOG.md`](ENTITY_CATALOG.md).

See the catalog's "Low-confidence frames" note for the four frames most worth a
manual re-pass.
