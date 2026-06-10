package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.repository;

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.TaskApi;
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
public final class TasksRepositoryImpl_Factory implements Factory<TasksRepositoryImpl> {
  private final Provider<TaskApi> apiProvider;

  private TasksRepositoryImpl_Factory(Provider<TaskApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public TasksRepositoryImpl get() {
    return newInstance(apiProvider.get());
  }

  public static TasksRepositoryImpl_Factory create(Provider<TaskApi> apiProvider) {
    return new TasksRepositoryImpl_Factory(apiProvider);
  }

  public static TasksRepositoryImpl newInstance(TaskApi api) {
    return new TasksRepositoryImpl(api);
  }
}
