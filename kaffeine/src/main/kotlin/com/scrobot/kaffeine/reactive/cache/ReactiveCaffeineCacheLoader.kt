package com.scrobot.kaffeine.reactive.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Policy
import com.github.benmanes.caffeine.cache.stats.CacheStats
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import reactor.util.function.Tuples
import java.util.concurrent.ConcurrentSkipListSet

class ReactiveCaffeineCacheLoader<K, V>(
    private val cache: Cache<K, V>
) : ReactiveCache<K, V> {

    private val keyStore: Collection<K> = ConcurrentSkipListSet()

    override fun single(key: K): Mono<V> = Mono.just(key)
        .mapNotNull(cache::getIfPresent)

    override fun fetchAllPresent(keys: Iterable<K>): Flux<V> = Flux.fromIterable(keys)
        .flatMap(this::single)

    override fun fetchAllPresentWithKeys(keys: Iterable<K>): Flux<Tuple2<K, V>> = Flux.fromIterable(keys)
        .mapNotNull { key -> cache.getIfPresent(key)?.let { value -> Tuples.of(key, value) } }

    override fun fetchAll(): Flux<V> = fetchAllPresent(keyStore)

    override fun fetchAllWithKeys(): Flux<Tuple2<K, V>> = fetchAllPresentWithKeys(keyStore)

    override fun put(key: K, value: V) {
        keyStore.plusElement(key)
        cache.put(key, value)
    }

    override fun putAll(map: Map<out K, V>) {
        keyStore.plus(map.keys)
        cache.putAll(map)
    }

    override fun invalidate(key: K) {
        cache.invalidate(key)
    }

    override fun invalidateAll(keys: Iterable<K>?) {
        cache.invalidateAll(keys)
    }

    override fun invalidateAll() {
        cache.invalidateAll()
    }

    override fun estimatedSize(): Mono<Long> = Mono.just(cache.estimatedSize())

    override fun stats(): Mono<CacheStats> = Mono.just(cache.stats())

    override fun cleanUp() {
        cache.cleanUp()
    }

    override fun policy(): Mono<Policy<K, V>> = Mono.just(cache.policy())
}
