package it.matteo.gymtastic.presentation.main.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.domain.trainingCard.TrainingCardService
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.domain.user.UserService
import it.matteo.gymtastic.domain.user.model.GymRole
import it.matteo.gymtastic.domain.user.model.UserModel
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val trainingCardService: TrainingCardService,
    private val userService: UserService
) : ViewModel() {
    var lastTrainingCard = mutableStateOf<TrainingCardModel?>(null)
        private set

    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val auth = Firebase.auth

    var user = mutableStateOf<UserModel?>(null)
        private set

    var customers = mutableStateOf(listOf<UserModel>())
        private set


    fun fetchLastTrainingCard() {
        viewModelScope.launch {
            user.value = userService.getUserByEmail(auth.currentUser!!.email!!)
            _loadingState.tryEmit(LoadingState.LOADING)
            val result = trainingCardService.getLastTrainingCard(user.value!!.id)
            lastTrainingCard.value = result
            _loadingState.tryEmit(LoadingState.LOADED)
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            user.value = userService.getUserByEmail(auth.currentUser!!.email!!)
        }
    }

    fun isTrainer(): Boolean {
        return user.value?.role == GymRole.trainer
    }

    fun fetchCustomers() {
        viewModelScope.launch {
            _loadingState.tryEmit(LoadingState.LOADING)
            customers.value = userService.getCustomers()
            _loadingState.tryEmit(LoadingState.LOADED)
        }
    }
}