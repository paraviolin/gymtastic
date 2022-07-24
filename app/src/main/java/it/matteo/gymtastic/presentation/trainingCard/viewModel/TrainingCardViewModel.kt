package it.matteo.gymtastic.presentation.trainingCard.viewModel

import androidx.compose.runtime.mutableStateOf
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
class TrainingCardViewModel @Inject constructor(
    private val userService: UserService,
    private val trainingCardService: TrainingCardService
) : ViewModel() {
    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    var trainingCards = mutableStateOf(listOf<TrainingCardModel>())
        private set

    val hasTrainingCards: Boolean = trainingCards.value.isNotEmpty()

    var user = mutableStateOf<UserModel?>(null)
        private set

    private val auth = Firebase.auth

    fun fetchTrainingCards() {
        viewModelScope.launch {
            auth.currentUser?.let {
                user.value = userService.getUserByEmail(it.email!!)
                _loadingState.tryEmit(LoadingState.LOADING)
                val result = trainingCardService.getAllTrainingCards(user.value!!.id)
                trainingCards.value = result
                _loadingState.tryEmit(LoadingState.LOADED)
            }
        }
    }

    fun isTrainer(): Boolean {
        return user.value?.role == GymRole.trainer
    }
}