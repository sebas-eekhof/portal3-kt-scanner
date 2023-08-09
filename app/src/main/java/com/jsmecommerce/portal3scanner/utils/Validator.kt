package com.jsmecommerce.portal3scanner.utils

import java.lang.Exception

class Validator(val string: String) {
    fun isValidEmail(): Boolean {
        return string.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"))
    }

    fun isValidBSN(): Boolean {
        try {
            var candidate = string.toInt()
            if (candidate <= 9999999 || candidate > 999999999) {
                return false
            }
            var sum = -1 * candidate % 10
            var multiplier = 2
            while (candidate > 0) {
                val `val` = 10.let { candidate /= it; candidate } % 10
                sum += multiplier * `val`
                multiplier++
            }
            return sum != 0 && sum % 11 == 0
        } catch(e: Exception) {
            return false
        }
    }
}