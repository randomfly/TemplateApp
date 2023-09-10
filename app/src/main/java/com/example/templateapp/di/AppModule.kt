package com.example.templateapp.di

import android.content.Context
import com.example.templateapp.BuildConfig
import com.example.templateapp.data.source.local.UserDatabase
import com.example.templateapp.data.source.remote.api.ApiService
import com.example.templateapp.data.source.repository.UserRepoImpl
import com.example.templateapp.data.source.repository.UserRepository
import com.example.templateapp.utils.Constant
import com.example.templateapp.utils.NetworkHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constant.RANDOM_DATA_BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        baseUrl: String,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(client)
            addConverterFactory(MoshiConverterFactory.create(moshi))
        }.build()

    @Provides
    @Singleton
    fun provideHttpLogging() =
        HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
        }.build()

    @Provides
    @Singleton
    fun provideRepository(
        service: ApiService,
        database: UserDatabase,
        networkHelper: NetworkHelper
    ): UserRepository =
        UserRepoImpl(service, database, networkHelper)

    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkHelper =
        NetworkHelper(context)

    // Provide Moshi
    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().apply {
            addLast(KotlinJsonAdapterFactory())
        }.build()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}