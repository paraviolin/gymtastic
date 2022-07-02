package it.matteo.gymtastic.data.workout

import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.data.workout.entity.WorkoutEntity

interface WorkoutRepository {
    suspend fun getWorkouts(userEntity: UserEntity): List<WorkoutEntity>

    suspend fun addWorkout(workoutModel: WorkoutEntity)

    suspend fun removeWorkout(workoutModel: WorkoutEntity)

    suspend fun updateWorkout(workoutEntity: WorkoutEntity)
}