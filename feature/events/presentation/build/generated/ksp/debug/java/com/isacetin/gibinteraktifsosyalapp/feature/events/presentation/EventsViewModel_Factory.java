package com.isacetin.gibinteraktifsosyalapp.feature.events.presentation;

import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase.CreateEventUseCase;
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase.GetEventsUseCase;
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase.JoinEventUseCase;
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase.LeaveEventUseCase;
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
public final class EventsViewModel_Factory implements Factory<EventsViewModel> {
  private final Provider<GetEventsUseCase> getEventsUseCaseProvider;

  private final Provider<CreateEventUseCase> createEventUseCaseProvider;

  private final Provider<JoinEventUseCase> joinEventUseCaseProvider;

  private final Provider<LeaveEventUseCase> leaveEventUseCaseProvider;

  private EventsViewModel_Factory(Provider<GetEventsUseCase> getEventsUseCaseProvider,
      Provider<CreateEventUseCase> createEventUseCaseProvider,
      Provider<JoinEventUseCase> joinEventUseCaseProvider,
      Provider<LeaveEventUseCase> leaveEventUseCaseProvider) {
    this.getEventsUseCaseProvider = getEventsUseCaseProvider;
    this.createEventUseCaseProvider = createEventUseCaseProvider;
    this.joinEventUseCaseProvider = joinEventUseCaseProvider;
    this.leaveEventUseCaseProvider = leaveEventUseCaseProvider;
  }

  @Override
  public EventsViewModel get() {
    return newInstance(getEventsUseCaseProvider.get(), createEventUseCaseProvider.get(), joinEventUseCaseProvider.get(), leaveEventUseCaseProvider.get());
  }

  public static EventsViewModel_Factory create(Provider<GetEventsUseCase> getEventsUseCaseProvider,
      Provider<CreateEventUseCase> createEventUseCaseProvider,
      Provider<JoinEventUseCase> joinEventUseCaseProvider,
      Provider<LeaveEventUseCase> leaveEventUseCaseProvider) {
    return new EventsViewModel_Factory(getEventsUseCaseProvider, createEventUseCaseProvider, joinEventUseCaseProvider, leaveEventUseCaseProvider);
  }

  public static EventsViewModel newInstance(GetEventsUseCase getEventsUseCase,
      CreateEventUseCase createEventUseCase, JoinEventUseCase joinEventUseCase,
      LeaveEventUseCase leaveEventUseCase) {
    return new EventsViewModel(getEventsUseCase, createEventUseCase, joinEventUseCase, leaveEventUseCase);
  }
}
