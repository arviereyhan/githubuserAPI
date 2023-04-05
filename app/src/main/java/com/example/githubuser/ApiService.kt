package com.example.githubuser

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