package com.treblig.footballmatch

import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
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
import  android.support.test.espresso.intent.Intents.intended
import  android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.treblig.footballmatch.ui.detail.DetailActivity
import org.junit.After

import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MatchActivityInstrumentedTest {
    @Rule
    @JvmField
    var matchActivityRule = ActivityTestRule(MatchActivity::class.java)

    private lateinit var mBottomNavigation: BottomNavigationView
    private var instrumentationCtx: Context? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        val activity = matchActivityRule.activity
        mBottomNavigation = activity.findViewById(R.id.navigation) as BottomNavigationView
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun chooseLeague_whenChooseMatchDetail_ShouldOpenMatchDetails() {
        delay(1000)
        chooseLeague("English League Championship")
        openMatchDetail(1)
        hasComponentWithClassName(DetailActivity::class.java.name)
    }

    @Test
    fun chooseLeagueAndOpenNextMatch_whenChooseMatchDetail_ShouldOpenMatchDetails() {
        delay(1000)
        chooseLeague("English League Championship")
        clickNextMatch()
        openMatchDetail(1)
        hasComponentWithClassName(DetailActivity::class.java.name)
    }

    @Test
    fun chooseLeagueAndOpenPrevMatch_whenChooseMatchDetail_ShouldOpenMatchDetails() {
        delay(1000)
        chooseLeague("English League Championship")
        clickPrevMatch()
        openMatchDetail(1)
        hasComponentWithClassName(DetailActivity::class.java.name)
    }

    @Test
    fun markMatchAsFavorite() {
        delay(1000)
        chooseLeague("English League Championship")
        openMatchDetail(1)
        markOrUnmarkAsFavorite()
        pressBack()
    }

    @Test
    fun removeMatchAsFavorite() {
        delay(1000)
        clickFavoriteMatch()
        delay(1000)
        val matchEventDB = MatchEventDB(context = instrumentationCtx)
        val list = matchEventDB.getFavorites().blockingFirst()
        if (list != null && list.isNotEmpty()) {
            openMatchDetail(0)
            markOrUnmarkAsFavorite()
            pressBack()
        }
    }

    @Test
    fun markFourFavoriteAndUnMarkAllFavorite() {
        val matchEventDB = MatchEventDB(context = instrumentationCtx)
        delay(1000)
        chooseLeague("Italian Serie A")
        for (position in 0..3) {
            openMatchDetail(position)
            markOrUnmarkAsFavorite()
            pressBack()
        }
        clickFavoriteMatch()
        val list = matchEventDB.getFavorites().blockingFirst()
        if (list != null && list.isNotEmpty()) {
            for (position in list.size-1 downTo 0) {
                openMatchDetail(position)
                markOrUnmarkAsFavorite()
                pressBack()
            }
        }
    }


    private fun delay(time: Long) {
        Thread.sleep(time)
    }

    private fun chooseLeague(name: String) {
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText(name)).check(matches(isDisplayed()))
        onView(withText(name)).perform(click())
        delay(1000)
    }

    private fun openMatchDetail(position: Int) {
        onView(withId(recyclerView))
                .check(matches(isDisplayed()))
        onView(withId(recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
        onView(withId(recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
        delay(1000)
    }

    private fun hasComponentWithClassName(className: String) {
        intended(hasComponent(className))
    }

    private fun clickNextMatch() {
        onView(AllOf.allOf(
                withText(matchActivityRule.activity.getString(R.string.next_match)),
                isDescendantOfA(withId(R.id.navigation)),
                isDisplayed()))
                .perform(click())
        delay(1000)
    }

    private fun clickPrevMatch() {
        onView(AllOf.allOf(
                withText(matchActivityRule.activity.getString(R.string.prev_match)),
                isDescendantOfA(withId(R.id.navigation)),
                isDisplayed()))
                .perform(click())
        delay(1000)
    }

    private fun clickFavoriteMatch() {
        onView(AllOf.allOf(
                withText(matchActivityRule.activity.getString(R.string.favorite)),
                isDescendantOfA(withId(R.id.navigation)),
                isDisplayed()))
                .perform(click())
    }

    private fun markOrUnmarkAsFavorite() {
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        delay(1000)
    }

    private fun pressBack() {
        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .check(matches(isDisplayed()))
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        delay(1000)
    }
}
