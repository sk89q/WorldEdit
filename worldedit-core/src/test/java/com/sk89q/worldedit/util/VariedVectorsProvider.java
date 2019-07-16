package com.sk89q.worldedit.util;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.sk89q.worldedit.math.BlockVector3;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Argument provider for various vectors.
 */
public final class VariedVectorsProvider implements ArgumentsProvider, AnnotationConsumer<VariedVectorsProvider.Test> {

    @Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @ArgumentsSource(VariedVectorsProvider.class)
    @ParameterizedTest(name = ParameterizedTest.ARGUMENTS_PLACEHOLDER)
    public @interface Test {

        /**
         * If {@code true}, provide a non-matching vector from
         * the existing vectors set as well. This will nearly
         * square the number of tests executed, since it will
         * test <em>every</em> non-matching vector.
         */
        boolean provideNonMatching() default false;

    }

    private static final int WORLD_XZ_MINMAX = 30_000_000;
    private static final int WORLD_Y_MAX = 4095;

    // For better coverage assurance, increase these values for a local Gradle run.
    // Don't do it for IntelliJ, it'll probably run out of memory.
    private static final int DIVISIONS_XZ = Integer.getInteger("ordered.block.divisions.xz", 4);
    private static final int DIVISIONS_Y = Integer.getInteger("ordered.block.divisions.y", 3);

    private static final int XZ_STEP = (WORLD_XZ_MINMAX * 2) / DIVISIONS_XZ;
    private static final int Y_STEP = WORLD_Y_MAX / DIVISIONS_Y;

    private static final Set<BlockVector3> ALWAYS_INCLUDE =
        ImmutableSet.of(BlockVector3.ZERO, BlockVector3.ONE,
            BlockVector3.at(-WORLD_XZ_MINMAX, 0, -WORLD_XZ_MINMAX),
            BlockVector3.at(WORLD_XZ_MINMAX, 0, WORLD_XZ_MINMAX),
            BlockVector3.at(-WORLD_XZ_MINMAX, WORLD_Y_MAX, -WORLD_XZ_MINMAX),
            BlockVector3.at(WORLD_XZ_MINMAX, WORLD_Y_MAX, WORLD_XZ_MINMAX));

    private boolean provideNonMatching;

    @Override
    public void accept(Test test) {
        provideNonMatching = test.provideNonMatching();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        if (provideNonMatching) {
            return makeVectorsStream()
                .flatMap(vec -> makeVectorsStream().filter(v -> !v.equals(vec))
                    .map(v -> Arguments.of(vec, v)));
        }
        return makeVectorsStream().map(Arguments::of);
    }

    public static Stream<BlockVector3> makeVectorsStream() {
        return Stream.concat(
            ALWAYS_INCLUDE.stream(),
            Streams.stream(generateVectors()).filter(v -> !ALWAYS_INCLUDE.contains(v))
        );
    }

    private static Iterator<BlockVector3> generateVectors() {
        return new AbstractIterator<BlockVector3>() {

            private int x = -WORLD_XZ_MINMAX + 1;
            private int z = -WORLD_XZ_MINMAX + 1;
            private int y = 0;

            @Override
            protected BlockVector3 computeNext() {
                if (x > WORLD_XZ_MINMAX) {
                    return endOfData();
                }
                BlockVector3 newVector = BlockVector3.at(x, y, z);
                y += Y_STEP;
                if (y > WORLD_Y_MAX) {
                    y = 0;
                    z += XZ_STEP;
                    if (z > WORLD_XZ_MINMAX) {
                        z = -WORLD_XZ_MINMAX;
                        x += XZ_STEP;
                    }
                }
                return newVector;
            }
        };
    }
}
