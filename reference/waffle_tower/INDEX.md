# Waffle Tower — reference stills

Per-second stills of the **Waffle Tower** raid, extracted from the source
playthrough (video `sgEkKc10RlI`) at full 1080p, one frame per second.

The tower is discovered/raided on **Day 8–9**, at video time **~7:48–9:24**
(`t≈468–564 s`). The game flashes a **"✅ WAFFLE TOWER"** structure-discovery
card at `t≈484`, and the **Waffle Mage** boss bar appears at `t≈548` when the
player looks up ("is that his … up there?") — confirming the Mage sits at the
**top** of the tower.

## Files

- `frames/t_<sec>.jpg` — one still per second, `t=468 … t=564`. Each frame has
  its timestamp burned into the top-left corner (`t=NNNs (M:SS)`).
- `CONTACT_SHEET.jpg` — all 97 frames tiled (8 cols, row-major from t=468).
- `images/exterior_establishing.png` — the three "WAFFLE TOWER" title-card
  frames (t=484–486) side by side: the clearest exterior look.

## Timeline / sections

| range (s) | video time | section | what it shows |
|---|---|---|---|
| 468–483 | 7:48–8:03 | approach | fighting waffle guys across the candy biome toward the tower |
| 484–487 | 8:04–8:07 | **exterior** | "WAFFLE TOWER" card — tall tower: golden **waffle-block** vertical ribs, **chocolate-brick** wall infill, marshmallow/lollipop stems around the base, pink sky |
| 488–497 | 8:08–8:17 | entry | climbing/breaking in; Waffle Block + Chocolate Block (→ Choco Bricks) pickups |
| 498–527 | 8:18–8:47 | **interior — lower** | waffle-block walls, chocolate-brick accents, orange-&-white checkered waffle floors, white columns, red hard-candy blocks, loot **chests** (Splash Potion of Healing, Lollipop), waffle-guy defenders |
| 528–547 | 8:48–9:07 | **interior — upper** | "DIAMONDS!" large chest, white pillars, checkered floors, purple beds, more waffle guys, stairs/ladders climbing up |
| 548–559 | 9:08–9:19 | **top / Mage** | Waffle Mage boss bar appears; player looks up at the top of the tower |
| 560–564 | 9:20–9:24 | exit | back out onto the candy biome (Day 9) |

## Notes for the build

- **Palette:** waffle block (golden grid), chocolate brick (dark brown grid),
  chocolate block, orange/white checkered waffle floor, white marshmallow
  columns, red/pink hard-candy accents.
- **Contents:** loot chests (regular + large), waffle-guy defenders throughout,
  purple beds, interior stairs/ladders spanning multiple floors.
- **Layout:** a tall multi-floor tower; Mage spawns/sits at the top once the
  defenders are cleared (matches MECHANICS_SPEC §8.3).

## Regenerating / extending

Frames came from `--download-sections "*468-565"` at format 299 (1080p60), then
`ffmpeg -vf fps=1`. Later tower raids (the player revisits towers for waffle
blocks) appear elsewhere in the video and could be added the same way if more
exterior/interior angles are wanted.
