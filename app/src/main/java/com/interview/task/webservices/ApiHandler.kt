package com.interview.task.webservices

import com.interview.task.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiHandler {

    private var sEndPointService: EndPointService? = null

    val endPointService: EndPointService
        get() {
            if (sEndPointService == null) {
                val retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(Constants.NetworkConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                sEndPointService = retrofit.create(EndPointService::class.java)
            }
            return sEndPointService!!
        }



    // Can be Level.BASIC, Level.HEADERS, or Level.BODY
    private val client: OkHttpClient
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient().newBuilder()
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
            builder.addNetworkInterceptor(interceptor)

            return builder.build()
        }
}
