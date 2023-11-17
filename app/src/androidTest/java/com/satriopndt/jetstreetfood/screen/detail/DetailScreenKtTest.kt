package com.satriopndt.jetstreetfood.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.satriopndt.jetstreetfood.R
import com.satriopndt.jetstreetfood.model.OrderFood
import com.satriopndt.jetstreetfood.model.StreetFood
import com.satriopndt.jetstreetfood.onNodeWithStringId
import com.satriopndt.jetstreetfood.ui.theme.JetStreetFoodTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val fakeDataOrderFood = OrderFood(
        streetFood = StreetFood(
            1,
            "Mango Sticky Rice",
            com.satriopndt.jetstreetfood.R.drawable.mango_sticky,
            13000,
            "Mango Sticky Rice merupakan hidangan Thailand yang terdiri dari nasi ketan yang dimasak dengan santan dan disajikan dengan potongan mangga matang di atasnya. Kombinasi manis dari mangga dan kelembutan nasi membuat hidangan ini sangat memikat."
        ),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetStreetFoodTheme {
                DetailContent(
                    photoUrl = fakeDataOrderFood.streetFood.photoUrl,
                    name = fakeDataOrderFood.streetFood.name,
                    basePrice = fakeDataOrderFood.streetFood.price,
                    count = fakeDataOrderFood.count,
                    description = fakeDataOrderFood.streetFood.description,
                    onBackClick = { },
                    addCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDelayed() {
        composeTestRule.onNodeWithText(fakeDataOrderFood.streetFood.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                com.satriopndt.jetstreetfood.R.string.required_price,
                fakeDataOrderFood.streetFood.price
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataOrderFood.streetFood.description).assertIsDisplayed()
    }

    @Test
    fun increastProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_stillZero() {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }


}