package it.matteo.gymtastic.presentation.profile.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.domain.user.UserService
import it.matteo.gymtastic.domain.user.model.UserModel
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    var userProfile = mutableStateOf<UserModel?>(null)

    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    fun updateUserProfile(email: String) {
        viewModelScope.launch {
            _loadingState.tryEmit(LoadingState.LOADING)
            userProfile.value = userService.getUserByEmail(email)
            _loadingState.tryEmit(LoadingState.LOADED)
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            userProfile.value?.let{
                val updatedUser = it.copy(name = name)
                userProfile.value = updatedUser
                userService.updateUser(it.copy(name = name))
            }
        }
    }

    fun updateSurname(surname: String) {
        viewModelScope.launch {
            userProfile.value?.let{
                val updatedUser = it.copy(surname = surname)
                userProfile.value = updatedUser
                userService.updateUser(it.copy(surname = surname))
            }
        }
    }
}