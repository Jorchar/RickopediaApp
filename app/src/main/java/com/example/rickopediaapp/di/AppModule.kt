package com.example.rickopediaapp.di
import android.content.Context
import androidx.room.Room
import com.example.rickopediaapp.data.local.AppDatabase
import com.example.rickopediaapp.data.remote.RickAndMortyAPI
import com.example.rickopediaapp.util.URL_SERVER
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

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

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "rick-database"
        ).allowMainThreadQueries().build()
}