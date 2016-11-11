package com.example.akarpenko.basekotlin;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by akarpenko on 24.10.16.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkFragmentContainer() {
        onView(withId(R.id.container)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void checkToolbar() {
        onView(withId(R.id.toolbar)).check(ViewAssertions.matches(isDisplayed()));
    }

}
