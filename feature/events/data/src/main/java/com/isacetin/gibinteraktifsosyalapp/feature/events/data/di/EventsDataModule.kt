package com.isacetin.gibinteraktifsosyalapp.feature.events.data.di

import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.EventsApi
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.repository.EventsRepositoryImpl
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.repository.EventsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EventsDataModule {

    @Binds
    abstract fun bindEventsRepository(impl: EventsRepositoryImpl): EventsRepository

    companion object {
        @Provides
        @Singleton
        fun provideEventsApi(retrofit: Retrofit): EventsApi = retrofit.create(EventsApi::class.java)
    }
}
