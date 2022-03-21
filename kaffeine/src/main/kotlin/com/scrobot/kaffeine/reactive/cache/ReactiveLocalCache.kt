package com.scrobot.kaffeine.reactive.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Policy
import com.github.benmanes.caffeine.cache.stats.CacheStats
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import reactor.util.function.Tuple2
import reactor.util.function.Tuples

class ReactiveLocalCache<K, V> : ReactiveCache<K, V>, ObservableCache<K, V> {

    private val store: Sinks.Many<Tuple2<K, V>> = Sinks.many().replay().all()

    override fun single(key: K): Mono<V> = store.asFlux()
        .filter { (k, _) -> k == key }
        .toMono()
        .map { (_, v) -> v }

    override fun fetchAllPresent(keys: Iterable<K>): Flux<V> = store.asFlux()
        .filter { (k, _) -> keys.contains(k) }
        .map { (_, v) -> v }

    override fun fetchAllPresentWithKeys(keys: Iterable<K>): Flux<Tuple2<K, V>> = store.asFlux()
        .filter { (k, _) -> keys.contains(k) }
        .map { (k, v) -> Tuples.of(k, v) }

    override fun fetchAll(): Flux<V> = store.asFlux().mapNotNull { (_, v) -> v }

    override fun fetchAllWithKeys(): Flux<Tuple2<K, V>> = store.asFlux().mapNotNull { (k, v) -> Tuples.of(k, v) }

    override fun put(key: K, value: V) {
        store.tryEmitNext(Tuples.of(key, value))
            .orThrow()
    }

    override fun putAll(map: Map<out K, V>) {
        map.forEach { (key, value) ->
            store.tryEmitNext(Tuples.of(key, value))
                .orThrow()
        }
    }

    override fun invalidate(key: K) {
        TODO("Not yet implemented")
    }

    override fun invalidateAll(keys: Iterable<K>?) {
        TODO("Not yet implemented")
    }

    override fun invalidateAll() {
        TODO("Not yet implemented")
    }

    override fun estimatedSize(): Mono<Long> {
        TODO("Not yet implemented")
    }

    override fun stats(): Mono<CacheStats> {
        TODO("Not yet implemented")
    }

    override fun cleanUp() {
        TODO("Not yet implemented")
    }

    override fun policy(): Mono<Policy<K, V>> {
        TODO("Not yet implemented")
    }

    override fun observeChanges(): Flux<Tuple2<K, V>> {
        TODO("Not yet implemented")
    }

    override fun observeKeyChanges(key: K): Flux<V> {
        TODO("Not yet implemented")
    }

    override fun observeCacheChanges(): Flux<Cache<K, V>> {
        TODO("Not yet implemented")
    }
}
