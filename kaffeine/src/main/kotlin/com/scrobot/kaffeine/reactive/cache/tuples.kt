package com.scrobot.kaffeine.reactive.cache

typealias Tuple2<T1, T2> = Pair<T1, T2>
typealias Tuple3<T1, T2, T3> = Triple<T1, T2, T3>

data class Tuple4<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)

fun <T1, T2> reactor.util.function.Tuple2<T1, T2>.toTuple(): Tuple2<T1, T2> = Tuple2(t1, t2)
fun <T1, T2, T3> reactor.util.function.Tuple3<T1, T2, T3>.toTuple(): Tuple3<T1, T2, T3> = Tuple3(t1, t2, t3)
fun <T1, T2, T3, T4> reactor.util.function.Tuple4<T1, T2, T3, T4>.toTuple(): Tuple4<T1, T2, T3, T4> =
    Tuple4(t1, t2, t3, t4)
