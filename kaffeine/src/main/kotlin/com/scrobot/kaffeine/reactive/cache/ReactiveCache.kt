package com.scrobot.kaffeine.reactive.cache

import com.github.benmanes.caffeine.cache.Policy
import com.github.benmanes.caffeine.cache.stats.CacheStats
import com.google.errorprone.annotations.CheckReturnValue
import org.checkerframework.checker.index.qual.NonNegative
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.Function

interface ReactiveCache<K, V> {

    fun single(key: K): Mono<V>

    fun single(key: K, mappingFunction: Function<in K, out V>?): Mono<V>

    fun fetchAllPresent(keys: Iterable<K>): Flux<V>

    fun fetchAllPresentWithKeys(keys: Iterable<K>): Flux<Tuple2<K, V>>

    fun fetchAll(
        keys: Iterable<K>,
        mappingFunction: Function<in MutableSet<out K>?, out Map<out K, V>?>?
    ): Flux<V>

    fun getAllWithKeys(
        keys: Iterable<K>?,
        mappingFunction: Function<in MutableSet<out K>?, out Map<out K, V>?>?
    ): Flux<Tuple2<K, V>>

    fun put(key: K, value: V)

    fun putAll(map: Map<out K, V>?)

    fun invalidate(key: K)

    fun invalidateAll(keys: Iterable<K>?)

    fun invalidateAll()

    @CheckReturnValue
    fun estimatedSize(): @NonNegative Long

    @CheckReturnValue
    fun stats(): Mono<CacheStats>

    fun cleanUp()

    @CheckReturnValue
    fun policy(): Mono<Policy<K, V>>
}
