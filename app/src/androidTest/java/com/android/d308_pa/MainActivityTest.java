package com.android.d308_pa;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.d308_pa.UI.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void correctPasswordFormat() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertTrue(activity.validatePassword("Password1!"));
        });
    }
    @Test
    public void tooShort() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertFalse(activity.validatePassword("Pass"));
        });
    }
}
