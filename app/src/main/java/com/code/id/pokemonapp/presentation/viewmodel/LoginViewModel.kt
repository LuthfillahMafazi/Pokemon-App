package com.code.id.pokemonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.id.pokemonapp.data.local.PreferenceManager
import com.code.id.pokemonapp.domain.model.UserEntity
import com.code.id.pokemonapp.domain.usecase.ILoginUseCase
import com.code.id.pokemonapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: ILoginUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _userResult = MutableSharedFlow<UserEntity?>(replay = 0)
    val userResult get() = _userResult.asSharedFlow()

    private val _errorResult = MutableSharedFlow<String?>(replay = 0)
    val errorResult get() = _errorResult.asSharedFlow()

    fun loginUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.loginUser(username, password).collect {
                when (it) {
                    is Resource.Success -> {
                        _userResult.emit(it.data)
                        preferenceManager.saveUserLogin(it.data?.id ?: 0, it.data?.userName ?: "")
                    }

                    is Resource.Error -> {
                        _errorResult.emit(it.message)
                    }

                    else -> Unit
                }
            }
        }
    }
}