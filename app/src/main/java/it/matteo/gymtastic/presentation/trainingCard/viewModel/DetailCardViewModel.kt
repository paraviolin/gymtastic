package it.matteo.gymtastic.presentation.trainingCard.viewModel

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
class DetailCardViewModel @Inject constructor(
    private val trainingCardService: TrainingCardService,
    private val userService: UserService
) : ViewModel() {
    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val _currentTrainingCard = MutableLiveData<TrainingCardModel>()
    val currentTrainingCard: TrainingCardModel?
        get() = _currentTrainingCard.value

    private val auth = Firebase.auth

    var user = mutableStateOf<UserModel?>(null)
        private set

    val hasTrainingCard: Boolean = currentTrainingCard != null

    fun getTrainingCard(cardId: String) {
        viewModelScope.launch {
            _loadingState.tryEmit(LoadingState.LOADING)
            val result = trainingCardService.getTrainingCard(cardId)
            _currentTrainingCard.postValue(result)
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
}