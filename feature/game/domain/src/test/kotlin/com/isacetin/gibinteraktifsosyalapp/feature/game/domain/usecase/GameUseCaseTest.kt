package com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameConstants
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameException
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.repository.GameRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameUseCaseTest {
    private val repository = FakeGameRepository()

    @Test
    fun `positive score submits and returns earned points`() = runTest {
        val result = SubmitGameScoreUseCase(repository)(1840)

        assertTrue(result.isSuccess)
        assertEquals(1840, repository.submittedScore)
        assertEquals(18, repository.submittedPoints)
        assertEquals(GameConstants.GAME_ID, repository.submittedGameId)
        assertEquals(18, result.getOrThrow().earnedPoints)
    }

    @Test
    fun `negative score returns 400 validation and does not submit`() = runTest {
        val result = SubmitGameScoreUseCase(repository)(-5)

        assertTrue(result.isFailure)
        assertEquals(400, (result.exceptionOrNull() as GameException.Validation).code)
        assertFalse(repository.submitCalled)
    }

    @Test
    fun `leaderboard sorts by score descending and limits to twenty`() = runTest {
        repository.leaderboard = (1..25).map { index ->
            LeaderboardEntry(rank = index, displayName = "Oyuncu $index", score = index * 10)
        }

        val result = GetLeaderboardUseCase(repository)()

        assertTrue(result.isSuccess)
        val entries = result.getOrThrow()
        assertEquals(20, entries.size)
        assertEquals(250, entries.first().score)
        assertEquals(60, entries.last().score)
        assertEquals(1, entries.first().rank)
    }

    private class FakeGameRepository : GameRepository {
        var leaderboard: List<LeaderboardEntry> = emptyList()
        var submitCalled = false
        var submittedGameId: String? = null
        var submittedScore: Int? = null
        var submittedPoints: Int? = null

        override suspend fun getLeaderboard(limit: Int): Result<List<LeaderboardEntry>> =
            Result.success(leaderboard)

        override suspend fun getBestScore(): Result<Int> = Result.success(0)

        override suspend fun getBalance(): Result<Int> = Result.success(0)

        override suspend fun submitScore(gameId: String, score: Int, earnedPoints: Int): Result<Unit> {
            submitCalled = true
            submittedGameId = gameId
            submittedScore = score
            submittedPoints = earnedPoints
            return Result.success(Unit)
        }
    }
}
