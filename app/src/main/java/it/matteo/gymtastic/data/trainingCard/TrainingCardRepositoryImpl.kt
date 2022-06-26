package it.matteo.gymtastic.data.trainingCard

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.Response
import it.matteo.gymtastic.data.Response.*
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import it.matteo.gymtastic.data.utils.serializers.TrainingCardSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.Error

class TrainingCardRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    TrainingCardRepository {
    private val _trainingCardDocumentName = "training_card"

    override suspend fun addTrainingCard(trainingCardEntity: TrainingCardEntity) =
        flow<Response<Void?>> {
            emit(Loading)
            val trainingCardDto = TrainingCardSerializer.toMap(trainingCardEntity)

            val result = db.collection(_trainingCardDocumentName)
                .document(trainingCardEntity.id)
                .set(trainingCardDto)
                .addOnFailureListener {
                    throw FirebaseConnectionException()
                }
                .result

            emit(Success(result))

        }

    override suspend fun getTrainingCard(id: String) = callbackFlow<TrainingCardEntity?> {

        db.collection(_trainingCardDocumentName)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                trySend((TrainingCardSerializer.fromMap(it.first().data)))
            }
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

    override suspend fun getLastTrainingCard(userId: String) =
        callbackFlow<TrainingCardEntity?> {
            db.collection(_trainingCardDocumentName)
                .whereEqualTo("id", userId)
                .get()
                .addOnSuccessListener {
                    trySend(TrainingCardSerializer.fromMap(it.first().data))
                }
                .addOnFailureListener {
                    throw FirebaseConnectionException()
                }
        }

    override suspend fun getAllTrainingCards(userId: String) =
        callbackFlow<List<TrainingCardEntity>> {
            val trainingCards = mutableListOf<TrainingCardEntity>()

            db.collection(_trainingCardDocumentName)
                .get()
                .addOnSuccessListener { documents ->
                    documents.forEach { document ->
                        trainingCards.add(TrainingCardSerializer.fromMap(document.data))
                    }
                    trySend(trainingCards)
                }
                .addOnFailureListener {
                    throw FirebaseConnectionException()
                }
        }

    override suspend fun deleteTrainingCard(id: String) = flow<Response<Void?>> {
        emit(Loading)
        val result = db.collection(_trainingCardDocumentName)
            .document(id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
            .result
        emit(Success(result))

    }

}