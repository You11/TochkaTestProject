package ru.you11.tochkatestproject.model

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query


interface GithubService {

    @GET("search/users")
    fun usersSearch(
        @Query("q") q: String,
        @Query("page") page: Int
    ): Observable<GithubUserList>

    companion object {
        fun create(): GithubService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(GithubService::class.java)
        }
    }

}