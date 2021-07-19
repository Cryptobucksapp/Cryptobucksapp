package com.cryptobucksapp.cryptobucks.app.network.di

import android.content.Context
import android.os.Build
import com.cryptobucksapp.cryptobucks.BuildConfig
import com.cryptobucksapp.cryptobucks.app.AliantPaymentsApp
import com.cryptobucksapp.cryptobucks.app.di.AliantPaymentsScope
import com.cryptobucksapp.cryptobucks.app.network.interceptor.ConnectivityInterceptor
import com.cryptobucksapp.cryptobucks.utils.manager.CustomCookieManager
import com.cryptobucksapp.cryptobucks.utils.manager.SessionManager
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    companion object {
        const val OK_HTTP_CACHE = "okhttp_cache"
    }

    @Provides
    @AliantPaymentsScope
    fun provideinterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @AliantPaymentsScope
    fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, 10*1000*1000) // 10MB Cache
    }

    @Provides
    @AliantPaymentsScope
    fun provideCacheFile(context: Context): File {
        return File(context.cacheDir, OK_HTTP_CACHE)
    }

    @Provides
    @AliantPaymentsScope
    fun provideCookieManager(context: Context): CustomCookieManager = CustomCookieManager(context)

    @Provides
    @AliantPaymentsScope
    fun provideCookieJar(customCookieManager: CustomCookieManager): PersistentCookieJar =
        PersistentCookieJar(SetCookieCache(), customCookieManager)

    @Provides
    @AliantPaymentsScope
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache,
                            cookieJar: PersistentCookieJar): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ConnectivityInterceptor(AliantPaymentsApp.appContext))
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .addHeader(
                        "User-Agent",
                        "Cryptobucks-ANDROID " + " BUILD VERSION: " + " SMARTPHONE: " + Build.MODEL + " ANDROID VERSION: " + Build.VERSION.RELEASE
                    )
                    .addHeader("x-api-key", SessionManager.getInstance().getToken()?: "")
                    .addHeader("X-SDK-PLATFORM", "android")
                    .addHeader("Content-Type", "application/json")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @Provides
    @AliantPaymentsScope
    @LoginQualifier
    fun provideOkHttpClientLogin(loggingInterceptor: HttpLoggingInterceptor, cache: Cache,
                                 cookieJar: PersistentCookieJar): OkHttpClient {
        return getOkHttpClient(loggingInterceptor, cache, cookieJar)
    }

    private fun getOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache,
                                cookieJar: PersistentCookieJar): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ConnectivityInterceptor(AliantPaymentsApp.appContext))
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "User-Agent",
                        "Cryptobucks-ANDROID" + " BUILD VERSION: " + " SMARTPHONE: " + Build.MODEL + " ANDROID VERSION: " + Build.VERSION.RELEASE
                    )
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

}