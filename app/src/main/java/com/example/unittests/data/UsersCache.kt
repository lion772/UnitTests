package com.example.unittests.data

import androidx.annotation.Nullable

interface UsersCache {
    fun cacheUser(user: User?)

    @Nullable
    fun getUser(userId: String?): User?
}