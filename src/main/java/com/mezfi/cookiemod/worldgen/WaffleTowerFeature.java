package com.mezfi.cookiemod.worldgen;

import com.mezfi.cookiemod.registry.ModBlocks;
import com.mezfi.cookiemod.registry.ModItems;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * The Waffle Tower — the raid dungeon of waffle-guy defenders with the Waffle Mage on top
 * (see {@code reference/WAFFLE_TOWER_SPEC.md}, MECHANICS_SPEC §8.3, §10).
 *
 * <p>A ~13×13 square tower of chocolate brick with golden waffle-block pilasters and
 * horizontal bands, white marshmallow corner columns and interior pillars, and a
 * checkered waffle/marshmallow floor on each of ~6 storeys around a central shaft. A
 * perimeter ladder links the floors; each floor carries loot chests, a purple bed and a
 * waffle-guy spawner. The roof is a battlemented deck; the Waffle Mage is released from a
 * hidden altar at its centre once the defenders are cleared.
 */
public class WaffleTowerFeature extends Feature<NoneFeatureConfiguration> {

    private static final int R = 6;            // half-width → 13×13 footprint
    private static final int STOREY = 5;       // vertical pitch between floors
    private static final int ROOMS = 6;        // interior storeys (floor levels 0..ROOMS)
    private static final int FLATNESS = 6;

    // Grid placement (as CakeGolemArenaFeature): one tower per SPACING×SPACING chunk cell.
    private static final int SPACING = 28;
    private static final int SEPARATION = 12;
    private static final long SALT = 0x0F177E12L;

    // Roof parapet screen (one side, top row first), 11 wide between the marshmallow corners.
    // M = marshmallow, w = waffle, l = waffle lattice, b/t = bottom/top waffle slab, . = gap.
    private static final String[] PARAPET = {
            "Mw.......wM",
            "Mlw.....wlM",
            "MllwbbbwllM",
            "Mlllw.wlllM",
            "MlllwtwlllM",
            "MMMMwMwMMMM",
    };

    public WaffleTowerFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        final int cx = origin.getX();
        final int cz = origin.getZ();
        final int y0 = origin.getY();                 // ground floor level
        final int deckY = y0 + STOREY * ROOMS;        // roof deck
        final int topY = deckY + 4;                   // clearance above the parapet

        if (!isChosenChunk(level.getSeed(), cx >> 4, cz >> 4)) {
            return false;
        }
        if (!isBuildable(level, cx, cz, y0)) {
            return false;
        }

        BlockState choc = ModBlocks.CHOCOLATE_BRICK_BLOCK.get().defaultBlockState();
        BlockState waffle = ModBlocks.WAFFLE_BLOCK.get().defaultBlockState();
        BlockState marsh = ModBlocks.MARSHMALLOW_BLOCK.get().defaultBlockState();
        BlockState red = ModBlocks.RASPBERRY_HARD_CANDY_BLOCK.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();

        // 1. Clear the build volume of terrain/flora and lay a chocolate-brick foundation.
        for (int dx = -R; dx <= R; dx++) {
            for (int dz = -R; dz <= R; dz++) {
                int x = cx + dx, z = cz + dz;
                for (int y = y0; y <= topY; y++) {
                    set(level, x, y, z, air);
                }
                for (int y = y0 - 1; y >= y0 - 4; y--) {
                    set(level, x, y, z, choc);
                }
            }
        }

        // 2. Walls: y0 .. deckY-1, with corner columns, waffle pilasters, floor-line bands,
        //    and the odd red-candy window in the chocolate field.
        for (int y = y0; y < deckY; y++) {
            boolean band = ((y - y0) % STOREY) == 0;      // golden band at each floor line
            boolean mid = ((y - y0) % STOREY) == 2;       // window height
            for (int dx = -R; dx <= R; dx++) {
                for (int dz = -R; dz <= R; dz++) {
                    if (Math.abs(dx) != R && Math.abs(dz) != R) continue; // perimeter only
                    BlockState b;
                    if (Math.abs(dx) == R && Math.abs(dz) == R) {
                        b = marsh;                                        // corner column
                    } else if (band || isPilaster(dx, dz)) {
                        b = waffle;                                       // band / pilaster
                    } else if (mid && Math.abs(along(dx, dz)) == 2) {
                        b = red;                                          // candy window
                    } else {
                        b = choc;
                    }
                    set(level, cx + dx, y, cz + dz, b);
                }
            }
        }
        // Corner columns continue up as marshmallow posts flanking the roof parapet.
        for (int cxi = -R; cxi <= R; cxi += 2 * R) {
            for (int czi = -R; czi <= R; czi += 2 * R) {
                for (int y = deckY; y <= deckY + 5; y++) {
                    set(level, cx + cxi, y, cz + czi, marsh);
                }
            }
        }

