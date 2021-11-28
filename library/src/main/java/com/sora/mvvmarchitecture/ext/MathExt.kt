package com.sora.mvvmarchitecture.ext

/**
 * 求两个数的最大公约数
 * @param other 另一个数
 * @return 最大公约数
 */
fun Int.getMaxGY(other: Int): Int {
    var m = this
    var n = other
    return if (m == n) {
        m
    } else {
        while (m % n != 0) {
            val temp = m % n
            m = n
            n = temp
        }
        n
    }
}