# Cookie Mod — Entity Reference Catalog

High-resolution (1920×1080) reference frames pulled from the source playthrough,
one clean frame per distinct **block**, **boss**, **mob**, plus supporting
**items** and **structures**. These are art/asset reference — the intent is to be
able to recreate each entity from its frame.

- Frames live in [`frames/keyframes/`](frames/keyframes/).
- Visual index: [`frames/keyframes/_CONTACT_SHEET.png`](frames/keyframes/_CONTACT_SHEET.png).
- How the frames were produced: [`EXTRACT.md`](EXTRACT.md).
- `time` = timestamp in the source video the frame was taken from.
- `conf` = how cleanly the entity is isolated in the frame
  (`high` = clear/centered, `med` = visible but busy/partial, `low` = best available, see notes).

## Bosses

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Cake Golem | `boss__cake_golem.png` | 15:21 | low | First boss. "Cake Golem" health bar present; the boss appears amid a marching candy/iron army by the arch, so the model is hard to isolate. Best candidate — see EXTRACT.md for alternates. |
| Waffle Mage | `boss__waffle_mage.png` | 25:40 | med | Second boss. "Waffle Mage" health bar visible; flying cake/waffle creature against the pink void. |
| Milkman | `boss__milkman.png` | 42:00 | high | Final boss. Full standing giant humanoid + "Milk Man" health bar. |

## Mobs

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Waffle Guy | `mob__waffle_guy.png` | 3:34 | high | Common waffle enemy, engaging the player. |
| Chocolate Bunny | `mob__chocolate_bunny.png` | 29:26 | high | Several dark-brown bunnies on the waffle-tower floor. |
| Gingerbread Man | `mob__gingerbread_man.png` | 24:16 | high | Nametagged, on farmland; recruitable into a soldier. |
| Cookie Soldier | `mob__cookie_soldier.png` | 2:46 | high | Recruited friendly unit. |
| Polar Bear (blue) | `mob__polar_bear_blue.png` | 6:09 | low | Gummy-bear-dropping bear; individual hard to isolate in the candy field. |
| Polar Bear (radioactive) | `mob__polar_bear_radioactive.png` | 1:58 | low | "Chernobyl"/radioactive variant; green-tinted creature, identification uncertain. |

## Blocks

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Cookie Block | `block__cookie_block.png` | 1:47 | high | |
| Frosted Cookie Block | `block__frosted_cookie_block.png` | 16:30 | med | Light-topped cookie block (mistaken for sand in-video). |
| Raspberry Hard Candy Block | `block__raspberry_hard_candy_block.png` | 2:19 | high | Red/crimson candy. |
| Aniseed Hard Candy Block | `block__aniseed_hard_candy_block.png` | 2:20 | med | White hard candy; not fully isolated in the candy biome. |
| Waffle Block | `block__waffle_block.png` | 8:07 | high | |
| Chocolate Brick Block | `block__chocolate_brick_block.png` | 8:13 | high | Shown as the block icon in the crafting UI ("Chocolate Block" advancement). |
| Chocolate Block | `block__chocolate_block.png` | 30:46 | high | Chocolate + waffle wall with the "Waffle Block" nametag for scale. |
| Candy Cane Block | `block__candy_cane_block.png` | 16:36 | high | Red/white striped block (castle build). |
| Candy / Lollipop Block | `block__candy_lollipop_block.png` | 2:18 | high | Lollipop-topped candy on sticks. |
| Milk Block | `block__milk_block.png` | 16:57 | low | Milk is a fluid (buggy in-video); no clean placed block exists — frame shows the "Cookie Milk Bucket" context. |

## Items

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Holy Cookie | `item__holy_cookie.png` | 24:00 | high | Totem-like item; inventory tooltip visible. |
| Milky Sword | `item__milky_sword.png` | 42:55 | high | Final weapon; tooltip (18 attack) visible. |
| Milk Bucket | `item__milk_bucket.png` | 5:58 | high | Crafting output (cookie milk bucket). |
| Lollipop (weapon) | `item__lollipop_weapon.png` | 6:25 | med | 5-attack lollipop; shown in soldiers' hands rather than as an isolated item. |

## Structures

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Waffle Tower | `structure__waffle_tower.png` | 8:03 | med | Candy-biome approach / tower exterior context. |
| Milkman's House | `structure__milkman_house.png` | 36:40 | high | Exterior. |
| Gingerbread Furnace | `structure__gingerbread_furnace.png` | 44:02 | high | Furnace that spawns gingerbread soldiers. |

---

### Low-confidence frames (candidates for a re-pass)

`boss__cake_golem`, `mob__polar_bear_blue`, `mob__polar_bear_radioactive`, and
`block__milk_block` are the weakest captures — the entity is either never cleanly
framed in the footage (the polar bears, the fluid "milk block") or is visually
merged into a crowd (the cake golem). If a cleaner recreation reference is needed
for these, the next step is a manual scrub of the timestamp windows noted in
`EXTRACT.md` rather than a single grabbed frame.
