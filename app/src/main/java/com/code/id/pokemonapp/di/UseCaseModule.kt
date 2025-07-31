package com.code.id.pokemonapp.di

import com.code.id.pokemonapp.data.repository.IPokemonRepository
import com.code.id.pokemonapp.domain.usecase.HomeUseCase
import com.code.id.pokemonapp.domain.usecase.IHomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideHomeUseCase(
        repository: IPokemonRepository
    ): IHomeUseCase {
        return HomeUseCase(repository)
    }
}