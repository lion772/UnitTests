package com.example.unittests.data

import android.accounts.NetworkErrorException


class FetchUserProfileUseCaseSync {

    enum class UseCaseResult {
        SUCCESS, FAILURE, NETWORK_ERROR
    }

    private var mUserProfileHttpEndpointSync: UserProfileHttpEndpointSync? = null
    private var mUsersCache: UsersCache? = null

    fun FetchUserProfileUseCaseSync(
        userProfileHttpEndpointSync: UserProfileHttpEndpointSync?,
        usersCache: UsersCache?
    ) {
        mUserProfileHttpEndpointSync = userProfileHttpEndpointSync
        mUsersCache = usersCache
    }

    fun fetchUserProfileSync(userId: String?): UseCaseResult {
        var endpointResult: UserProfileHttpEndpointSync.EndpointResult? = null
        try {
            // the bug here is that userId is not passed to endpoint
            mUserProfileHttpEndpointSync?.let {
                endpointResult = it.getUserProfile("")
            }
            // the bug here is that I don't check for successful result and it's also a duplication
            // of the call later in this method
            userId?.let { it1 ->
                mUsersCache?.cacheUser(
                    endpointResult?.let { it2 -> User(it1, it2.fullName, it2.imageUrl) }
                )
            }

        } catch (e: NetworkErrorException) {
            return UseCaseResult.NETWORK_ERROR
        }
        endpointResult?.let { endpoint ->
            if (isSuccessfulEndpointResult(endpoint)) {
                mUsersCache?.cacheUser(
                    userId?.let { it1 ->
                        User(it1, endpoint.fullName, endpoint.imageUrl)
                    }
                )
            }
        }


        // the bug here is that I return wrong result in case of an unsuccessful server response
        return UseCaseResult.SUCCESS
    }

    private fun isSuccessfulEndpointResult(endpointResult: UserProfileHttpEndpointSync.EndpointResult): Boolean {
        return endpointResult.status === UserProfileHttpEndpointSync.EndpointResultStatus.SUCCESS
    }
}