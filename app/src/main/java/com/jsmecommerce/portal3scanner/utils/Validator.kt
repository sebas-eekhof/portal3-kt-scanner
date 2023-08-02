package com.jsmecommerce.portal3scanner.utils

class Validator(val string: String) {
    fun isValidEmail(): Boolean {
        return string.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"))
    }
}