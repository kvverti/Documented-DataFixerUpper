// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.
package com.mojang.datafixers.types;

import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import java.util.Objects;
import java.util.function.Function;

public final class Func<A, B> extends Type<Function<A, B>> {
    protected final Type<A> first;
    protected final Type<B> second;

    public Func(final Type<A> first, final Type<B> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public TypeTemplate buildTemplate() {
        throw new UnsupportedOperationException("No template for function types.");
    }

    @Override
    public <T> DataResult<Pair<Function<A, B>, T>> read(final DynamicOps<T> ops, final T input) {
        return DataResult.error("Cannot read a function");
    }

    @Override
    public <T> DataResult<T> write(final DynamicOps<T> ops, final T rest, final Function<A, B> value) {
        return DataResult.error("Cannot save a function " + value, rest);
    }

    @Override
    public String toString() {
        return "(" + first + " -> " + second + ")";
    }

    @Override
    public boolean equals(final Object obj, final boolean ignoreRecursionPoints, final boolean checkIndex) {
        if (!(obj instanceof com.mojang.datafixers.types.Func<?, ?>)) {
            return false;
        }
        final com.mojang.datafixers.types.Func<?, ?> that = (com.mojang.datafixers.types.Func<?, ?>) obj;
        return first.equals(that.first, ignoreRecursionPoints, checkIndex) && second.equals(that.second, ignoreRecursionPoints, checkIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public Type<A> first() {
        return first;
    }

    public Type<B> second() {
        return second;
    }
}
