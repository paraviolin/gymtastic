package it.matteo.gymtastic.presentation.workoutDetail.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.domain.exercise.ExerciseService
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(private val exerciseService: ExerciseService): ViewModel() {
    private val _exercise = MutableLiveData<ExerciseModel>()
    val exercise: ExerciseModel?
        get() {
            return _exercise.value
        }

    private var _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    fun getExerciseDetail(exerciseId: String) {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch {
            _loadingState.tryEmit(LoadingState.LOADING)
            val result = exerciseService.getExercise(exerciseId)
            _exercise.postValue(result)
            _loadingState.tryEmit(LoadingState.LOADED)
        }
    }
}