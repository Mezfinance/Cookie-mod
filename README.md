# Cookie Mod — Reference

Art/asset reference for recreating the entities from a Cookie-Mod Minecraft
playthrough: one high-resolution frame per distinct **block**, **boss**, and
**mob** (plus supporting items and structures).

## Where things are

| path | what |
|---|---|
| [`reference/MECHANICS_SPEC.md`](reference/MECHANICS_SPEC.md) | Full spec of the custom mechanics — progression/ranks, soldier taming & commands, cookie economy, gummy/milk effects, bosses (scaling + milkman aura), crafting, farms — with timestamp citations. |
| [`reference/ENTITY_CATALOG.md`](reference/ENTITY_CATALOG.md) | The catalog — every entity, its frame, timestamp, and a confidence note. Start here. |
| [`reference/frames/keyframes/`](reference/frames/keyframes/) | 32 reference frames, named `<category>__<name>.png` (bosses/mobs/blocks/items/crafting/structures). |
| `reference/frames/keyframes/_CONTACT_SHEET.png` | Labelled visual index of all 32 frames. |
| `reference/frames/keyframes/_JEI_INDEX.png` | Master index of the custom block families (block/stairs/slab/door/etc.). |
| [`reference/BIOME_MAP.md`](reference/BIOME_MAP.md) | Block-by-block recreation guide for the cookie/candy biome, with stills in `reference/frames/biome/`. |
| [`reference/EXTRACT.md`](reference/EXTRACT.md) | The extraction pipeline — how the frames were produced and how to regenerate or extend them. |

## What's tracked vs. not

The curated keyframes are committed because they're the deliverable. The raw
video (~1.4 GB), the ~2328-frame scene survey (~2.4 GB), and the YouTube session
`cookies.txt` are **not** committed — the two large artifacts are regenerable per
`reference/EXTRACT.md`, and the cookies are a secret.

## Coverage

32 frames: 3 bosses (clean intro-card renders), 6 mobs, 11 blocks, 7 items, 2
crafting recipes, 3 structures — plus the JEI block-family index and the cookie
biome map. See the catalog's "Coverage & known gaps" for what the footage does
not cleanly provide (per-item icons beyond the tooltips, polar-bear colour
variants, a solo minty hard candy block).
