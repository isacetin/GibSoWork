package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.di

import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.ShopApi
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.repository.ShopRepositoryImpl
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.repository.ShopRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopDataModule {

    @Binds
    abstract fun bindShopRepository(impl: ShopRepositoryImpl): ShopRepository

    companion object {
        @Provides
        @Singleton
        fun provideShopApi(retrofit: Retrofit): ShopApi = retrofit.create(ShopApi::class.java)
    }
}
