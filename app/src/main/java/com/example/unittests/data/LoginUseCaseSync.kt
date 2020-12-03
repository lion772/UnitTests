package com.example.unittests.data

import android.accounts.NetworkErrorException
import com.example.unittests.`interface`.AuthTokenCache
import com.example.unittests.`interface`.EventBusPoster
import com.example.unittests.`interface`.LoginHttpEndpointSync
import com.example.unittests.`interface`.LoginHttpEndpointSync.*


class LoginUseCaseSync(
    loginHttpEndpointSync: LoginHttpEndpointSync,
    authTokenCache: AuthTokenCache,
    eventBusPoster: EventBusPoster
) {
    enum class UseCaseResult {
        SUCCESS, FAILURE, NETWORK_ERROR
    }

    private var mLoginHttpEndpointSync: LoginHttpEndpointSync? = null
    private val mAuthTokenCache: AuthTokenCache
    private val mEventBusPoster: EventBusPoster
    fun loginSync(username: String, password: String): UseCaseResult {
        val endpointEndpointResult: EndpointResult? = try {
            mLoginHttpEndpointSync?.loginSync(username, password)
        } catch (e: NetworkErrorException) {
            return UseCaseResult.NETWORK_ERROR
        }
        return if (endpointEndpointResult?.let { isSuccessfulEndpointResult(it) } == true) {
            mAuthTokenCache.cacheAuthToken(endpointEndpointResult.authToken)
            mEventBusPoster.postEvent(LoggedInEvent())
            UseCaseResult.SUCCESS
        } else {
            UseCaseResult.FAILURE
        }
    }

    private fun isSuccessfulEndpointResult(endpointResult: EndpointResult): Boolean {
        return endpointResult.status === EndpointResultStatus.SUCCESS
    }

    init {
        mLoginHttpEndpointSync = loginHttpEndpointSync
        mAuthTokenCache = authTokenCache
        mEventBusPoster = eventBusPoster
    }
}