        // 3. Interior floors (k = 0 .. ROOMS-1): checkered waffle/marshmallow. The ground
        //    room is solid; the storeys above are donuts around a 3×3 central shaft. The
        //    ladder cell is always open. (The roof deck, k = ROOMS, is built in step 8.)
        for (int k = 0; k < ROOMS; k++) {
            int fy = y0 + STOREY * k;
            boolean solid = (k == 0);                     // only the ground room is filled
            for (int dx = -(R - 1); dx <= R - 1; dx++) {
                for (int dz = -(R - 1); dz <= R - 1; dz++) {
                    if (dx == -(R - 1) && dz == 0) continue;               // ladder passage
                    if (!solid && Math.abs(dx) <= 1 && Math.abs(dz) <= 1) continue; // shaft
                    BlockState tile = ((dx + dz) & 1) == 0 ? waffle : marsh;
                    set(level, cx + dx, fy, cz + dz, tile);
                }
            }
        }

        // 4. Interior marshmallow pillars at (±3,±3), full height.
        for (int px = -3; px <= 3; px += 6) {
            for (int pz = -3; pz <= 3; pz += 6) {
                for (int y = y0; y < deckY; y++) {
                    set(level, cx + px, y, cz + pz, marsh);
                }
            }
        }

        // 5. Perimeter ladder up the west wall (backed by the wall at dx=-R).
        BlockState ladder = Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.EAST);
        for (int y = y0 + 1; y <= deckY; y++) {
            set(level, cx - (R - 1), y, cz, ladder);
        }

        // 6. Ground-floor doorway in the south wall.
        set(level, cx, y0 + 1, cz + R, air);
        set(level, cx, y0 + 2, cz + R, air);

        // 7. Per-storey contents: a waffle-guy spawner, loot chests and a purple bed.
        BlockState spawner = ModBlocks.ARENA_SPAWNER.get().defaultBlockState();
        for (int k = 0; k < ROOMS; k++) {
            int fy = y0 + STOREY * k + 1;   // sitting on the floor tile
            set(level, cx + 2, fy, cz + 2, spawner);
            placeChest(level, cx - 3, fy, cz + 2, random);
            if ((k & 1) == 0) {
                placeChest(level, cx + 3, fy, cz - 2, random);
            }
            placeBed(level, cx - 4, fy, cz + 4, Direction.NORTH);
        }

        // 8. Roof: a solid waffle deck with a central shaft opening framed by a chocolate
        //    arch, and an ornate 6-tall parapet screen on each side (from the in-game view).
        int E = R - 1; // deck edge index (5)
        for (int dx = -E; dx <= E; dx++) {
            for (int dz = -E; dz <= E; dz++) {
                int cheb = Math.max(Math.abs(dx), Math.abs(dz));
                if (cheb <= 1) continue;                          // central shaft stays open
                if (dx == -E && dz == 0) continue;                // ladder passage
                set(level, cx + dx, deckY, cz + dz, cheb == 2 ? choc : waffle); // arch ring / deck
            }
        }
        // Parapet screens on the four sides, between the corner columns.
        BlockState slabB = ModBlocks.WAFFLE_SLAB.get().defaultBlockState();
        BlockState slabT = slabB.setValue(SlabBlock.TYPE, SlabType.TOP);
        BlockState fence = ModBlocks.WAFFLE_LATTICE.get().defaultBlockState();
        List<BlockPos> lattices = new ArrayList<>();
        for (int side = 0; side < 4; side++) {
            for (int i = 0; i < PARAPET.length; i++) {
                String row = PARAPET[i];
                int y = deckY + (PARAPET.length - 1 - i); // bottom row at deckY, top highest
                for (int j = 0; j < row.length(); j++) {
                    char sym = row.charAt(j);
                    if (sym == '.') continue;
                    int t = j - 5;                        // -5..+5 along the wall
                    int x, z;
                    switch (side) {
                        case 0 -> { x = cx + t; z = cz + R; }   // south
                        case 1 -> { x = cx + t; z = cz - R; }   // north
                        case 2 -> { x = cx + R; z = cz + t; }   // east
                        default -> { x = cx - R; z = cz + t; }  // west
                    }
                    BlockState b = switch (sym) {
                        case 'M' -> marsh;
                        case 'w' -> waffle;
                        case 'b' -> slabB;
                        case 't' -> slabT;
                        default -> fence;                       // 'l'
                    };
                    set(level, x, y, z, b);
                    if (sym == 'l') lattices.add(new BlockPos(x, y, z));
                }
            }
        }
        // Resolve lattice (fence) connections against their placed neighbours.
        for (BlockPos p : lattices) {
            BlockState f = level.getBlockState(p)
                    .setValue(BlockStateProperties.NORTH, connectable(level, p, Direction.NORTH))
                    .setValue(BlockStateProperties.EAST, connectable(level, p, Direction.EAST))
                    .setValue(BlockStateProperties.SOUTH, connectable(level, p, Direction.SOUTH))
                    .setValue(BlockStateProperties.WEST, connectable(level, p, Direction.WEST));
            level.setBlock(p, f, 2);
        }
        // Deck loot chests and the hidden Mage trigger under the central opening.
        placeChest(level, cx + (E - 1), deckY + 1, cz - 2, random);
        placeChest(level, cx - (E - 1), deckY + 1, cz + 2, random);
        placeChest(level, cx + 2, deckY + 1, cz + (E - 1), random);
        set(level, cx, deckY - 1, cz, ModBlocks.WAFFLE_MAGE_ALTAR.get().defaultBlockState());

        return true;
    }

    // --- helpers ---

    /** Along-wall coordinate for a perimeter cell (dz on the E/W walls, dx on the N/S walls). */
    private static int along(int dx, int dz) {
        return Math.abs(dx) == R ? dz : dx;
    }

    /** A lattice fence connects to a solid full face or to another fence. */
    private static boolean connectable(WorldGenLevel level, BlockPos p, Direction dir) {
        BlockPos np = p.relative(dir);
        BlockState ns = level.getBlockState(np);
        return ns.getBlock() instanceof FenceBlock || ns.isFaceSturdy(level, np, dir.getOpposite());
    }

    private static boolean isPilaster(int dx, int dz) {
        if (Math.abs(dx) == R && Math.abs(dz) == R) return false;
        int a = along(dx, dz);
        return a == -3 || a == 0 || a == 3;
    }

    private static void set(WorldGenLevel level, int x, int y, int z, BlockState state) {
        level.setBlock(new BlockPos(x, y, z), state, 2);
    }

    private static void placeChest(WorldGenLevel level, int x, int y, int z, RandomSource random) {
        BlockPos pos = new BlockPos(x, y, z);
        level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 2);
        if (level.getBlockEntity(pos) instanceof Container container) {
            Item[] pool = {
                    ModItems.WAFFLE.get(), ModItems.CHOCOLATE_BAR.get(), ModItems.COOKIE.get(),
                    ModItems.CANDY.get(), ModItems.LOLLIPOP.get()
            };
            int drops = 2 + random.nextInt(3);
            for (int i = 0; i < drops; i++) {
                int slot = random.nextInt(container.getContainerSize());
                Item item = pool[random.nextInt(pool.length)];
                container.setItem(slot, new ItemStack(item, 1 + random.nextInt(6)));
            }
            if (random.nextInt(3) == 0) {
                container.setItem(random.nextInt(container.getContainerSize()),
                        new ItemStack(Items.DIAMOND, 1 + random.nextInt(3)));
            }
        }
    }

    private static void placeBed(WorldGenLevel level, int x, int y, int z, Direction facing) {
        BlockState bed = Blocks.PURPLE_BED.defaultBlockState().setValue(BedBlock.FACING, facing);
        BlockPos foot = new BlockPos(x, y, z);
        BlockPos head = foot.relative(facing);
        level.setBlock(foot, bed.setValue(BedBlock.PART, BedPart.FOOT), 2);
        level.setBlock(head, bed.setValue(BedBlock.PART, BedPart.HEAD), 2);
    }

    private static boolean isBuildable(WorldGenLevel level, int cx, int cz, int y0) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[][] probes = {{0, 0}, {-R, -R}, {R, -R}, {-R, R}, {R, R}};
        for (int[] p : probes) {
            int h = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, cx + p[0], cz + p[1]);
            min = Math.min(min, h);
            max = Math.max(max, h);
            if (!level.getFluidState(new BlockPos(cx + p[0], h - 1, cz + p[1])).isEmpty()) {
                return false;
            }
        }
        return (max - min) <= FLATNESS && Math.abs(max - y0) <= FLATNESS;
    }

    private static boolean isChosenChunk(long seed, int chunkX, int chunkZ) {
        int cellX = Math.floorDiv(chunkX, SPACING);
        int cellZ = Math.floorDiv(chunkZ, SPACING);
        long h = splitmix(seed + SALT + cellX * 0x9E3779B97F4A7C15L + cellZ * 0xC2B2AE3D27D4EB4FL);
        int range = SPACING - SEPARATION;
        int offX = (int) Math.floorMod(h, range);
        int offZ = (int) Math.floorMod(h >>> 32, range);
        return chunkX == cellX * SPACING + offX && chunkZ == cellZ * SPACING + offZ;
    }

    private static long splitmix(long z) {
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }
}
