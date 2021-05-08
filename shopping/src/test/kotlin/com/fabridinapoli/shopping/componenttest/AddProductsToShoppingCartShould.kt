package com.fabridinapoli.shopping.componenttest

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AddProductsToShoppingCartShould : BaseComponentTest() {

    @DisplayName(
        """
        GIVEN a shopping cart
        WHEN a customer adds a product into it
        THEN should calculate the total price and decrease stock
    """
    )
    @Test
    fun `calculate total price and decrease stock`() {

    }
}
