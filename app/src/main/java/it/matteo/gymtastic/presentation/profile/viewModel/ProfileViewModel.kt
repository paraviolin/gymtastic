package it.matteo.gymtastic.presentation.profile.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.domain.user.UserService
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _userProfile = MutableLiveData<UserEntity>()
    val userProfile: UserEntity?
        get() = _userProfile.value

    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    fun updateUserProfile(email: String) {
        viewModelScope.launch {
            _loadingState.tryEmit(LoadingState.LOADING)
            val result = userService.getUserByEmail(email)
            _userProfile.postValue(result)
            _loadingState.tryEmit(LoadingState.LOADED)
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            userProfile?.let{
                userService.updateUser(it.copy(name = name))
            }
        }
    }

    fun updateSurname(surname: String) {
        viewModelScope.launch {
            userProfile?.let{
                userService.updateUser(it.copy(surname = surname))
            }
        }
    }
}