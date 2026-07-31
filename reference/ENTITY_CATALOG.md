# Cookie Mod — Entity Reference Catalog

High-resolution reference frames pulled from the source playthrough, one clean
frame per distinct **block**, **boss**, **mob**, plus **items**, **crafting
recipes**, and **structures**. These are art/asset reference — the intent is to
be able to recreate each entity from its frame.

- Frames live in [`frames/keyframes/`](frames/keyframes/).
- Visual index: [`frames/keyframes/_CONTACT_SHEET.png`](frames/keyframes/_CONTACT_SHEET.png).
- Custom-block family index (all stairs/slabs/doors/etc.): [`frames/keyframes/_JEI_INDEX.png`](frames/keyframes/_JEI_INDEX.png).
- Cookie-biome recreation guide: [`BIOME_MAP.md`](BIOME_MAP.md).
- How frames were produced: [`EXTRACT.md`](EXTRACT.md).
- `time` = timestamp in the source video. `conf` = how cleanly the entity is
  isolated (`high` clear, `med` visible but busy, `low` best available).

## Bosses

The three bosses use the game's own **intro title cards** — clean, isolated,
front-facing character renders, which beat any in-world action shot for
recreation reference.

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Cake Golem | `boss__cake_golem.png` | 0:17 | high | Intro card. Chocolate/waffle golem with a frosted-cookie head. |
| Waffle Mage | `boss__waffle_mage.png` | 0:18 | high | Intro card. Floating waffle-block limbs, white frosted body with red sprinkles. |
| Milkman | `boss__milkman.png` | 0:21 | high | Intro card. Full-body: dark head/white eyes, cream body, gold belt (9999 HP/ATK/DEF). |

## Mobs

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Waffle Guy | `mob__waffle_guy.png` | 3:34 | high | Common waffle enemy. |
| Chocolate Bunny | `mob__chocolate_bunny.png` | 29:26 | high | Dark-brown bunnies on the tower floor. |
| Gingerbread Man | `mob__gingerbread_man.png` | 24:16 | high | Nametagged; recruitable into a soldier. |
| Cookie Soldier | `mob__cookie_soldier.png` | 2:46 | high | Recruited friendly unit. |
| Gummy Bear | `mob__gummy_bear.png` | 2:02 | high | Green gummy creature (confirmed by the "KILL GUMMYBEAR" advancement). |
| Polar Bear | `mob__polar_bear.png` | 2:06 | med | Orange bear that drops gummy bears. Colour variants (blue/pink/radioactive) exist but aren't cleanly separable in the footage. |

## Blocks

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Cookie Block | `block__cookie_block.png` | 1:47 | high | Golden waffle top, brown cookie-chip sides. |
| Frosted Cookie Block | `block__frosted_cookie_block.png` | 16:30 | med | Light-topped cookie block (mistaken for sand in-video). |
| Waffle Block | `block__waffle_block.png` | 8:07 | high | |
| Chocolate Block | `block__chocolate_block.png` | 30:46 | high | Chocolate + waffle wall with "Waffle Block" nametag for scale. |
| Chocolate Brick Block | `block__chocolate_brick_block.png` | 8:13 | high | Shown as the block icon in the crafting UI. |
| Candy Cane Block | `block__candy_cane_block.png` | 16:36 | high | Red/white striped. |
| Candy / Lollipop Block | `block__candy_lollipop_block.png` | 2:18 | high | Lollipop-topped candy on sticks. |
| Raspberry Hard Candy Block | `block__raspberry_hard_candy_block.png` | 2:19 | high | Red checker. |
| Grape Hard Candy Block | `block__grape_hard_candy_block.png` | 31:12 | high | Purple checker; named tooltip in creative menu. |
| Aniseed Hard Candy Block | `block__aniseed_hard_candy_block.png` | 2:20 | med | White hard candy; busy candy-biome shot. |
| Milk Block | `block__milk_block.png` | 16:47 | high | White milk liquid flooding a field (milk is a fluid). |

> **Minty Hard Candy Block** (teal checker) has no clean solo frame but appears
> in `_JEI_INDEX.png` and throughout the biome flora. The JEI index also shows
> every block's full stairs/slab/fence/door/wall variant set.

## Items

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Holy Cookie | `item__holy_cookie.png` | 24:00 | high | Totem-like; tooltip visible. |
| Milky Sword | `item__milky_sword.png` | 42:55 | high | Final weapon; tooltip (18 atk). |
| Lollipop | `item__lollipop.png` | 6:33 | high | Weapon; tooltip (5 atk, 1.6 speed). |
| Apple Gum | `item__apple_gum.png` | 2:10 | high | Consumable; "stat boost when consumed" tooltip. |
| Cookie Milk Bucket | `item__cookie_milk_bucket.png` | 6:00 | high | Tooltip in crafting grid. |
| Raspberry-Peach Jelly Helmet | `item__raspberry_peach_jelly_helmet.png` | 26:47 | high | Jelly armor set (head): +20 KB resist, +4 armor. |
| Minty Jelly Chestplate | `item__minty_jelly_chestplate.png` | 37:24 | high | Jelly armor set (body): +20 KB resist, +8 armor. |

> The jelly/gummy armor set also includes grape-jelly leggings (seen in-inventory
> at 26:48 but never on a clean tooltip). Colored gummy bears (apple/green,
> orange, raspberry/red, grape/purple, minty) are visible in the crafting frames.

## Crafting recipes

| recipe | file | time | notes |
|---|---|---|---|
| Chocolate Block | `craft__chocolate_block.png` | 8:12 | Advancement + crafting UI. |
| Gummy Bears | `craft__gummy_bears.png` | 40:24 | Gummy crafting; inventory shows the full colored-gummy set. |

## Structures

| entity | file | time | conf | notes |
|---|---|---|---|---|
| Waffle Tower | `structure__waffle_tower.png` | 8:03 | med | Candy-biome approach / tower context. |
| Milkman's House | `structure__milkman_house.png` | 36:40 | high | Exterior. |
| Gingerbread Furnace | `structure__gingerbread_furnace.png` | 44:02 | high | Spawns gingerbread soldiers. |

---

### Coverage & known gaps

32 frames: 3 bosses, 6 mobs, 11 blocks, 7 items, 2 crafting recipes, 3
structures — plus the JEI block-family index and the biome map.

What the footage does **not** cleanly provide: an isolated icon for *every*
custom item (the video only lingers on the tooltips above), the individual
polar-bear colour variants, grape-jelly leggings on a clean tooltip, and a solo
Minty Hard Candy Block. The JEI index (`_JEI_INDEX.png`, page 1/12) is the best
available visual index for blocks beyond those with dedicated frames.
