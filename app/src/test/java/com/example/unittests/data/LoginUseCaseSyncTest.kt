package com.example.unittests.data

import androidx.annotation.Nullable
import com.example.unittests.`interface`.AuthTokenCache
import com.example.unittests.`interface`.EventBusPoster
import com.example.unittests.`interface`.LoginHttpEndpointSync
import com.example.unittests.data.LoginUseCaseSync.*
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

class LoginUseCaseSyncTest {

    private lateinit var _loginHttpEndpointSyncTd: LoginHttpEndpointSyncTd
    private lateinit var _authTokenCachedTd: AuthTokenCacheTd
    private lateinit var _eventBusPosterTd: EventBusPosterTd
    private lateinit var SUT: LoginUseCaseSync

    @Before
    fun setUp() {
        _loginHttpEndpointSyncTd = LoginHttpEndpointSyncTd()
        _authTokenCachedTd = AuthTokenCacheTd()
        _eventBusPosterTd = EventBusPosterTd()
        SUT = LoginUseCaseSync(_loginHttpEndpointSyncTd, _authTokenCachedTd, _eventBusPosterTd)
    }

    @Test
    fun loginSync_success_usernameAndPasswordPassedToEndpoint() {
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_loginHttpEndpointSyncTd.username, Is(USERNAME))
        assertThat(_loginHttpEndpointSyncTd.password, Is(PASSWORD))
    }

    @Test
    fun loginSync_success_authTokenCached() {
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_authTokenCachedTd.authToken, Is(AUTH_TOKEN))
    }

    //If login fails - auth token is not changed
    @Test
    fun loginSync_generalError_authTokenNotCached() {
        _loginHttpEndpointSyncTd.isGeneralError = true
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_authTokenCachedTd.authToken, Is(""))
    }

    @Test
    fun loginSync_authError_authTokenNotCached() {
        _loginHttpEndpointSyncTd.isAuthError = true
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_authTokenCachedTd.authToken, Is(""))
    }

    @Test
    fun loginSync_serverError_authTokenNotCached() {
        _loginHttpEndpointSyncTd.isServerError = true
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_authTokenCachedTd.authToken, Is(""))
    }

    @Test
    fun loginSync_success_loggedInEventPosted() {
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_eventBusPosterTd.event, Is(instanceOf(LoggedInEvent::class.java)))
    }

    @Test
    fun loginSync_generalError_noInteractionWithEventBusPoster() {
        _loginHttpEndpointSyncTd.isGeneralError = true
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_eventBusPosterTd.interactionsCount, Is(0))
    }

    @Test
    fun loginSync_authError_noInteractionWithEventBusPoster() {
        _loginHttpEndpointSyncTd.isAuthError = true
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_eventBusPosterTd.interactionsCount, Is(0))
    }

    @Test
    fun loginSync_serverError_noInteractionWithEventBusPoster() {
        _loginHttpEndpointSyncTd.isServerError = true
        SUT.loginSync(USERNAME, PASSWORD)
        assertThat(_eventBusPosterTd.interactionsCount, Is(0))
    }

    @Test
    fun loginSync_success_successReturned() {
        val result: UseCaseResult = SUT.loginSync(USERNAME, PASSWORD)
        assertThat(result, Is(UseCaseResult.SUCCESS))
    }

    @Test
    fun loginSync_serverError_failureReturned() {
        _loginHttpEndpointSyncTd.isServerError = true
        val result: UseCaseResult = SUT.loginSync(USERNAME, PASSWORD)
        assertThat(result, Is(UseCaseResult.FAILURE))
    }

    @Test
    fun loginSync_authError_failureReturned() {
        _loginHttpEndpointSyncTd.isAuthError = true
        val result: UseCaseResult = SUT.loginSync(USERNAME, PASSWORD)
        assertThat(result, Is(UseCaseResult.FAILURE))
    }

    @Test
    fun loginSync_generalError_failureReturned() {
        _loginHttpEndpointSyncTd.isGeneralError = true
        val result: UseCaseResult = SUT.loginSync(USERNAME, PASSWORD)
        assertThat(result, Is(UseCaseResult.FAILURE))
    }

    //Network - network error returned
    @Test
    fun loginSync_networkError_networkErrorReturned() {
        _loginHttpEndpointSyncTd.isNetworkError = true
        val result: UseCaseResult = SUT.loginSync(USERNAME, PASSWORD)
        assertThat(result, Is(UseCaseResult.FAILURE))
    }

    private class LoginHttpEndpointSyncTd : LoginHttpEndpointSync {
        var username: String = ""
        var password: String = ""
        var isGeneralError: Boolean = true
        var isAuthError: Boolean = true
        var isServerError: Boolean = true
        var isNetworkError: Boolean = true

        override fun loginSync(
            username: String,
            password: String
        ): LoginHttpEndpointSync.EndpointResult {

           if (username.isNotEmpty()) {
                this.username = username
            }
            if (password.isNotEmpty()) {
                this.password = password
            }
            return when {
                isGeneralError -> { LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR, "")}
                isAuthError -> { LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR, "")}
                isServerError -> { LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.SERVER_ERROR, "")}
                isNetworkError -> { LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.NETWORK_ERROR, "") }
                else -> { LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.SUCCESS, AUTH_TOKEN) }
            }
        }
    }

    private class AuthTokenCacheTd : AuthTokenCache {
        var authTokenStr: String = ""

        override fun cacheAuthToken(authToken: String) {
            this.authTokenStr = authToken
        }

        override val authToken = authTokenStr

    }

    private class EventBusPosterTd : EventBusPoster {
        var event: Any? = null
        var interactionsCount: Int = 0
        override fun postEvent(event: Any) {
            interactionsCount++
            this.event = event
        }

    }


    companion object {
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val AUTH_TOKEN = "authToken"
    }
}