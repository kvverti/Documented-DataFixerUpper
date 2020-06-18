// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.
package com.mojang.datafixers.kinds;

import com.mojang.datafixers.util.Either;

import java.util.function.Function;

/**
 * A {@link Traversable} functor that can convert values to and from a sum type. This type class defines two
 * methods, {@link #from(App)} and {@link #to(App)}, that convert the type {@code T} from and to the type
 * {@link Either}.
 *
 * @param <T>  The container type.
 * @param <C>  The right type of the either.
 * @param <Mu> The witness type for this functor.
 * @dfu.shape %.Type. %0
 * @see com.mojang.datafixers.optics.profunctors.Cocartesian
 */
public interface CocartesianLike<T extends K1, C, Mu extends CocartesianLike.Mu> extends Functor<T, Mu>, Traversable<T, Mu> {
    /**
     * Thunk method that casts an applied {@link CocartesianLike.Mu} to a {@link CocartesianLike}.
     *
     * @param proofBox The boxed value.
     * @param <F>      The container type.
     * @param <C>      A type contained in {@link F}.
     * @param <Mu>     The witness type of this functor.
     * @return The unboxed value.
     */
    static <F extends K1, C, Mu extends CocartesianLike.Mu> CocartesianLike<F, C, Mu> unbox(final App<Mu, F> proofBox) {
        return (CocartesianLike<F, C, Mu>) proofBox;
    }

    /**
     * The witness type of {@link CocartesianLike}.
     *
     * @dfu.shape %.Mu. %^1
     */
    interface Mu extends Functor.Mu, Traversable.Mu {}

    /**
     * Converts the given container to an {@link Either}.
     *
     * @param input The container.
     * @param <A>   The type of the contained values.
     * @return An {@link Either} representing an equivalent container.
     */
    <A> App<Either.Mu<C>, A> to(final App<T, A> input);

    /**
     * Converts the given {@link Either} to the container type.
     *
     * @param input The {@link Either}.
     * @param <A>   The type of the contained values.
     * @return A container equivalent to the input.
     */
    <A> App<T, A> from(final App<Either.Mu<C>, A> input);

    @Override
    default <F extends K1, A, B> App<F, App<T, B>> traverse(final Applicative<F, ?> applicative, final Function<A, App<F, B>> function, final App<T, A> input) {
        return applicative.map(this::from, new Either.Instance<C>().traverse(applicative, function, to(input)));
    }
}
