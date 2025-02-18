package com.ntvinhdev.kodeco.jetreddit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.ntvinhdev.kodeco.jetreddit.screens.TrendingTopic
import com.ntvinhdev.kodeco.jetreddit.screens.TrendingTopicModel
import com.yourcompany.android.jetreddit.util.Tags
import org.junit.Rule
import org.junit.Test

class TrendingItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun trending_item_is_displayed() {
        val topic = TrendingTopicModel("Compose Tutorial", R.drawable.jetpack_composer)

        composeTestRule.setContent { TrendingTopic(topic) }
        composeTestRule.onNodeWithTag(Tags.TRENDING_ITEM)
            .assertIsDisplayed()
    }
}
