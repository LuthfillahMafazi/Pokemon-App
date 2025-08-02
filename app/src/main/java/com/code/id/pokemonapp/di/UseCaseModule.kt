package com.code.id.pokemonapp.di

import com.code.id.pokemonapp.data.repository.IPokemonRepository
import com.code.id.pokemonapp.data.repository.IUserRepository
import com.code.id.pokemonapp.domain.usecase.DetailUseCase
import com.code.id.pokemonapp.domain.usecase.HomeUseCase
import com.code.id.pokemonapp.domain.usecase.IDetailUseCase
import com.code.id.pokemonapp.domain.usecase.IHomeUseCase
import com.code.id.pokemonapp.domain.usecase.ILoginUseCase
import com.code.id.pokemonapp.domain.usecase.IRegisterUseCase
import com.code.id.pokemonapp.domain.usecase.LoginUseCase
import com.code.id.pokemonapp.domain.usecase.RegisterUseCase
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

    @Provides
    @Singleton
    fun provideDetailUseCase(
        repository: IPokemonRepository
    ): IDetailUseCase {
        return DetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        repository: IUserRepository
    ): IRegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: IUserRepository
    ): ILoginUseCase {
        return LoginUseCase(repository)
    }
}