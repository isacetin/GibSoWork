package com.isacetin.gibinteraktifsosyalapp.feature.events.data.di;

import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.EventsApi;
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
public final class EventsDataModule_Companion_ProvideEventsApiFactory implements Factory<EventsApi> {
  private final Provider<Retrofit> retrofitProvider;

  private EventsDataModule_Companion_ProvideEventsApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public EventsApi get() {
    return provideEventsApi(retrofitProvider.get());
  }

  public static EventsDataModule_Companion_ProvideEventsApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new EventsDataModule_Companion_ProvideEventsApiFactory(retrofitProvider);
  }

  public static EventsApi provideEventsApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(EventsDataModule.Companion.provideEventsApi(retrofit));
  }
}
