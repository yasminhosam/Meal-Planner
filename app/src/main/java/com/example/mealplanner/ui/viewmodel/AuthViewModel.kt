package com.example.mealplanner.ui.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.AuthMethod
import com.example.mealplanner.domain.repo.AuthRepository
import com.example.mealplanner.domain.usecase.LoginUseCase
import com.example.mealplanner.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor (
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val authRepository: AuthRepository

) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<AuthEvent>()
    val event = _event.asSharedFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(emailInput = newValue, emailError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(passwordInput = newValue, passwordError = null) }
    }

    fun onUsernameChange(newValue: String) {
        _uiState.update { it.copy(usernameInput = newValue, usernameError = null) }
    }

    fun loginWithEmail() {
        val current = _uiState.value

        val emailError = validateEmail(current.emailInput)
        val passError = validatePassword(current.passwordInput)
        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passError,
                generalError = null
            )
        }
        if ( emailError != null || passError != null) return
        viewModelScope.launch {

            try {
                _uiState.update { it.copy(isLoading = true) }

                loginUseCase(
                    AuthMethod.EmailPassword(current.emailInput, current.passwordInput)
                )
                if(authRepository.isEmailVerified()){
                    _event.emit(AuthEvent.LoginSuccess)
                }else{
                    authRepository.logout()
                    _uiState.update {
                        it.copy(generalError = "Please verify your email before logging in. Check your inbox!")
                    }

                }

            } catch (e: Exception) {
                Log.d("AuthViewModel",e.message.toString())
                _uiState.update {
                    it.copy(generalError = mapAuthError(e))
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                loginUseCase(AuthMethod.Google(idToken))

                _event.emit(AuthEvent.LoginSuccess)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(generalError = mapAuthError(e))
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

//    fun loginWithFacebook(accessToken: String) {
//        viewModelScope.launch {
//            try {
//                _uiState.update { it.copy(isLoading = true) }
//
//                loginUseCase(AuthMethod.Facebook(accessToken))
//
//                _event.emit(AuthEvent.LoginSuccess)
//
//            } catch (e: Exception) {
//                _uiState.update {
//                    it.copy(generalError = e.localizedMessage)
//                }
//            } finally {
//                _uiState.update { it.copy(isLoading = false) }
//            }
//        }
//    }
//

    fun onSignUpClick() {
        val current = _uiState.value
        val nameError = validateName(current.usernameInput)
        val emailError = validateEmail(current.emailInput)
        val passError = validatePassword(current.passwordInput)
        _uiState.update {
            it.copy(
                usernameError = nameError,
                emailError = emailError,
                passwordError = passError,
                generalError = null
            )
        }
        if (nameError != null || emailError != null || passError != null) return
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                signUpUseCase(current.emailInput, current.passwordInput, current.usernameInput)
                _uiState.update {
                    it.copy(generalError = "Verification email sent! Please check your inbox..")
                }
                authRepository.logout()
                _event.emit(AuthEvent.SignUpSuccess)


            } catch (e: Exception) {
                _uiState.update { it.copy(generalError = mapAuthError(e)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }


    }

    private fun validateName(name: String): String? {
        if (name.trim().length < 2) return "Your name must be at least two characters"
        return null
    }

    private fun validateEmail(email: String): String? {
        if (email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) return null
        return if (email.isBlank()) "Email is required" else "Invalid email format"
    }

    private fun validatePassword(password: String): String? {
        if (password.length < 6) return "Password must be at least 6 characters"
        if (!password.any { it.isDigit() }) return "Password must contain a number"
        if (!password.any { it.isUpperCase() }) return "Password must contain an uppercase letter"
        return null
    }
    private fun mapAuthError(e: Exception): String {
        val message = e.message ?: return "Something went wrong"

        return when {
            message.contains("invalid-email", true) ->
                "Invalid email address"

            message.contains("user-not-found", true) ->
                "Email or password is incorrect"

            message.contains("wrong-password", true) ->
                "Email or password is incorrect"

            message.contains("email-already-in-use", true) ->
                "This email is already registered"

            message.contains("weak-password", true) ->
                "Password is too weak"

            message.contains("network", true) ->
                "No internet connection"

            else ->
                "Authentication failed email or password is incorrect"
        }
    }

}


sealed interface AuthEvent {
    object LoginSuccess : AuthEvent
    object SignUpSuccess : AuthEvent

}

data class AuthUiState(
    val isLoading: Boolean = false,

    val emailInput: String = "",
    val passwordInput: String = "",
    val usernameInput: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,
    val usernameError: String? = null,
    val generalError: String? = null
)
