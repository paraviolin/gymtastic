package it.matteo.gymtastic.presentation.exercise.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.domain.exercise.ExerciseService
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.exercise.model.ExerciseType
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(private val exerciseService: ExerciseService): ViewModel() {
    var exercises = mutableStateOf<List<ExerciseModel>?>(null)
        private set

    private var _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    fun fetchExercises() {
        _loadingState.tryEmit(LoadingState.LOADING)
        viewModelScope.launch{
            exercises.value = exerciseService.getAllExercises()
        }
        _loadingState.tryEmit(LoadingState.LOADED)
    }

    fun save(id: String, name: String, description: String, duration: String, type: String) {
        exerciseService.createExercise(ExerciseModel(
            id = id,
            name = name,
            description = description,
            duration = duration,
            type = ExerciseType.valueOf(type)
        ))
    }

}