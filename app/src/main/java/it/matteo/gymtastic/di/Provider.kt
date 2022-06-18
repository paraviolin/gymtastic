package it.matteo.gymtastic.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import it.matteo.gymtastic.MainActivity

@Module
@InstallIn(MainActivity::class)
object FirestoreModule {
    @Provides
    fun provideFirestoreService(): FirebaseFirestore {
        return Firebase.firestore
    }
}