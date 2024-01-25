package com.example.coupproject.di

import android.content.Context
import com.example.coupproject.data.repository.KakaoRepositoryImpl
import com.example.coupproject.data.repository.FirebaseRepositoryImpl
import com.example.coupproject.domain.repository.KakaoRepository
import com.example.coupproject.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLoginRepositoryImpl(@ApplicationContext context: Context): KakaoRepository {
        return KakaoRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMainRepositoryImpl(@ApplicationContext context: Context): MainRepository {
        return FirebaseRepositoryImpl(context)
    }
}