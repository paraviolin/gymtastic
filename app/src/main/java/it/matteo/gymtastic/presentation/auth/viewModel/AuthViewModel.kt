package it.matteo.gymtastic.presentation.auth.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private val _loadingState = MutableStateFlow(LoadingState.IDLE)
    val loadingState = _loadingState.asStateFlow()

    private val _user = MutableLiveData<FirebaseUser>(auth.currentUser)
    val user: LiveData<FirebaseUser>
        get() = _user

    fun isLoggedIn() = auth.currentUser != null

    fun loadScreen() {
        _loadingState.tryEmit(LoadingState.IDLE)
    }

    fun signUp(email: String, password: String) {
        _loadingState.tryEmit(LoadingState.LOADING)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    _loadingState.tryEmit(LoadingState.LOADED)
                }
            }
            .addOnFailureListener {
                _loadingState.tryEmit(LoadingState.error(it.localizedMessage))
            }
    }

    fun login(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch {
                _loadingState.emit(LoadingState.LOADING)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _user.value = auth.currentUser
                            _loadingState.tryEmit(LoadingState.LOGGED_IN)
                        } else {
                            try {
                                _loadingState.tryEmit(LoadingState.error(task.result.toString()))
                            } catch (ex: Exception) {
                                _loadingState.tryEmit(LoadingState.error(ex.localizedMessage))
                            }
                        }
                    }
                    .addOnFailureListener { task ->
                        _loadingState.tryEmit(LoadingState.error(task.localizedMessage))
                    }
                _loadingState.emit(LoadingState.LOADED)
            }

        }
    }

    fun logout() {
        _loadingState.tryEmit(LoadingState.LOADING)
        auth.signOut()
        _user.value = null
        _loadingState.tryEmit(LoadingState.IDLE)
    }
}
