package com.code.id.pokemonapp.data.repository

import com.code.id.pokemonapp.data.local.user.UserDbSqLite
import com.code.id.pokemonapp.domain.model.UserEntity
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localService: UserDbSqLite
): IUserRepository {
    override suspend fun registerUser(userEntity: UserEntity): Boolean {
        return localService.insertUser(userEntity)
    }

    override suspend fun loginUser(
        username: String,
        password: String
    ): Flow<Resource<UserEntity>> = flow {
        val dataUser = localService.getUserByUsernameAndPassword(username, password)
        if (dataUser != null) emit(Resource.Success(dataUser))
        else emit(Resource.Error("User not found"))
    }
}