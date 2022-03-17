package com.scrobot.kaffeine.spring.reactive.cache

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager

class ReactiveBlockingCacheManager : CacheManager {
    override fun getCache(name: String): Cache? {
        TODO("Not yet implemented")
    }

    override fun getCacheNames(): Collection<String> {
        TODO("Not yet implemented")
    }
}
