package com.karan.findout.data.network

import com.karan.findout.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val NETWORK_TIMEOUT = 60L
        private const val KEY_HEADER = "user_key"
    }

    @Singleton
    @Provides
    fun provideAuthenticationInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder().addHeader(KEY_HEADER, BuildConfig.API_KEY).build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun providerOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}
