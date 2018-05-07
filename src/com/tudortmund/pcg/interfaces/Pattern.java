package com.tudortmund.pcg.interfaces;

import java.util.Objects;
import org.bukkit.Material;

/**
 * Represents an operation that accepts a single {@code int}-valued argument and
 * returns no result.  This is the primitive type specialization of
 * {@link Consumer} for {@code int}.  Unlike most other functional interfaces,
 * {@code IntConsumer} is expected to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(int)}.
 *
 * @see Consumer
 * @since 1.8
 */
@FunctionalInterface
public interface Pattern<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    Material accept(T value);
}
