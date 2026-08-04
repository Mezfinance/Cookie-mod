package com.mezfi.cookiemod.worldgen;

import com.mezfi.cookiemod.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * The Cake Golem boss arena (see {@code reference/CAKE_GOLEM_ARENA_SPEC.md}).
 *
 * <p>A candy colonnade: a 21×21 cookie pad carrying a 5×5 grid of white aniseed
 * columns under an open aniseed beam roof, with a ring of waffle-guy spawner
 * cages around an open centre where a Cake Golem spawns. Candy-cane posts and
 * oak-leaf hedges dress the floor. The checkerboard lollipop discs seen in the
 * footage are the surrounding cookie biome's flora — not built here.
 */
public class CakeGolemArenaFeature extends Feature<NoneFeatureConfiguration> {

    private static final int R = 10;                 // half-width → 21×21 pad
    private static final int[] GRID = {-10, -5, 0, 5, 10}; // column nodes (5-pitch)
    private static final int COL_H = 9;              // exterior column height (raised roof)
    private static final int FLATNESS = 6;           // max surface variance tolerated

    // Grid placement (mirrors vanilla RandomSpreadStructurePlacement): at most one arena per
    // SPACING×SPACING chunk cell, jittered within it, so arenas are spread out and never
    // adjacent. SEPARATION is the guaranteed minimum gap (in chunks) between neighbours.
    private static final int SPACING = 24;           // ~384-block grid → sparse
    private static final int SEPARATION = 10;        // ≥160-block minimum distance
    private static final long SALT = 0x0CA6E9A17L;   // distinct per-structure salt

    public CakeGolemArenaFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        final int cx = origin.getX();
        final int cz = origin.getZ();
        final int y0 = origin.getY();       // first air above the surface
        final int floorY = y0 - 1;          // pad top (overwritten)
        final int topY = y0 + COL_H - 1;    // beam height

        // One arena per grid cell, at a deterministic jittered chunk — enforces min distance.
        if (!isChosenChunk(level.getSeed(), cx >> 4, cz >> 4)) {
            return false;
        }

        // Only build on reasonably flat, non-flooded ground.
        if (!isBuildable(level, cx, cz, y0)) {
            return false;
        }

        BlockState cookieSurface = ModBlocks.COOKIE_SURFACE.get().defaultBlockState();
        BlockState cookie = ModBlocks.COOKIE_BLOCK.get().defaultBlockState();
        BlockState frosted = ModBlocks.FROSTED_COOKIE_BLOCK.get().defaultBlockState();
        BlockState aniseed = ModBlocks.ANISEED_HARD_CANDY_BLOCK.get().defaultBlockState();
        BlockState humbug = ModBlocks.HUMBUG_CANDY_BLOCK.get().defaultBlockState();
        BlockState spawner = ModBlocks.WAFFLE_GUY_SPAWNER.get().defaultBlockState();
        BlockState leaves = Blocks.OAK_LEAVES.defaultBlockState();

