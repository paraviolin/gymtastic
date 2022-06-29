package it.matteo.gymtastic.presentation.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.domain.trainingCard.TrainingCardService
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.domain.user.UserService
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val trainingCardService: TrainingCardService,
    private val userService: UserService
) :
    ViewModel() {
    private val _trainingCards = MutableLiveData<MutableList<TrainingCardModel>>(mutableListOf())
    val trainingCards: List<TrainingCardModel>
        get() {
            return _trainingCards.value!!
        }

    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val auth = Firebase.auth

    private var _user = MutableLiveData<UserEntity>()
    val user: UserEntity?
        get() = _user.value


    fun getTrainingCards() {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            val user = userService.getUserByEmail(auth.currentUser!!.email!!)
            _user.postValue(user)
            _loadingState.tryEmit(LoadingState.LOADING)
            val result = trainingCardService.getAllTrainingCards(user.name)
            _trainingCards.postValue(result as MutableList<TrainingCardModel>?)
            _loadingState.tryEmit(LoadingState.LOADED)
        }
    }
}