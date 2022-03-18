package com.scrobot.kaffeine.reactive.cache

import com.github.benmanes.caffeine.cache.Cache
import reactor.core.publisher.Flux

interface ObservableCache<K, V> : ReactiveCache<K, V> {

    fun observeChanges(): Flux<Tuple2<K, V>>

    fun observeKeyChanges(key: K): Flux<V>

    fun observeCacheChanges(): Flux<Cache<K, V>>
}
