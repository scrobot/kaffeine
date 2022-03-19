package com.scrobot.kaffeine.reactive.cache

import com.github.benmanes.caffeine.cache.Caffeine
import junit.framework.TestCase
import org.apache.commons.lang3.RandomStringUtils
import reactor.test.test

class ReactiveCaffeineCacheLoaderTest : TestCase() {

    private val reactiveCache: ReactiveCache<String, String> = ReactiveCaffeineCacheLoader(
        Caffeine.newBuilder().build()
    )

    fun testSingle() {
        val (key, value) = Pair(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(50))
        reactiveCache.put(key, value)

        reactiveCache.single(key)
            .test()
            .expectNext()
            .assertNext { it == value }
            .expectComplete()
            .verify()
    }

    fun testFetchAllPresent() {}

    fun testFetchAllPresentWithKeys() {}

    fun testFetchAll() {}

    fun testFetchAllWithKeys() {}

    fun testPut() {}

    fun testPutAll() {}

    fun testInvalidate() {}

    fun testInvalidateAll() {}

    fun testTestInvalidateAll() {}

    fun testEstimatedSize() {}

    fun testStats() {}

    fun testCleanUp() {}

    fun testPolicy() {}
}