package com.isacetin.gibinteraktifsosyalapp.feature.game.data.di;

import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.GameApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
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
public final class GameDataModule_Companion_ProvideGameApiFactory implements Factory<GameApi> {
  private final Provider<Retrofit> retrofitProvider;

  private GameDataModule_Companion_ProvideGameApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public GameApi get() {
    return provideGameApi(retrofitProvider.get());
  }

  public static GameDataModule_Companion_ProvideGameApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new GameDataModule_Companion_ProvideGameApiFactory(retrofitProvider);
  }

  public static GameApi provideGameApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(GameDataModule.Companion.provideGameApi(retrofit));
  }
}
