package it.matteo.gymtastic.presentation.customerDetail.viewModel

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
class CustomerDetailViewModel @Inject constructor(private val userService: UserService): ViewModel() {
    var customer = mutableStateOf<UserModel?>(null)
        private set

    private var _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    fun fetchUserDetail(email: String) {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            customer.value = userService.getUserByEmail(email)
        }
        _loadingState.tryEmit(LoadingState.LOADED)
    }
}