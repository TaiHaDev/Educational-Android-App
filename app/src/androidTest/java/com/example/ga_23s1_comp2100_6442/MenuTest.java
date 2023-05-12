package com.example.ga_23s1_comp2100_6442;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MenuTest {

    @Before
    public void launchBigfiterPage() {
        // Launch the activity under test before each test
        ActivityScenario.launch(BigfilterPage.class);
    }

    @Test
    public void testCoursesPage() throws InterruptedException {
        // Click the courses button
        Espresso.onView(ViewMatchers.withId(R.id.coursesMenu)).perform(click());

        // Wait for the new page to appear
        Thread.sleep(2000);

        // Verify that the expected button is displayed on the new page
        Espresso.onView(ViewMatchers.withId(R.id.btn_computer_science)).check(matches(isDisplayed()));

        // Verify that the navigation menu is present
        Espresso.onView(ViewMatchers.withId(R.id.coursesMenu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.forumsMenu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.profileMenu)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    @Test
    public void testForumsPage() throws InterruptedException {
        // Click the courses button
        Espresso.onView(ViewMatchers.withId(R.id.forumsMenu)).perform(click());

        // Wait for the new page to appear
        Thread.sleep(2000);

        // Verify that the navigation menu is present
        Espresso.onView(ViewMatchers.withId(R.id.coursesMenu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.forumsMenu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.profileMenu)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    @Before
    public void launchBigfiterPage3() {
        // Launch the activity under test before each test
        ActivityScenario.launch(BigfilterPage.class);
    }

    @Test
    public void testProfilePage() throws InterruptedException {
        // Click the courses button
        Espresso.onView(ViewMatchers.withId(R.id.profileMenu)).perform(click());

        // Wait for the new page to appear
        Thread.sleep(2000);

        // Verify that the expected button is displayed on the new page
        Espresso.onView(ViewMatchers.withId(R.id.logOutBtn)).check(matches(isDisplayed()));

        // Verify that the navigation menu is present
        Espresso.onView(ViewMatchers.withId(R.id.coursesMenu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.forumsMenu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.profileMenu)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

}