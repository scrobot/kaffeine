package com.scrobot.kaffeine.spring.reactive.cache

import org.springframework.cache.Cache
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ReactiveCacheManager {
    fun getCache(name: String): Mono<Cache> {
        TODO("Not yet implemented")
    }

    fun subscribeToCache(name: String): Flux<Cache> {
        TODO("Not yet implemented")
    }

    fun getCacheNames(): Flux<String> {
        TODO("Not yet implemented")
    }
}
