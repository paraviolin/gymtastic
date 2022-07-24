package it.matteo.gymtastic.presentation.session.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.matteo.gymtastic.data.session.entity.SessionExerciseEntity
import it.matteo.gymtastic.domain.exercise.ExerciseService
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.session.SessionService
import it.matteo.gymtastic.domain.session.model.SessionExerciseModel
import it.matteo.gymtastic.domain.trainingCard.TrainingCardService
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(private val sessionService: SessionService, private val trainingCardService: TrainingCardService): ViewModel() {
    private var _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()

    var currentExercise = mutableStateOf<ExerciseModel?>(null)
        private set

    var notes = mutableStateOf("")
        private set

    var exercises = mutableStateOf<List<ExerciseModel>>(listOf())
        private set

    var sessionExercises = mutableListOf<SessionExerciseModel>()

    private var currentExerciseIndex = -1
    var needToSave = mutableStateOf(false)
        private set

    fun fetchExercises(trainingId: String) {
        viewModelScope.launch {
            _loadingState.tryEmit(LoadingState.LOADING)
            trainingCardService.getTrainingCard(trainingId)?.exercises?.let {
                exercises.value = it
                currentExercise.value = it.first()
                currentExerciseIndex = 0
                needToSave.value = currentExerciseIndex == exercises.value.size - 1
            }
            _loadingState.tryEmit(LoadingState.LOADED)
        }

    }

    fun getNextExercise(): Boolean {
        addCurrentExercise()

        notes.value = ""
        currentExerciseIndex++
        needToSave.value = currentExerciseIndex >= exercises.value.size
        if (!needToSave.value) {
            currentExercise.value = exercises.value[currentExerciseIndex]
        }
        return needToSave.value
    }

    fun saveCurrentSession(userId: String, trainingId: String) {
        addCurrentExercise()
        sessionService.createSession(userId, trainingId, sessionExercises)
        currentExercise.value = null
        notes.value = ""
    }

    fun updateNote(newContent: String) {
        notes.value = newContent
    }

    private fun addCurrentExercise() {
        currentExercise.value?.let {
            val sessionExercise = SessionExerciseModel(
                id = UUID.randomUUID().toString(),
                note = notes.value,
                exerciseId = it.id,
                completed = true
            )
            sessionExercises.add(sessionExercise)
        }
    }
}