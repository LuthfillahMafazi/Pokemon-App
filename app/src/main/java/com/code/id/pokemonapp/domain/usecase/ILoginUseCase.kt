package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.domain.model.UserEntity
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ILoginUseCase {
    suspend fun loginUser(username: String, password: String): Flow<Resource<UserEntity>>
}