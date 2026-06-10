package com.isacetin.gibinteraktifsosyalapp.feature.game.presentation;

import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase.GetGameOverviewUseCase;
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase.GetLeaderboardUseCase;
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase.SubmitGameScoreUseCase;
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
public final class GameViewModel_Factory implements Factory<GameViewModel> {
  private final Provider<GetGameOverviewUseCase> getOverviewProvider;

  private final Provider<GetLeaderboardUseCase> getLeaderboardProvider;

  private final Provider<SubmitGameScoreUseCase> submitScoreProvider;

  private GameViewModel_Factory(Provider<GetGameOverviewUseCase> getOverviewProvider,
      Provider<GetLeaderboardUseCase> getLeaderboardProvider,
      Provider<SubmitGameScoreUseCase> submitScoreProvider) {
    this.getOverviewProvider = getOverviewProvider;
    this.getLeaderboardProvider = getLeaderboardProvider;
    this.submitScoreProvider = submitScoreProvider;
  }

  @Override
  public GameViewModel get() {
    return newInstance(getOverviewProvider.get(), getLeaderboardProvider.get(), submitScoreProvider.get());
  }

  public static GameViewModel_Factory create(Provider<GetGameOverviewUseCase> getOverviewProvider,
      Provider<GetLeaderboardUseCase> getLeaderboardProvider,
      Provider<SubmitGameScoreUseCase> submitScoreProvider) {
    return new GameViewModel_Factory(getOverviewProvider, getLeaderboardProvider, submitScoreProvider);
  }

  public static GameViewModel newInstance(GetGameOverviewUseCase getOverview,
      GetLeaderboardUseCase getLeaderboard, SubmitGameScoreUseCase submitScore) {
    return new GameViewModel(getOverview, getLeaderboard, submitScore);
  }
}
