package app.tinks.readkeeper.search.network

import app.tinks.readkeeper.search.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

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
            val client = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
                connectTimeout(5, TimeUnit.SECONDS)
                readTimeout(5, TimeUnit.SECONDS)
            }
            .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            retrofit.create()
        }
    }
}
