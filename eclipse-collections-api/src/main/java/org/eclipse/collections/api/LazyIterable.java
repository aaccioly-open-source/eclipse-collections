/*
 * Copyright (c) 2021 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api;

import java.util.Collection;

import org.eclipse.collections.api.annotation.category.Converting;
import org.eclipse.collections.api.annotation.category.Filtering;
import org.eclipse.collections.api.annotation.category.Finding;
import org.eclipse.collections.api.annotation.category.Grouping;
import org.eclipse.collections.api.annotation.category.Iterating;
import org.eclipse.collections.api.annotation.category.Transforming;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.api.block.function.primitive.ByteFunction;
import org.eclipse.collections.api.block.function.primitive.CharFunction;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatFunction;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.block.function.primitive.LongFunction;
import org.eclipse.collections.api.block.function.primitive.ShortFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.ordered.OrderedIterable;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;

/**
 * A LazyIterable is RichIterable which will defer evaluation for certain methods like select, reject, collect, etc.
 * Any methods that do not return a LazyIterable when called will cause evaluation to be forced.
 * <p>
 * The methods in LazyIterable are organized into the following method categories via region comments which are collapsible
 * in various IDEs.
 * Links are provided below as a convenience to help discover specific methods in Javadoc.
 *
 * <ul>
 * <li><b>Converting 🔌</b>
 * <ul><li>
 * {@link #concatenate(Iterable)}, {@link #into(Collection)}, {@link #toArray()}, {@link #toArray(Object[])},
 * {@link #toImmutableBag()}, {@link #toImmutableList()}, {@link #toImmutableSet()}
 * </li></ul>
 * <li><b>Filtering 🚰</b>
 * <ul><li>
 * {@link #distinct()}, {@link #drop(int)}, {@link #dropWhile(Predicate)}, {@link #reject(Predicate)},
 * {@link #rejectWith(Predicate2, Object)}, {@link #select(Predicate)}, {@link #selectInstancesOf(Class)},
 * {@link #selectWith(Predicate2, Object)}, {@link #take(int)}, {@link #takeWhile(Predicate)}
 * </li></ul>
 * <li><b>Finding 🔎</b>
 * <ul><li>
 * {@link #getFirst()}
 * </li></ul>
 * <li><b>Grouping 🏘️</b>
 * <ul><li>
 * {@link #chunk(int)}
 * </li></ul>
 * <li><b>Iterating 🔄</b>
 * <ul><li>
 * {@link #tap(Procedure)}
 * </li></ul>
 * <li><b>Transforming 🦋</b>
 * <ul><li>
 * {@link #collect(Function)}, {@link #collectBoolean(BooleanFunction)}, {@link #collectByte(ByteFunction)},
 * {@link #collectChar(CharFunction)}, {@link #collectDouble(DoubleFunction)}, {@link #collectFloat(FloatFunction)},
 * {@link #collectIf(Predicate, Function)}, {@link #collectInt(IntFunction)}, {@link #collectLong(LongFunction)},
 * {@link #collectShort(ShortFunction)}, {@link #collectWith(Function2, Object)}, {@link #flatCollect(Function)},
 * {@link #flatCollectWith(Function2, Object)}, {@link #zip(Iterable)}, {@link #zipWithIndex()}
 * </li></ul>
 * </ul>
 *
 * @since 1.0
 */
