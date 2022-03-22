package com.scrobot.kaffeine.reactive.cache

import org.apache.commons.lang3.RandomStringUtils
import org.junit.Test
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.time.Duration
import kotlin.test.assertEquals

class ReactiveLocalCacheTest {

    private val reactiveCache = ReactiveLocalCache<String, String>()

    @Test
    fun `should return single result if key is exists`() {
        val (key, value) = "existing key" to "123"

        reactiveCache.put(key, value)

        reactiveCache.single(key)
            .log()
            .test()
            .assertNext { assertEquals(value, it) }
            .expectComplete()
            .verify(Duration.ofMillis(100))
    }

    @Test
    fun `should handle empty cache storage`() {
        reactiveCache.single("not existing key")
            .log()
            .switchIfEmpty(Mono.error(RuntimeException("Key is not exists")))
            .test()
            .expectError()
            .verify(Duration.ofMillis(100))
    }

    @Test
    fun `should return all fetching result`() {
        val kv = mapOf(
            "existing key 1" to "123",
            "existing key 2" to "321",
            "existing key 3" to "456",
        )

        reactiveCache.putAll(kv)

        reactiveCache.fetchAll()
            .log()
            .test()
            .expectNextCount(kv.size.toLong())
            .expectComplete()
            .verify(Duration.ofMillis(100))

        reactiveCache.put("test", RandomStringUtils.randomAlphabetic(5))

        reactiveCache.fetchAll()
            .log()
            .test()
            .expectNextCount(kv.size.toLong() + 1)
            .expectComplete()
            .verify(Duration.ofMillis(100))
    }
}
