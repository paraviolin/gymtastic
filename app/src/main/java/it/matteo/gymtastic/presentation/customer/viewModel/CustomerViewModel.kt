package it.matteo.gymtastic.presentation.customer.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.domain.exercise.ExerciseService
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.trainingCard.TrainingCardService
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.domain.user.UserService
import it.matteo.gymtastic.domain.user.model.UserModel
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val userService: UserService,
    private val trainingCardService: TrainingCardService,
    private val exerciseService: ExerciseService
) : ViewModel() {
    var customer = mutableStateOf<UserModel?>(null)
        private set

    var workouts = mutableStateOf<List<TrainingCardModel>>(listOf())
        private set

    var availableExercises = mutableStateOf<List<ExerciseModel>>(listOf())
        private set

    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    fun fetchUserDetail(email: String) {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            customer.value = userService.getUserByEmail(email)
        }
        _loadingState.tryEmit(LoadingState.LOADED)
    }

    fun fetchUserDetailFromId(id: String) {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            customer.value = userService.getUserById(id)
        }
        _loadingState.tryEmit(LoadingState.LOADED)
    }

    fun fetchUserWorkouts(customerId: String) {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            workouts.value = trainingCardService.getAllTrainingCards(customerId)
        }
        _loadingState.tryEmit(LoadingState.LOADED)
    }

    fun fetchAllAvailableExercises() {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            availableExercises.value = exerciseService.getAllExercises()
        }
        _loadingState.tryEmit(LoadingState.LOADED)
    }
}