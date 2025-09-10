package com.example.mynotes.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.Repository.AuthRepo
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val repo:AuthRepo
): ViewModel()
{

    // we will implement all the functions
    private val _state = mutableStateOf<AuthState>(AuthState.Idle)
    val state: State<AuthState> = _state

    fun isLoggedIn()=repo.isLoggedIn()

    fun SignOut() =repo.SignOut()

    fun getCurrentUser()=repo.getCurrentuser()

    fun SignUp(email: String, password: String){
        viewModelScope.launch{
            val user=repo.SignUp(email, password)
            if(user!=null){
                _state.value=AuthState.Success(user)
            }else{
                _state.value=AuthState.error("Not valid")
            }
        }
    }

    fun SignIn(email: String, password: String){
        viewModelScope.launch{
            _state.value=AuthState.isLoading
            val user=repo.SignIn(email, password)
            if(user!=null){
                _state.value=AuthState.Success(user)
            }else{
                _state.value=AuthState.error("Invalid data ")
            }
        }
        /*
        // if we would be using Result , then iy would look something like this
        viewModelScope.launch {
        _state.value = AuthState.Loading
         val user = repo.signIn(email, password)

         user.onSuccess { user ->
         _state.value = AuthState.Success(user)
         }.onFailure { e ->
         _state.value = AuthState.Error(e.localizedMessage ?: "Unknown error")
         }
        }
         */
    }


        // necessary to store states
        sealed class AuthState
        {
           object  Idle:AuthState()
           object  isLoading :AuthState()
           data class Success(val user: FirebaseUser) :AuthState()
            data class error(val msg: String) :AuthState()
        }

}
