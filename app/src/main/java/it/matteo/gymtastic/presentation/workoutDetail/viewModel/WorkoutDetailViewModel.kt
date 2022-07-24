package it.matteo.gymtastic.presentation.workoutDetail.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.domain.exercise.ExerciseService
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.session.SessionService
import it.matteo.gymtastic.domain.session.model.SessionExerciseModel
import it.matteo.gymtastic.domain.user.UserService
import it.matteo.gymtastic.domain.user.model.UserModel
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val exerciseService: ExerciseService,
    private val sessionService: SessionService,
    private val userService: UserService
) : ViewModel() {
    private val _exercise = MutableLiveData<ExerciseModel>()
    val exercise: ExerciseModel?
        get() {
            return _exercise.value
        }

    var user = mutableStateOf<UserModel?>(null)
        private set

    var notes = mutableStateOf<List<SessionExerciseModel>?>(null)
        private set

    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    val hasData: Boolean = exercise != null && loadingState.value == LoadingState.LOADED

    private val auth = Firebase.auth

    fun getExerciseDetail(exerciseId: String) {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            val result = exerciseService.getExercise(exerciseId)
            _exercise.postValue(result)
            _loadingState.tryEmit(LoadingState.LOADED)
        }
    }

    fun getNotes(exerciseId: String, userId: String) {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            notes.value = sessionService.getSessionExercises(exerciseId, userId)
            _loadingState.tryEmit(LoadingState.LOADED)
        }
    }

    fun getUser() {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            user.value = userService.getUserByEmail(auth.currentUser!!.email!!)
        }
    }
}