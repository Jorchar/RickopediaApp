package com.example.rickopediaapp.di

import com.example.rickopediaapp.data.remote.RickAndMortyAPI
import com.example.rickopediaapp.util.URL_SERVER
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun providesHello(): String = "Hello"

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(URL_SERVER)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideRickAndMortyAPI(retrofit: Retrofit): RickAndMortyAPI =
        retrofit.create(RickAndMortyAPI::class.java)
}