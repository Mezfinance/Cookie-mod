# Cookie Mod — Reference

Art/asset reference for recreating the entities from a Cookie-Mod Minecraft
playthrough: one high-resolution frame per distinct **block**, **boss**, and
**mob** (plus supporting items and structures).

## Where things are

| path | what |
|---|---|
| [`reference/ENTITY_CATALOG.md`](reference/ENTITY_CATALOG.md) | The catalog — every entity, its frame, timestamp, and a confidence note. Start here. |
| [`reference/frames/keyframes/`](reference/frames/keyframes/) | 26 full-res (1920×1080) reference frames, named `<category>__<name>.png`. |
| `reference/frames/keyframes/_CONTACT_SHEET.png` | Labelled visual index of all 26 frames. |
| [`reference/EXTRACT.md`](reference/EXTRACT.md) | The extraction pipeline — how the frames were produced and how to regenerate or extend them. |

## What's tracked vs. not

The curated keyframes are committed because they're the deliverable. The raw
video (~1.4 GB), the ~2328-frame scene survey (~2.4 GB), and the YouTube session
`cookies.txt` are **not** committed — the two large artifacts are regenerable per
`reference/EXTRACT.md`, and the cookies are a secret.

## Coverage

26 entities: 3 bosses, 6 mobs, 10 blocks, 4 items, 3 structures. Four frames are
flagged low-confidence (the two polar bears, the fluid milk "block", and the cake
golem) — see the catalog for why and where to re-pass.
