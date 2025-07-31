package com.code.id.pokemonapp.di

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.code.id.pokemonapp.data.local.PokemonDbSqLite
import com.code.id.pokemonapp.utils.NetworkException
import com.code.id.pokemonapp.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return logging
    }

    @Singleton
    @Provides
    fun provideNetworkInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            if (!NetworkUtil.isConnected(context)) {
                throw NetworkException()
            }
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(
        application: Application,
        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: Interceptor
    ): OkHttpClient {
        val chuckInterceptor = ChuckerInterceptor.Builder(application.applicationContext).build()

        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(networkInterceptor)
            .addInterceptor(chuckInterceptor)
            .build()
    }

    fun buildRetrofit(
        baseUrl: String,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideDbHelper(@ApplicationContext context: Context): PokemonDbSqLite =
        PokemonDbSqLite(context)

}