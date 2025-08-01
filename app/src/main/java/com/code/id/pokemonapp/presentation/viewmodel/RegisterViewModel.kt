package com.code.id.pokemonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.id.pokemonapp.domain.model.UserEntity
import com.code.id.pokemonapp.domain.usecase.IRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: IRegisterUseCase
): ViewModel() {

    private val _registerResult = MutableSharedFlow<Boolean?>(replay = 0)
    val registerResult get() = _registerResult.asSharedFlow()

    fun registerUser(userEntity: UserEntity) {
        viewModelScope.launch {
            val result = useCase.registerUser(userEntity)
            _registerResult.emit(result)
        }
    }

}