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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    private static final String USERNAME = "lecturer1@l.com";
    private static final String PASSWORD = "123456789";

    @Before
    public void launchLoginPage() {
        // Launch the activity under test before each test
        ActivityScenario.launch(LoginPage.class);
    }

    @Test
    public void testLoginSuccess() throws InterruptedException {
        // Logout
        Espresso.onView(ViewMatchers.withId(R.id.profileMenu)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.logOutBtn)).perform(click());

        // Turn on the switch
        Espresso.onView(ViewMatchers.withId(R.id.switch2)).perform(click());

        // Enter valid login credentials and click the login button
        Espresso.onView(ViewMatchers.withId(R.id.userName)).perform(typeText(USERNAME));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(typeText(PASSWORD));

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.loginBtn)).perform(click());

        // Wait for the new page to appear
        Thread.sleep(2000);

        // Verify that the expected button is displayed on the new page
        Espresso.onView(ViewMatchers.withId(R.id.btn_computer_science)).check(matches(isDisplayed()));

        // Verify that the navigation menu is present
        Espresso.onView(ViewMatchers.withId(R.id.coursesMenu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.forumsMenu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.profileMenu)).check(matches(isDisplayed()));
    }
}