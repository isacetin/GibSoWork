package com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameConstants
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameException
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameResult
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.repository.GameRepository
import javax.inject.Inject

class SubmitGameScoreUseCase @Inject constructor(
    private val repository: GameRepository,
) {
    suspend operator fun invoke(score: Int): Result<GameResult> {
        if (score < 0) {
            return Result.failure(GameException.Validation("Skor negatif olamaz"))
        }

        val earnedPoints = score / GameConstants.POINT_SCORE_DIVISOR
        return repository.submitScore(
            gameId = GameConstants.GAME_ID,
            score = score,
            earnedPoints = earnedPoints,
        ).map { GameResult(score = score, earnedPoints = earnedPoints) }
    }
}
