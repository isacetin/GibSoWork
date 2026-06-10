package com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation;

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase.CompleteTaskUseCase;
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase.GetTasksUseCase;
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
public final class TasksViewModel_Factory implements Factory<TasksViewModel> {
  private final Provider<GetTasksUseCase> getTasksUseCaseProvider;

  private final Provider<CompleteTaskUseCase> completeTaskUseCaseProvider;

  private TasksViewModel_Factory(Provider<GetTasksUseCase> getTasksUseCaseProvider,
      Provider<CompleteTaskUseCase> completeTaskUseCaseProvider) {
    this.getTasksUseCaseProvider = getTasksUseCaseProvider;
    this.completeTaskUseCaseProvider = completeTaskUseCaseProvider;
  }

  @Override
  public TasksViewModel get() {
    return newInstance(getTasksUseCaseProvider.get(), completeTaskUseCaseProvider.get());
  }

  public static TasksViewModel_Factory create(Provider<GetTasksUseCase> getTasksUseCaseProvider,
      Provider<CompleteTaskUseCase> completeTaskUseCaseProvider) {
    return new TasksViewModel_Factory(getTasksUseCaseProvider, completeTaskUseCaseProvider);
  }

  public static TasksViewModel newInstance(GetTasksUseCase getTasksUseCase,
      CompleteTaskUseCase completeTaskUseCase) {
    return new TasksViewModel(getTasksUseCase, completeTaskUseCase);
  }
}
