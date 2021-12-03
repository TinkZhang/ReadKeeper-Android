package com.github.tinkzhang.readkeeper.search.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

private const val GOOGLE_BOOK_KEY = "AIzaSyDntV4wugC2oEewMD4RZ5tCDZ75ysTf300"

const val SIZE = 10

interface GoogleBookService {
    @GET("books/v1/volumes")
    suspend fun search(
        @Query("q") keyword: String,
        @Query("startIndex") startIndex: Int,
        @Query("key") key: String = GOOGLE_BOOK_KEY
    ): GoogleBookDto

    companion object {
        val instance: GoogleBookService by lazy {
            val client = OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }
            ).build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            retrofit.create()
        }
    }
}
