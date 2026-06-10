package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.di

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.TaskApi
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.repository.TasksRepositoryImpl
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.repository.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TasksDataModule {

    @Binds
    abstract fun bindTasksRepository(impl: TasksRepositoryImpl): TasksRepository

    companion object {
        @Provides
        @Singleton
        fun provideTaskApi(retrofit: Retrofit): TaskApi = retrofit.create(TaskApi::class.java)
    }
}
