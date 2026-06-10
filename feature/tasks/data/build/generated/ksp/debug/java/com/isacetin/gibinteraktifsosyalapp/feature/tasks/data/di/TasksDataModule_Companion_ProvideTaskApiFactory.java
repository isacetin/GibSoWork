package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.di;

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.TaskApi;
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
public final class TasksDataModule_Companion_ProvideTaskApiFactory implements Factory<TaskApi> {
  private final Provider<Retrofit> retrofitProvider;

  private TasksDataModule_Companion_ProvideTaskApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public TaskApi get() {
    return provideTaskApi(retrofitProvider.get());
  }

  public static TasksDataModule_Companion_ProvideTaskApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new TasksDataModule_Companion_ProvideTaskApiFactory(retrofitProvider);
  }

  public static TaskApi provideTaskApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(TasksDataModule.Companion.provideTaskApi(retrofit));
  }
}
