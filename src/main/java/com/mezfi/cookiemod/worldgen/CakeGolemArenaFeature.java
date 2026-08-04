package com.mezfi.cookiemod.worldgen;

import com.mezfi.cookiemod.entity.CakeGolemEntity;
import com.mezfi.cookiemod.registry.ModBlocks;
import com.mezfi.cookiemod.registry.ModEntities;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
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
    private static final int COL_H = 6;              // column height
    private static final int FLATNESS = 6;           // max surface variance tolerated

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

        // Only build on reasonably flat, non-flooded ground.
        if (!isBuildable(level, cx, cz, y0)) {
            return false;
        }

        BlockState cookieSurface = ModBlocks.COOKIE_SURFACE.get().defaultBlockState();
        BlockState cookie = ModBlocks.COOKIE_BLOCK.get().defaultBlockState();
        BlockState frosted = ModBlocks.FROSTED_COOKIE_BLOCK.get().defaultBlockState();
        BlockState aniseed = ModBlocks.ANISEED_HARD_CANDY_BLOCK.get().defaultBlockState();
        BlockState cane = ModBlocks.CANDY_CANE_BLOCK.get().defaultBlockState();
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

        // 3. Columns: aniseed, y0..topY at each grid node.
        for (int gx : GRID) {
            for (int gz : GRID) {
                for (int y = y0; y <= topY; y++) {
                    level.setBlock(new BlockPos(cx + gx, y, cz + gz), aniseed, 2);
                }
            }
        }

        // 4. Beam lattice at topY: run aniseed along every grid line, both axes.
        for (int gz : GRID) {
            for (int dx = -R; dx <= R; dx++) {
                level.setBlock(new BlockPos(cx + dx, topY, cz + gz), aniseed, 2);
            }
        }
        for (int gx : GRID) {
            for (int dz = -R; dz <= R; dz++) {
                level.setBlock(new BlockPos(cx + gx, topY, cz + dz), aniseed, 2);
            }
        }

        // 5. Spawner-cage ring around the open centre (4 cages at ±3,±3).
        for (int sx = -3; sx <= 3; sx += 6) {
            for (int sz = -3; sz <= 3; sz += 6) {
                level.setBlock(new BlockPos(cx + sx, y0, cz + sz), spawner, 2);
            }
        }

        // 6. Candy-cane posts: a few bare 3–4 tall posts on open interior cells.
        int posts = 5 + random.nextInt(3);
        for (int i = 0; i < posts; i++) {
            int dx = random.nextInt(2 * R - 3) - (R - 2);
            int dz = random.nextInt(2 * R - 3) - (R - 2);
            if (!isOpenFloorCell(dx, dz)) continue;
            int h = 3 + random.nextInt(2);
            for (int y = 0; y < h; y++) {
                level.setBlock(new BlockPos(cx + dx, y0 + y, cz + dz), cane, 2);
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

        // 8. Spawn the Cake Golem on the open centre floor.
        spawnGolem(level, cx, cz, y0, random);
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

    private static void spawnGolem(WorldGenLevel level, int cx, int cz, int y0, RandomSource random) {
        CakeGolemEntity golem = ModEntities.CAKE_GOLEM.get().create(level.getLevel());
        if (golem == null) return;
        golem.moveTo(cx + 0.5D, y0, cz + 0.5D, random.nextFloat() * 360F, 0F);
        BlockPos at = new BlockPos(cx, y0, cz);
        golem.finalizeSpawn(level, level.getLevel().getCurrentDifficultyAt(at),
                MobSpawnType.STRUCTURE, null);
        level.addFreshEntityWithPassengers(golem);
    }
}
