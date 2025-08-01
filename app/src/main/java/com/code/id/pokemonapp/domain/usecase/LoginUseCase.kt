package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.data.repository.IUserRepository
import com.code.id.pokemonapp.domain.model.UserEntity
import com.code.id.pokemonapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: IUserRepository
): ILoginUseCase {
    override suspend fun loginUser(
        username: String,
        password: String
    ): Flow<Resource<UserEntity>> {
        return repository.loginUser(username, password)
    }
}