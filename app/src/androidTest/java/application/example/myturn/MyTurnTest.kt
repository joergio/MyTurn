package application.example.myturn

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import application.example.myturn.RecyclerViewAssertions.checkItemAtPosition
import application.example.myturn.RecyclerViewAssertions.isOrdered
import application.example.myturn.RecyclerViewAssertions.withRowContaining
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.graphics.Color
import android.graphics.drawable.Drawable

@RunWith(AndroidJUnit4::class)
@LargeTest
class MyTurnTest {


    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    /*
    * Check if items in login page are displayed
    * */
    @Test fun displayedItemsTest(){
        onView(withId(R.id.username))
            .check(
                ViewAssertions.matches(
                    isDisplayed()
                )
            )
        onView(withId(R.id.password))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.loginButton)).check(ViewAssertions.matches(isDisplayed()))
    }

    /*
    * Check if hints in login page are correct
    * */
    @Test fun hintTest(){
        onView(withId(R.id.username)).check(ViewAssertions.matches(withHint(R.string.password_hint)))
        onView(withId(R.id.password)).check(ViewAssertions.matches(withHint(R.string.password_hint)))
    }

    /*
    * Check if elements on login page are in the corrent place
    * */
    @Test fun rightPlaceElementsTest(){
        onView(withId(R.id.username)).check(isCompletelyAbove(withId(R.id.password)))
        onView(withId(R.id.password)).check(isCompletelyAbove(withId(R.id.loginButton)))
        onView(withId(R.id.joinButton)).check(isCompletelyAbove(withId(R.id.loginButton)))
    }

    /*
    * Check successfully authentication
    * */
    @Test
    fun authTest(){
        onView(withId(R.id.username)).perform(replaceText("mario@mario.net"))
        onView(withId(R.id.password)).perform(replaceText("mariomario"))
        onView(withId(R.id.loginButton)).perform(click())
        Thread.sleep(10000) //wait for authentication
        onView(withId(R.id.logoutButton)).check(ViewAssertions.matches(isDisplayed()))
    }

    /*
    * Check if not successfully authentication shows toast message
    * */
    @Test
    fun failedAuthTest(){
        onView(withId(R.id.username)).perform(replaceText("mario@mario.net"))
        onView(withId(R.id.password)).perform(replaceText("password"))
        onView(withId(R.id.loginButton)).perform(click())
        Thread.sleep(2500) //wait for authentication
        onView(withId(R.id.loginButton)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText(R.string.failed)).inRoot(ToastMatcher()).check(matches(isDisplayed()));

    }

    /*
    * Check if every turn is displayed in order
    * */
    @Test
    fun turnDisplayedTest(){
        authTest()
        Thread.sleep(2000)
        onView(
            withId(R.id.recyclerViewTurns)
        )
            .check(
                withRowContaining(
                    withText( "08:50")
                )
            )
            .check(
                withRowContaining(
                    withText( "09:30")
                )
            )
            .check(
                withRowContaining(
                    withText( "11:30")
                )
            )
            .check(isOrdered())
    }

    /*
    * Check if every button in turn details view is correctly displayed
    * */
    @Test
    fun turnDetailsUITest(){
        authTest()
        Thread.sleep(2000)
        onView(withId(R.id.recyclerViewTurns))
            .perform(
                RecyclerViewActions.
                    actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,click()
                    )
            )
        Thread.sleep(2000)
        // Check if changeButton and takeButton are in order
        onView(withId(R.id.changeButton)).check(
            isCompletelyAbove(
                withId(R.id.takeButton)
            )
        )
        onView(withId(R.id.descriptionText))
            .check(
                isCompletelyAbove(
                    withId(R.id.mailText)
                )
            )
        onView(withId(R.id.mailText))
            .check(
                isCompletelyAbove(
                    withId(R.id.timeText)
                )
            )
    }

    /*
    *   Check change of a turn
     */
    @Test
    fun changeTest(){
        authTest()
        Thread.sleep(2000)
        onView(withId(R.id.recyclerViewTurns))
            .check(
                checkItemAtPosition(
                    0,
                    BackgroundColorMatcher.hasBackgroundColor(Color.GREEN)
                )
            )
        onView(withId(R.id.recyclerViewTurns))
            .perform(
                RecyclerViewActions.
                    actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,click()
                    )
            )
        Thread.sleep(1000)
        onView(withId(R.id.changeButton))
            .perform(
                click()
            )
        Thread.sleep(2000)
        onView(withId(R.id.recyclerViewTurns))
            .check(
                checkItemAtPosition(
                    0,
                    BackgroundColorMatcher.hasBackgroundColor(Color.YELLOW)
                )
            )
        Thread.sleep(2000)
    }
    /*
    * Check if a turn is successfully taken
    *
    */
    @Test
    fun takeTest(){
        changeTest()
        onView(withId(R.id.recyclerViewTurns))
            .perform(
                RecyclerViewActions.
                    actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,click()
                    )
            )
        Thread.sleep(1000)
        onView(withId(R.id.takeButton))
            .perform(
                click()
            )
        Thread.sleep(2000)
        onView(withId(R.id.recyclerViewTurns))
            .check(
                checkItemAtPosition(
                    0,
                    BackgroundColorMatcher.hasBackgroundColor(Color.GREEN)
                )
            )
        Thread.sleep(2000)
    }

    /*
    *Check if failed change of the turn shows toast message
    *
     */
    @Test
    fun failedChangeTest(){
        changeTest()
        onView(withId(R.id.recyclerViewTurns))
            .perform(
                RecyclerViewActions.
                    actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,click()
                    )
            )
        Thread.sleep(1000)
        onView(withId(R.id.changeButton))
            .perform(
                click()
            )
        onView(withText("Cannot leave this turn"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()));


    }


    /*
    *Check if failed take of the turn shows toast message
    *
     */
    @Test
    fun failedTakeTest(){
        takeTest()
        onView(withId(R.id.recyclerViewTurns))
            .perform(
                RecyclerViewActions.
                    actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,click()
                    )
            )
        Thread.sleep(1000)
        onView(withId(R.id.takeButton))
            .perform(
                click()
            )
        onView(withText("Cannot take this turn"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()));

    }
}
