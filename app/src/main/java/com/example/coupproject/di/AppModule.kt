package com.example.coupproject.di

import android.content.Context
import com.example.coupproject.data.repository.LoginRepositoryImpl
import com.example.coupproject.data.repository.MainRepositoryImpl
import com.example.coupproject.domain.repository.LoginRepository
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
    fun provideLoginRepositoryImpl(@ApplicationContext context: Context): LoginRepository {
        return LoginRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMainRepositoryImpl(@ApplicationContext context: Context): MainRepository {
        return MainRepositoryImpl(context)
    }
}