package it.matteo.gymtastic.domain.exercise

import it.matteo.gymtastic.data.exercise.ExerciseRepository
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.utils.ExerciseConverter
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExerciseService @Inject constructor(private val exerciseRepository: ExerciseRepository){
    suspend fun getExercise(exerciseId: String): ExerciseModel? {
        val result = exerciseRepository.getExercise(exerciseId).first() ?: return null
        return ExerciseConverter.toModel(result)
    }
}