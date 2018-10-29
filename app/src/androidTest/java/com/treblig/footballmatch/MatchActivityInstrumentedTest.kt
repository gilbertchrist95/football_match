package com.treblig.footballmatch

import android.support.design.widget.BottomNavigationView
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.treblig.footballmatch.R.id.*
import com.treblig.footballmatch.db.MatchEventDB
import com.treblig.footballmatch.ui.match_event.MatchActivity
import org.hamcrest.core.AllOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchActivityInstrumentedTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MatchActivity::class.java)

    private lateinit var mBottomNavigation: BottomNavigationView

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val activity = activityRule.activity
        mBottomNavigation = activity.findViewById(R.id.navigation) as BottomNavigationView

        val res = activity.resources
    }

    /**
     * Scenario: Choose League and Open Match Detail
     *
     * 1. Choose league to English League Championship
     * 2. Observe list of match
     * 3. Open match detail at particular position
*/
    @Test
    fun chooseLeague_whenChooseMatchDetail_ShouldOpenMatchDetails() {
        delay(2000)
        chooseLaeague("English League Championship")
        delay(2000)
        openMatchDetail(1)
        delay(2000)
    }

    /**
     * Scenario: Go to Next Match and Open Match Detail
     *
     * 1. Choose league to English League Championship
     * 2. Observe list of match
     * 3. Click next match
     * 4. Open match detail at particular position
     */
    @Test
    fun chooseLeagueAndOpenNextMatch_whenChooseMatchDetail_ShouldOpenMatchDetails() {
        delay(2000)
        chooseLaeague("English League Championship")
        delay(2000)
        clickNextMatch()
        delay(2000)
        openMatchDetail(1)
        delay(3000)
    }

    /**
     * Scenario: Go to Prev Match and Open Match Detail
     *
     * 1. Change league to English League Championship
     * 2. Observe list of match
     * 3. Click Prev match
     * 4. Open match detail at particular position
     */
    @Test
    fun chooseLeagueAndOpenPrevMatch_whenChooseMatchDetail_ShouldOpenMatchDetails() {
        delay(2000)
        chooseLaeague("English League Championship")
        delay(2000)
        clickPrevMatch()
        delay(2000)
        openMatchDetail(1)
        delay(2000)
    }

    /**
     * Scenario: Mark Match as Favorite
     *
     * 1. Change league to English League Championship
     * 2. Observe list of match
     * 3. Open match detail at particular position
     * 4. Click favorite
     */
    @Test
    fun markMatchAsFavorite() {
        delay(2000)
        chooseLaeague("English League Championship")
        delay(2000)
        openMatchDetail(1)
        delay(2000)
        markOrUnmarkAsFavorite()
        delay(2000)
        back()
        delay(2000)
    }

    /**
     * Scenario: Unset Match as Favorite
     *
     * 1. Click Favorite
     * 2. Observe list of match
     * 3. when favorite is exist, open match detail at  position 0
     * 4. Unmark favorite
     */
    @Test
    fun removeMatchAsFavorite() {
        delay(2000)
        clickFavoriteMatch()
        delay(2000)
        var list = MatchEventDB.getFavorites(activityRule.activity)
        if (list != null && list.isNotEmpty()) {
            openMatchDetail(0)
            delay(2000)
            markOrUnmarkAsFavorite()
            back()
        }
        delay(2000)
    }


    private fun delay(time: Long) {
        try {
            Thread.sleep(time)
        } catch (ex: Exception) {

        }
    }

    private fun chooseLaeague(name: String) {
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText(name)).perform(click())
    }

    private fun openMatchDetail(position: Int) {
        onView(withId(recyclerView))
                .check(matches(isDisplayed()))
        onView(withId(recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
        onView(withId(recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    private fun clickNextMatch() {
        onView(AllOf.allOf(
                withText(activityRule.activity.getString(R.string.next_match)),
                isDescendantOfA(withId(R.id.navigation)),
                isDisplayed()))
                .perform(click())
    }

    private fun clickPrevMatch() {
        onView(AllOf.allOf(
                withText(activityRule.activity.getString(R.string.prev_match)),
                isDescendantOfA(withId(R.id.navigation)),
                isDisplayed()))
                .perform(click())
    }

    private fun clickFavoriteMatch() {
        onView(AllOf.allOf(
                withText(activityRule.activity.getString(R.string.favorite)),
                isDescendantOfA(withId(R.id.navigation)),
                isDisplayed()))
                .perform(click())
    }

    private fun markOrUnmarkAsFavorite() {
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
    }

    private fun back() {
        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .check(matches(isDisplayed()))
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}
