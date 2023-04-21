package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.User
import com.example.githubuser.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}