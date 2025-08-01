package com.code.id.pokemonapp.domain.usecase

import com.code.id.pokemonapp.data.repository.IUserRepository
import com.code.id.pokemonapp.domain.model.UserEntity
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: IUserRepository
): IRegisterUseCase {
    override suspend fun registerUser(user: UserEntity): Boolean {
        return repository.registerUser(user)
    }
}