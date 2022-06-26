package it.matteo.gymtastic.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.matteo.gymtastic.data.exercise.ExerciseRepository
import it.matteo.gymtastic.data.exercise.ExerciseRepositoryImpl
import it.matteo.gymtastic.data.trainingCard.TrainingCardRepository
import it.matteo.gymtastic.data.trainingCard.TrainingCardRepositoryImpl
import it.matteo.gymtastic.domain.trainingCard.TrainingCardService

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirestoreService(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideExerciseRepository(firestore: FirebaseFirestore): ExerciseRepository =
        ExerciseRepositoryImpl(firestore)

    @Provides
    fun provideTrainingCardRepository(firestore: FirebaseFirestore): TrainingCardRepository =
        TrainingCardRepositoryImpl(firestore)

    @Provides
    fun provideTrainingCardService(
        trainingCardRepository: TrainingCardRepository,
        exerciseRepository: ExerciseRepository
    ): TrainingCardService = TrainingCardService(trainingCardRepository, exerciseRepository)
}