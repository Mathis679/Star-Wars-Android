package com.example.starwarsappmvvm.di

import android.content.Context
import com.example.domain.exception.NoInternetException
import com.example.starwarsappmvvm.config.API_URL
import com.example.starwarsappmvvm.di.Properties.TIME_OUT
import com.example.utils.NetworkUtil
import com.google.gson.Gson
import okhttp3.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { Gson() }

    single {
        getRetrofit(
                okHttpClient = get(),
                gsonFactory = get()
        )
    }

    single { getOkHttpCache(context = androidApplication()) }

    single {
        getOkHttpClient(
            cache = get(),
            interceptor = get()
        )
    }
    single { getGsonConverterFactory(gson = get()) }

    single { getConnectivityInterceptor(context = androidApplication()) }
}

fun getRetrofit(
        okHttpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(gsonFactory)
            .client(okHttpClient)
            .build()
}

fun getOkHttpCache(context: Context): Cache {
    val size = (10 * 1024 * 1024).toLong() // 10 Mb
    return Cache(context.cacheDir, size)
}

fun getOkHttpClient(
        cache: Cache,
        interceptor: Interceptor
): OkHttpClient {

    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.apply {
        connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS)
        cache(cache)
        addInterceptor(interceptor)
    }
    return okHttpClientBuilder.build()
}

fun getGsonConverterFactory(gson: Gson): GsonConverterFactory {
    return GsonConverterFactory.create(gson)
}

fun getConnectivityInterceptor(context: Context): Interceptor {
    return Interceptor {

        if(!NetworkUtil.isConnected(context))
            throw NoInternetException()

        it.proceed(it.request().newBuilder().build())

    }
}

object Properties {
    const val TIME_OUT = 150L // seconds
}


