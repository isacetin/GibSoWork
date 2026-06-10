package com.isacetin.gibinteraktifsosyalapp.feature.game.data.repository;

import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.GameApi;
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
public final class GameRepositoryImpl_Factory implements Factory<GameRepositoryImpl> {
  private final Provider<GameApi> apiProvider;

  private GameRepositoryImpl_Factory(Provider<GameApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public GameRepositoryImpl get() {
    return newInstance(apiProvider.get());
  }

  public static GameRepositoryImpl_Factory create(Provider<GameApi> apiProvider) {
    return new GameRepositoryImpl_Factory(apiProvider);
  }

  public static GameRepositoryImpl newInstance(GameApi api) {
    return new GameRepositoryImpl(api);
  }
}
