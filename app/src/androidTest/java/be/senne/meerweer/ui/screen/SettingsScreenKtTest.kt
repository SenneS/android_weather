package be.senne.meerweer.ui.screen

import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        val items = listOf(
            SettingToggleGroupEntry<Int>(
                "Item 1",
                1
            ),
            SettingToggleGroupEntry<Int>(
                "Item 2",
                2
            )
        )


        composeTestRule.setContent {
            SettingToggle(
                name = "",
                selectedIndex = 0,
                items = items,
                onToggle = {
                }
            )
        }
    }

    @Test
    fun verifySettingToggle() {
        composeTestRule.onNodeWithTag("ToggleItems").assertExists()
        composeTestRule.onAllNodesWithTag("ToggleButton").assertAll(hasClickAction())
    }
}