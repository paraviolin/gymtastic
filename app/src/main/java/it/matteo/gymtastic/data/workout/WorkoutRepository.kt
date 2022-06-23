package it.matteo.gymtastic.data.workout

import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.data.workout.entity.WorkoutEntity

interface WorkoutRepository {
    fun getWorkouts(userEntity: UserEntity): List<WorkoutEntity>

    fun addWorkout(workoutModel: WorkoutEntity)

    fun removeWorkout(workoutModel: WorkoutEntity)

    fun updateWorkout(workoutEntity: WorkoutEntity)
}