package it.matteo.gymtastic.data.exercise

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.exercise.entity.ExerciseEntity
import it.matteo.gymtastic.data.serializers.ExerciseSerializer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    ExerciseRepository {
    private val _exerciseDocumentName = "exercise"

    override suspend fun getAllExercises() =
        callbackFlow<List<ExerciseEntity>> {
            val exercises = mutableListOf<ExerciseEntity>()

            db.collection(_exerciseDocumentName)
                .get()
                .addOnSuccessListener { documents ->
                    documents.forEach { document ->
                        exercises.add(ExerciseSerializer.exerciseFromMap(document.data))
                    }
                    trySend(exercises)
                }
                .addOnFailureListener {
                    throw FirebaseConnectionException()
                }
            awaitClose()
        }

    override suspend fun getExercise(exerciseId: String) =
        callbackFlow<ExerciseEntity?> {
            db.collection(_exerciseDocumentName)
                .whereEqualTo("id", exerciseId)
                .get()
                .addOnSuccessListener {
                    trySend(ExerciseSerializer.exerciseFromMap(it.first().data))
                }
                .addOnFailureListener {
                    throw FirebaseConnectionException()
                }
            awaitClose()
        }

    override fun addExercise(exerciseEntity: ExerciseEntity) {
        val exerciseDto = ExerciseSerializer.toMap(exerciseEntity)

        db.collection(_exerciseDocumentName)
            .document(exerciseEntity.id)
            .set(exerciseDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }


    override fun updateExercise(exerciseEntity: ExerciseEntity) =
        addExercise(exerciseEntity)

    override suspend fun deleteExercise(exerciseEntity: ExerciseEntity) = callbackFlow<Void?> {
        db.collection(_exerciseDocumentName)
            .document(exerciseEntity.id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }


}