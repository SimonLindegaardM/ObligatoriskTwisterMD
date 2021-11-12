package com.example.obligatorisktwistermd

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.obligatorisktwistermd", appContext.packageName)

        onView(withText("Log In")).check(matches(isDisplayed()));
        onView(withId(R.id.emailInputField))
            .perform(clearText())
            .perform(typeText("slm@test.test"));
        onView(withId(R.id.passwordInputField))
            .perform(clearText())
            .perform(typeText("123456"))
        onView(withId(R.id.sign_in)).perform(click());
        Thread.sleep(1000)
        onView(withId(R.id.sign_in)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }
}