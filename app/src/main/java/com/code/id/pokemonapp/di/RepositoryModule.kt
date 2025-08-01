package com.code.id.pokemonapp.di

import com.code.id.pokemonapp.data.local.PokemonDbSqLite
import com.code.id.pokemonapp.data.local.user.UserDbSqLite
import com.code.id.pokemonapp.data.remote.PokemonApiService
import com.code.id.pokemonapp.data.repository.IPokemonRepository
import com.code.id.pokemonapp.data.repository.IUserRepository
import com.code.id.pokemonapp.data.repository.PokemonRepositoryImpl
import com.code.id.pokemonapp.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: PokemonApiService,
        dbHelper: PokemonDbSqLite
    ): IPokemonRepository {
        return PokemonRepositoryImpl(api, dbHelper)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        dbHelper: UserDbSqLite
    ): IUserRepository {
        return UserRepositoryImpl(dbHelper)
    }
}