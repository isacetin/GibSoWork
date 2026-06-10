package com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation;

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase.EquipItemUseCase;
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase.GetShopItemsUseCase;
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase.PurchaseItemUseCase;
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
public final class ShopViewModel_Factory implements Factory<ShopViewModel> {
  private final Provider<GetShopItemsUseCase> getShopItemsUseCaseProvider;

  private final Provider<PurchaseItemUseCase> purchaseItemUseCaseProvider;

  private final Provider<EquipItemUseCase> equipItemUseCaseProvider;

  private ShopViewModel_Factory(Provider<GetShopItemsUseCase> getShopItemsUseCaseProvider,
      Provider<PurchaseItemUseCase> purchaseItemUseCaseProvider,
      Provider<EquipItemUseCase> equipItemUseCaseProvider) {
    this.getShopItemsUseCaseProvider = getShopItemsUseCaseProvider;
    this.purchaseItemUseCaseProvider = purchaseItemUseCaseProvider;
    this.equipItemUseCaseProvider = equipItemUseCaseProvider;
  }

  @Override
  public ShopViewModel get() {
    return newInstance(getShopItemsUseCaseProvider.get(), purchaseItemUseCaseProvider.get(), equipItemUseCaseProvider.get());
  }

  public static ShopViewModel_Factory create(
      Provider<GetShopItemsUseCase> getShopItemsUseCaseProvider,
      Provider<PurchaseItemUseCase> purchaseItemUseCaseProvider,
      Provider<EquipItemUseCase> equipItemUseCaseProvider) {
    return new ShopViewModel_Factory(getShopItemsUseCaseProvider, purchaseItemUseCaseProvider, equipItemUseCaseProvider);
  }

  public static ShopViewModel newInstance(GetShopItemsUseCase getShopItemsUseCase,
      PurchaseItemUseCase purchaseItemUseCase, EquipItemUseCase equipItemUseCase) {
    return new ShopViewModel(getShopItemsUseCase, purchaseItemUseCase, equipItemUseCase);
  }
}
