package application.example.myturn

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import android.R.attr.button
import android.graphics.Color




class BackgroundColorMatcher private constructor(private val color: Int) :
    BoundedMatcher<View, AppCompatButton>(AppCompatButton::class.java) {
    override fun matchesSafely(button: AppCompatButton): Boolean {
        val buttonColor = button.background as ColorDrawable
        return buttonColor.color == color
    }

    override fun describeTo(description: Description) {
        description.appendText("with hint text color:")
            .appendValue(color)
    }

    companion object {
        fun hasBackgroundColor(color: Int): BackgroundColorMatcher {
            return BackgroundColorMatcher(color)
        }
    }
}