public interface LazyIterable<T>
        extends RichIterable<T>
{
    //region [Category: Converting] 🔌

    /**
     * Creates a deferred iterable that will join this iterable with the specified iterable.
     */
    @Converting
    LazyIterable<T> concatenate(Iterable<T> iterable);

    /**
     * Iterates over this iterable adding all elements into the target collection.
     */
    @Converting
    @Override
    <R extends Collection<T>> R into(R target);

    @Converting
    @Override
    default <E> E[] toArray(E[] array)
    {
        MutableList<T> mutableList = Lists.mutable.empty();
        this.forEach(mutableList::add);
        return mutableList.toArray(array);
    }

    @Converting
    @Override
    default Object[] toArray()
    {
        MutableList<T> mutableList = Lists.mutable.empty();
        this.forEach(mutableList::add);
        return mutableList.toArray();
    }

    @Converting
    @Override
    default ImmutableBag<T> toImmutableBag()
    {
        MutableBag<T> mutableBag = Bags.mutable.empty();
        this.forEach(mutableBag::add);
        return mutableBag.toImmutable();
    }

    @Converting
    @Override
    default ImmutableList<T> toImmutableList()
    {
        MutableList<T> mutableList = Lists.mutable.empty();
        this.forEach(mutableList::add);
        return mutableList.toImmutable();
    }

    @Converting
    @Override
    default ImmutableSet<T> toImmutableSet()
    {
        MutableSet<T> mutableSet = Sets.mutable.empty();
        this.forEach(mutableSet::add);
        return mutableSet.toImmutable();
    }

    //endregion [Category: Converting] 🔌

    //region [Category: Filtering] 🚰

    /**
     * Creates a deferred distinct iterable to get distinct elements from the current iterable.
     *
     * @since 5.0
     */
    @Filtering
    LazyIterable<T> distinct();

    /**
     * Creates a deferred drop iterable for the current iterable using the specified count as the limit.
     */
    @Filtering
    LazyIterable<T> drop(int count);

    /**
     * @see OrderedIterable#dropWhile(Predicate)
     * @since 8.0
     */
    @Filtering
    LazyIterable<T> dropWhile(Predicate<? super T> predicate);

    /**
     * Creates a deferred iterable for rejecting elements from the current iterable.
     */
    @Filtering
    @Override
    LazyIterable<T> reject(Predicate<? super T> predicate);

    @Filtering
    @Override
    <P> LazyIterable<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    /**
     * Creates a deferred iterable for selecting elements from the current iterable.
     */
    @Filtering
    @Override
    LazyIterable<T> select(Predicate<? super T> predicate);

    @Filtering
    @Override
    <S> LazyIterable<S> selectInstancesOf(Class<S> clazz);

    @Filtering
    @Override
    <P> LazyIterable<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    /**
     * Creates a deferred take iterable for the current iterable using the specified count as the limit.
     */
    @Filtering
    LazyIterable<T> take(int count);

    /**
     * @see OrderedIterable#takeWhile(Predicate)
     * @since 8.0
     */
    @Filtering
    LazyIterable<T> takeWhile(Predicate<? super T> predicate);

    //endregion [Category: Filtering] 🚰

    //region [Category: Finding] 🔎

    @Finding
    @Override
    T getFirst();

    //endregion [Category: Finding] 🔎

    //region [Category: Grouping] 🏘️

    /**
     * Creates a deferred chunk iterable.
     */
    @Grouping
    @Override
    LazyIterable<RichIterable<T>> chunk(int size);

    //endregion [Category: Grouping] 🏘️

    //region [Category: Iterating] 🔄

    /**
     * Creates a deferred tap iterable.
     */
    @Iterating
    @Override
    LazyIterable<T> tap(Procedure<? super T> procedure);

    //endregion [Category: Iterating] 🔄

    //region [Category: Transforming] 🦋

    /**
     * Creates a deferred iterable for collecting elements from the current iterable.
     */
    @Transforming
    @Override
    <V> LazyIterable<V> collect(Function<? super T, ? extends V> function);

    /**
     * Returns a lazy BooleanIterable which will transform the underlying iterable data to boolean values based on the booleanFunction.
     */
    @Transforming
    @Override
    LazyBooleanIterable collectBoolean(BooleanFunction<? super T> booleanFunction);

    /**
     * Returns a lazy ByteIterable which will transform the underlying iterable data to byte values based on the byteFunction.
     */
    @Transforming
    @Override
    LazyByteIterable collectByte(ByteFunction<? super T> byteFunction);

    /**
     * Returns a lazy CharIterable which will transform the underlying iterable data to char values based on the charFunction.
     */
    @Transforming
    @Override
    LazyCharIterable collectChar(CharFunction<? super T> charFunction);

    /**
     * Returns a lazy DoubleIterable which will transform the underlying iterable data to double values based on the doubleFunction.
     */
    @Transforming
    @Override
    LazyDoubleIterable collectDouble(DoubleFunction<? super T> doubleFunction);

    /**
     * Returns a lazy FloatIterable which will transform the underlying iterable data to float values based on the floatFunction.
     */
    @Transforming
    @Override
    LazyFloatIterable collectFloat(FloatFunction<? super T> floatFunction);

    /**
     * Creates a deferred iterable for selecting and collecting elements from the current iterable.
     */
    @Transforming
    @Override
    <V> LazyIterable<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);

    /**
     * Returns a lazy IntIterable which will transform the underlying iterable data to int values based on the intFunction.
     */
    @Transforming
    @Override
    LazyIntIterable collectInt(IntFunction<? super T> intFunction);

    /**
     * Returns a lazy LongIterable which will transform the underlying iterable data to long values based on the longFunction.
     */
    @Transforming
    @Override
    LazyLongIterable collectLong(LongFunction<? super T> longFunction);

    /**
     * Returns a lazy ShortIterable which will transform the underlying iterable data to short values based on the shortFunction.
     */
    @Transforming
    @Override
    LazyShortIterable collectShort(ShortFunction<? super T> shortFunction);

    @Transforming
    @Override
    <P, V> LazyIterable<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    /**
     * Creates a deferred flattening iterable for the current iterable.
     */
    @Transforming
    @Override
    <V> LazyIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);

    /**
     * @since 9.2
     */
    @Transforming
    @Override
    default <P, V> LazyIterable<V> flatCollectWith(Function2<? super T, ? super P, ? extends Iterable<V>> function, P parameter)
    {
        return this.flatCollect(each -> function.apply(each, parameter));
    }

    /**
     * Creates a deferred zip iterable.
     */
    @Transforming
    @Override
    <S> LazyIterable<Pair<T, S>> zip(Iterable<S> that);

    /**
     * Creates a deferred zipWithIndex iterable.
     */
    @Transforming
    @Override
    LazyIterable<Pair<T, Integer>> zipWithIndex();

    //endregion [Category: Transforming] 🦋
}