        // 1. Pad: 3-thick cookie foundation, cookie_surface top, cleared above.
        for (int dx = -R; dx <= R; dx++) {
            for (int dz = -R; dz <= R; dz++) {
                int x = cx + dx, z = cz + dz;
                level.setBlock(new BlockPos(x, floorY, z), cookieSurface, 2);
                level.setBlock(new BlockPos(x, floorY - 1, z), cookie, 2);
                level.setBlock(new BlockPos(x, floorY - 2, z), cookie, 2);
                for (int y = y0; y <= topY + 1; y++) {
                    level.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }

        // 2. Perimeter kerb: 1-high alternating cookie/frosted band on the edge,
        //    skipping the column nodes (which get full columns instead).
        for (int dx = -R; dx <= R; dx++) {
            for (int dz = -R; dz <= R; dz++) {
                boolean edge = Math.abs(dx) == R || Math.abs(dz) == R;
                if (!edge || (isNode(dx) && isNode(dz))) continue;
                BlockState k = ((dx + dz) & 1) == 0 ? frosted : cookie;
                level.setBlock(new BlockPos(cx + dx, y0, cz + dz), k, 2);
            }
        }

        // 3. Exterior columns only: aniseed, y0..topY at the PERIMETER grid nodes.
        for (int gx : GRID) {
            for (int gz : GRID) {
                if (!(Math.abs(gx) == R || Math.abs(gz) == R)) continue; // skip interior
                for (int y = y0; y <= topY; y++) {
                    level.setBlock(new BlockPos(cx + gx, y, cz + gz), aniseed, 2);
                }
            }
        }

        // 4. Roof: a perimeter frame of aniseed beams at topY joining the outer columns
        //    (the interior is left fully open to sky — no inner supports).
        for (int dx = -R; dx <= R; dx++) {
            level.setBlock(new BlockPos(cx + dx, topY, cz - R), aniseed, 2);
            level.setBlock(new BlockPos(cx + dx, topY, cz + R), aniseed, 2);
        }
        for (int dz = -R; dz <= R; dz++) {
            level.setBlock(new BlockPos(cx - R, topY, cz + dz), aniseed, 2);
            level.setBlock(new BlockPos(cx + R, topY, cz + dz), aniseed, 2);
        }

        // 5. Humbug pillars: black/red/white striped posts at the INTERIOR grid nodes
        //    (replacing the removed inner columns), framing the open centre. Slightly
        //    shorter than the roofline so they read as free-standing pillars.
        int humbugH = COL_H - 1;
        for (int gx : GRID) {
            for (int gz : GRID) {
                boolean interior = Math.abs(gx) != R && Math.abs(gz) != R;
                if (!interior || (gx == 0 && gz == 0)) continue; // keep the centre clear
                for (int y = 0; y < humbugH; y++) {
                    level.setBlock(new BlockPos(cx + gx, y0 + y, cz + gz), humbug, 2);
                }
            }
        }

        // 6. Spawner-cage ring around the open centre (4 cages at ±3,±3).
        for (int sx = -3; sx <= 3; sx += 6) {
            for (int sz = -3; sz <= 3; sz += 6) {
                level.setBlock(new BlockPos(cx + sx, y0, cz + sz), spawner, 2);
            }
        }

        // 7. Hedges: small oak-leaf clumps scattered on the floor.
        int hedges = 8 + random.nextInt(5);
        for (int i = 0; i < hedges; i++) {
            int dx = random.nextInt(2 * R - 3) - (R - 2);
            int dz = random.nextInt(2 * R - 3) - (R - 2);
            if (!isOpenFloorCell(dx, dz)) continue;
            level.setBlock(new BlockPos(cx + dx, y0, cz + dz), leaves, 2);
            if (random.nextBoolean()) {
                level.setBlock(new BlockPos(cx + dx, y0 + 1, cz + dz), leaves, 2);
            }
        }

        // 8. Bury the one-shot altar marker under the centre floor. It spawns the Cake Golem
        //    when a player first approaches (random-ticks only once the chunk is player-loaded),
        //    then reverts to cookie block — so the boss appears on arrival, not at worldgen.
        level.setBlock(new BlockPos(cx, y0 - 2, cz),
                ModBlocks.CAKE_GOLEM_ALTAR.get().defaultBlockState(), 2);
        return true;
    }

    /** True if the cell is interior, off the column grid, off the spawner ring, and clear of the centre. */
    private static boolean isOpenFloorCell(int dx, int dz) {
        if (Math.abs(dx) >= R || Math.abs(dz) >= R) return false;
        if (isNode(dx) && isNode(dz)) return false;                 // on a column
        if (Math.abs(dx) <= 3 && Math.abs(dz) <= 3) return false;   // spawner ring / fight circle
        return true;
    }

    private static boolean isNode(int d) {
        for (int g : GRID) if (g == d) return true;
        return false;
    }

    /** Surface must be flat enough and not underwater across the footprint corners + centre. */
    private static boolean isBuildable(WorldGenLevel level, int cx, int cz, int y0) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[][] probes = {{0, 0}, {-R, -R}, {R, -R}, {-R, R}, {R, R}};
        for (int[] p : probes) {
            int h = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, cx + p[0], cz + p[1]);
            min = Math.min(min, h);
            max = Math.max(max, h);
            if (!level.getFluidState(new BlockPos(cx + p[0], h - 1, cz + p[1])).isEmpty()) {
                return false; // corner in water
            }
        }
        return (max - min) <= FLATNESS && Math.abs(max - y0) <= FLATNESS;
    }

    /** True only for the single jittered chunk chosen within each SPACING×SPACING cell. */
    private static boolean isChosenChunk(long seed, int chunkX, int chunkZ) {
        int cellX = Math.floorDiv(chunkX, SPACING);
        int cellZ = Math.floorDiv(chunkZ, SPACING);
        long h = splitmix(seed + SALT + cellX * 0x9E3779B97F4A7C15L + cellZ * 0xC2B2AE3D27D4EB4FL);
        int range = SPACING - SEPARATION; // jitter window
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
