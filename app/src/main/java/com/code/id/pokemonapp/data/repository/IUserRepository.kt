package com.code.id.pokemonapp.data.repository

import com.code.id.pokemonapp.domain.model.UserEntity
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun registerUser(userEntity: UserEntity): Boolean
    suspend fun loginUser(username: String, password: String): Flow<Resource<UserEntity>>
}