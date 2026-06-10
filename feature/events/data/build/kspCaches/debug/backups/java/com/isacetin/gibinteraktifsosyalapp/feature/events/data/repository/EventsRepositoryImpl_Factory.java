package com.isacetin.gibinteraktifsosyalapp.feature.events.data.repository;

import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.EventsApi;
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
public final class EventsRepositoryImpl_Factory implements Factory<EventsRepositoryImpl> {
  private final Provider<EventsApi> apiProvider;

  private EventsRepositoryImpl_Factory(Provider<EventsApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public EventsRepositoryImpl get() {
    return newInstance(apiProvider.get());
  }

  public static EventsRepositoryImpl_Factory create(Provider<EventsApi> apiProvider) {
    return new EventsRepositoryImpl_Factory(apiProvider);
  }

  public static EventsRepositoryImpl newInstance(EventsApi api) {
    return new EventsRepositoryImpl(api);
  }
}
