package com.scrobot.kaffeine.reactive.cache

import com.github.benmanes.caffeine.cache.Policy
import com.github.benmanes.caffeine.cache.stats.CacheStats
import com.google.errorprone.annotations.CheckReturnValue
import org.checkerframework.checker.index.qual.NonNegative
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

interface ReactiveCache<K, V> {

    fun single(key: K): Mono<V>

    fun fetchAllPresent(keys: Iterable<K>): Flux<V>

    fun fetchAllPresentWithKeys(keys: Iterable<K>): Flux<Tuple2<K, V>>

    fun fetchAll(): Flux<V>

    fun fetchAllWithKeys(): Flux<Tuple2<K, V>>

    fun put(key: K, value: V)

    fun putAll(map: Map<out K, V>)

    fun invalidate(key: K)

    fun invalidateAll(keys: Iterable<K>?)

    fun invalidateAll()

    @CheckReturnValue
    fun estimatedSize(): Mono<@NonNegative Long>

    @CheckReturnValue
    fun stats(): Mono<CacheStats>

    fun cleanUp()

    @CheckReturnValue
    fun policy(): Mono<Policy<K, V>>
}
