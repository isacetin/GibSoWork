package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemRarity
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.PurchaseException
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.PurchaseOutcome
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.repository.ShopRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PurchaseItemUseCaseTest {
    private val repository: ShopRepository = mockk()
    private val useCase = PurchaseItemUseCase(repository)
    private val cowboyHat = ShopItem(
        id = "item-cowboy", name = "Kovboy Şapkası", category = ItemCategory.HAT,
        rarity = ItemRarity.RARE, price = 50, isOwned = false, isEquipped = false,
    )

    @Test
    fun `TC-04 - balance equal to price purchases successfully and balance reaches zero`() = runTest {
        coEvery { repository.purchaseItem(cowboyHat.id) } returns Result.success(0)

        val result = useCase(cowboyHat, balance = 50)

        assertTrue(result.isSuccess)
        val outcome = result.getOrThrow() as PurchaseOutcome.Success
        assertEquals(0, outcome.newBalance)
        assertTrue(outcome.item.isOwned)
        coVerify(exactly = 1) { repository.purchaseItem(cowboyHat.id) }
    }

    @Test
    fun `TC-04 - balance one below price is rejected without calling the repository`() = runTest {
        val result = useCase(cowboyHat, balance = 49)

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull() as PurchaseException.InsufficientBalance
        assertEquals(400, error.code)
        assertEquals(1, error.shortfall)
        coVerify(exactly = 0) { repository.purchaseItem(any()) }
    }

    @Test
    fun `TC-04 - balance one above price purchases successfully and balance is one`() = runTest {
        coEvery { repository.purchaseItem(cowboyHat.id) } returns Result.success(1)

        val result = useCase(cowboyHat, balance = 51)

        assertTrue(result.isSuccess)
        val outcome = result.getOrThrow() as PurchaseOutcome.Success
        assertEquals(1, outcome.newBalance)
    }

    @Test
    fun `TC-05 - already owned item is rejected without calling the repository`() = runTest {
        val owned = cowboyHat.copy(isOwned = true)

        val result = useCase(owned, balance = 1000)

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull()
        assertEquals(PurchaseException.AlreadyOwned, error)
        assertEquals(409, (error as PurchaseException).code)
        coVerify(exactly = 0) { repository.purchaseItem(any()) }
    }

    @Test
    fun `repository failure is propagated as Result failure`() = runTest {
        val error = RuntimeException("network error")
        coEvery { repository.purchaseItem(cowboyHat.id) } returns Result.failure(error)

        val result = useCase(cowboyHat, balance = 50)

        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}
