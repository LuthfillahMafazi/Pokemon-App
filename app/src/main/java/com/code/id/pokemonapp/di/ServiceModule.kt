package com.code.id.pokemonapp.di

import com.code.id.pokemonapp.BuildConfig
import com.code.id.pokemonapp.data.remote.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMovieService(
        client: OkHttpClient
    ) : PokemonApiService {
        return AppModule
            .buildRetrofit("https://pokeapi.co/api/v2/", client)
            .create(PokemonApiService::class.java)
    }
}