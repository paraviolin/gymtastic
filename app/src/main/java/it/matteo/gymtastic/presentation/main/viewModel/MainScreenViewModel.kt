package it.matteo.gymtastic.presentation.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.domain.trainingCard.TrainingCardService
import it.matteo.gymtastic.domain.trainingCard.model.TrainingCardModel
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val trainingCardService: TrainingCardService) :
    ViewModel() {

    init {
        getTrainingCards()
    }

    private val _trainingCards = MutableLiveData<MutableList<TrainingCardModel>>(mutableListOf())
    val trainingCards: List<TrainingCardModel>
        get() {
            return _trainingCards.value!!
        }

    private var _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

     fun getTrainingCards() = viewModelScope.launch {
        var auth: FirebaseAuth = Firebase.auth

         if (_loadingState == null) {
             _loadingState = MutableStateFlow(LoadingState.IDLE)
         }
        _loadingState.tryEmit(LoadingState.LOADING)
        val result = trainingCardService.getAllTrainingCards("pippo")
        _trainingCards.postValue(result as MutableList<TrainingCardModel>?)
        _loadingState.tryEmit(LoadingState.LOADED)
    }
}