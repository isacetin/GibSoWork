package com.isacetin.gibinteraktifsosyalapp.feature.game.data.di

import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.GameApi
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.repository.GameRepositoryImpl
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameDataModule {
    @Binds
    @Singleton
    abstract fun bindGameRepository(impl: GameRepositoryImpl): GameRepository

    companion object {
        @Provides
        @Singleton
        fun provideGameApi(retrofit: Retrofit): GameApi =
            retrofit.create(GameApi::class.java)
    }
}
