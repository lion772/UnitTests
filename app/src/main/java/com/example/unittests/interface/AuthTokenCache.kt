package com.example.unittests.`interface`

interface AuthTokenCache {
    fun cacheAuthToken(authToken: String)
    val authToken: String?
}