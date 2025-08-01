package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.domain.model.UserEntity

interface IRegisterUseCase {
    suspend fun registerUser(user: UserEntity): Boolean
}