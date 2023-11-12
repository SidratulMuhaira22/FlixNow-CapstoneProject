@file:Suppress("DEPRECATION")

package hera.flixnow.made.submission.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import hera.flixnow.made.core.utils.EspressoIdlingResource
import hera.flixnow.made.submission.R
import hera.flixnow.made.submission.ui.components.MainActivityBase
import hera.flixnow.made.submission.utils.RecyclerViewItemCount
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FlixNowInstrumentedTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivityBase::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMoviesBehavior() {
        //Load movie list
        Espresso.onView(withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.loading))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.rv_movie)).check(RecyclerViewItemCount(20))

        //Open detail movie at position 0 and add or remove to favorite
        Espresso.onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.fab_fav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.fab_fav)).perform(ViewActions.click())
        Espresso.pressBack()

        //Open detail movie at position 19 and add or remove to favorite
        Espresso.onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
        Espresso.onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                19,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.fab_fav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.fab_fav)).perform(ViewActions.click())
        Espresso.pressBack()
    }

    @Test
    fun loadTvShowsBehavior() {
        Espresso.onView(withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvFragment)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_tv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.loading))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.rv_tv)).check(RecyclerViewItemCount(20))

        //Open detail movie at position 0 and add or remove to favorite
        Espresso.onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.fab_fav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.fab_fav)).perform(ViewActions.click())
        Espresso.pressBack()

        //Open detail movie at position 19 and add or remove to favorite
        Espresso.onView(withId(R.id.rv_tv))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
        Espresso.onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                19,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.fab_fav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.fab_fav)).perform(ViewActions.click())
        Espresso.pressBack()
    }

    @Test
    fun loadFavoritesBehavior() {
        Espresso.onView(withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.favoriteFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.favoriteFragment)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("MOVIE")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("TV SHOW")).perform(ViewActions.click())
    }
}