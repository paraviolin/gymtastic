package it.matteo.gymtastic.data.trainingCard

import com.google.firebase.firestore.FirebaseFirestore
import it.matteo.gymtastic.data.exceptions.FirebaseConnectionException
import it.matteo.gymtastic.data.trainingCard.entity.TrainingCardEntity
import it.matteo.gymtastic.data.serializers.TrainingCardSerializer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class TrainingCardRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    TrainingCardRepository {
    private val _trainingCardDocumentName = "training_card"

    override fun addTrainingCard(trainingCardEntity: TrainingCardEntity) {
        val trainingCardDto = TrainingCardSerializer.toMap(trainingCardEntity)

        db.collection(_trainingCardDocumentName)
            .document(trainingCardEntity.id)
            .set(trainingCardDto)
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
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
        awaitClose()
    }

    override suspend fun getLastTrainingCard(userId: String) =
        callbackFlow<TrainingCardEntity?> {
            db.collection(_trainingCardDocumentName)
                .whereEqualTo("id", userId)
                .get()
                .addOnSuccessListener {
                    if (it.first().data.isNotEmpty())
                        trySend(TrainingCardSerializer.fromMap(it.first().data))
                }
                .addOnFailureListener {
                    throw FirebaseConnectionException()
                }
            awaitClose()
        }

    override suspend fun getAllTrainingCards(userId: String) =
        callbackFlow<List<TrainingCardEntity>> {
            kotlin.run {
                val trainingCards = mutableListOf<TrainingCardEntity>()

                db.collection(_trainingCardDocumentName)
                    .whereEqualTo("userId", userId)
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
                awaitClose()
            }

        }

    override suspend fun deleteTrainingCard(id: String) = callbackFlow<Void?> {
        db.collection(_trainingCardDocumentName)
            .document(id)
            .delete()
            .addOnFailureListener {
                throw FirebaseConnectionException()
            }
    }

}