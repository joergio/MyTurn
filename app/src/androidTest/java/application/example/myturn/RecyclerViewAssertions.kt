package application.example.myturn

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import junit.framework.Assert.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
/*
class ExampleTest {

    @Test
    fun exampleTest() {
        // Verifies a row in the recycler view contains the expected text
        onView(withId(R.id.recycler_views_id))
            .check(withRowContaining(withText("Expected text in a row")))
    }
}*/

object RecyclerViewAssertions {

    /**
     * Provides a RecyclerView assertion based on a view matcher. This allows you to
     * validate whether a RecyclerView contains a row in memory without scrolling the list.
     *
     * @param viewMatcher - an Espresso ViewMatcher for a descendant of any row in the recycler.
     * @return an Espresso ViewAssertion to check against a RecyclerView.
     */
    fun withRowContaining(viewMatcher: Matcher<View>): ViewAssertion {
        return object : ViewAssertion {

            override fun check(view: View, noViewException: NoMatchingViewException?) {
                if (noViewException != null) {
                    throw noViewException
                }

                assertTrue(view is RecyclerView)

                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter
                for (position in 0 until adapter!!.itemCount) {
                    val itemType = adapter.getItemViewType(position)
                    val viewHolder = adapter.createViewHolder(recyclerView, itemType)
                    adapter.bindViewHolder(viewHolder, position)

                    if (viewHolderMatcher(hasDescendant(viewMatcher)).matches(viewHolder)) {
                        return  // Found a matching row
                    }
                }

                fail("No match found")
            }
        }
    }
    fun isOrdered(): ViewAssertion {

            return object : ViewAssertion {

                override fun check(view: View, noViewException: NoMatchingViewException?) {
                    if (noViewException != null) {
                        throw noViewException
                    }

                    assertTrue(view is RecyclerView)

                    val recyclerView = view as RecyclerView
                    val adapter = recyclerView.adapter
                    var last = ""
                    for (position in 1 until adapter!!.itemCount) {
                        val itemType = adapter.getItemViewType(position)
                        Log.w("DEBUG",itemType.toString())
                        if (itemType.toString().compareTo(last) < 0)
                            fail("No match found")
                        last = itemType.toString()
                        val viewHolder = adapter.createViewHolder(recyclerView, itemType)
                        adapter.bindViewHolder(viewHolder, position)
                    }

                    return
                }
            }

    }
    fun checkItemAtPosition(position: Int, viewMatcher: Matcher<View>): ViewAssertion {
        return object : ViewAssertion {

            override fun check(view: View, noViewException: NoMatchingViewException?) {
                if (noViewException != null) {
                    throw noViewException
                }

                assertTrue(view is RecyclerView)

                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter
                val itemType = adapter!!.getItemViewType(position)
                val viewHolder = adapter.createViewHolder(recyclerView, itemType)
                adapter.bindViewHolder(viewHolder, position)
                if (viewHolderMatcher(hasDescendant(viewMatcher)).matches(viewHolder)) {
                    return  // Found a matching row
                }
                fail("No match found")
            }
        }
    }

    /**
     * Creates matcher for view holder with given item view matcher.
     *
     * @param itemViewMatcher a item view matcher which is used to match item.
     * @return a matcher which matches a view holder containing item matching itemViewMatcher.
     */
    private fun viewHolderMatcher(itemViewMatcher: Matcher<View>): Matcher<RecyclerView.ViewHolder> {
        return object : TypeSafeMatcher<RecyclerView.ViewHolder>() {

            override fun matchesSafely(viewHolder: RecyclerView.ViewHolder): Boolean {
                return itemViewMatcher.matches(viewHolder.itemView)
            }

            override fun describeTo(description: Description) {
                description.appendText("holder with view: ")
                itemViewMatcher.describeTo(description)
            }
        }
    }
}