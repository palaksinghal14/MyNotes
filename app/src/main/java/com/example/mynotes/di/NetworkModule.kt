package com.example.mynotes.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()
}