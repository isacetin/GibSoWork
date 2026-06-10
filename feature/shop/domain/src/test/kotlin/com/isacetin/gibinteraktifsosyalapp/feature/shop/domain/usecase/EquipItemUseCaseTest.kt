package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemRarity
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.repository.ShopRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class EquipItemUseCaseTest {
    private val repository: ShopRepository = mockk()
    private val useCase = EquipItemUseCase(repository)
    private val ownedHat = ShopItem(
        id = "item-cylinder", name = "Silindir Şapka", category = ItemCategory.HAT,
        rarity = ItemRarity.RARE, price = 0, isOwned = true, isEquipped = false,
    )

    @Test
    fun `equipping an owned item delegates to the repository`() = runTest {
        coEvery { repository.equipItem(ownedHat.id, ownedHat.category) } returns Result.success(Unit)

        val result = useCase(ownedHat)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.equipItem(ownedHat.id, ownedHat.category) }
    }

    @Test
    fun `equipping an already equipped item is a no-op`() = runTest {
        val equipped = ownedHat.copy(isEquipped = true)

        val result = useCase(equipped)

        assertTrue(result.isSuccess)
        coVerify(exactly = 0) { repository.equipItem(any(), any()) }
    }

    @Test
    fun `equipping an unowned item fails without calling the repository`() = runTest {
        val unowned = ownedHat.copy(isOwned = false)

        val result = useCase(unowned)

        assertTrue(result.isFailure)
        coVerify(exactly = 0) { repository.equipItem(any(), any()) }
    }
}
