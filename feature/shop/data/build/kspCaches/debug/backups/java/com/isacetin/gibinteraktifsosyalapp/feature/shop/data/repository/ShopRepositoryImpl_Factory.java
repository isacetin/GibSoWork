package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.repository;

import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.ShopApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ShopRepositoryImpl_Factory implements Factory<ShopRepositoryImpl> {
  private final Provider<ShopApi> apiProvider;

  private ShopRepositoryImpl_Factory(Provider<ShopApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public ShopRepositoryImpl get() {
    return newInstance(apiProvider.get());
  }

  public static ShopRepositoryImpl_Factory create(Provider<ShopApi> apiProvider) {
    return new ShopRepositoryImpl_Factory(apiProvider);
  }

  public static ShopRepositoryImpl newInstance(ShopApi api) {
    return new ShopRepositoryImpl(api);
  }
}
