package com.my.retrofittest;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import com.my.retrofittest.adapter.AlbumAdapter;
import com.my.retrofittest.receiver.NetworkConnectionReceiver;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * Created by Tejas Shah on 30/10/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private static final int ITEM_BELOW_THE_FOLD = 25;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void scrollToItemBelowFold_checkItsText() {

        // First scroll to the specific position.
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.<AlbumAdapter.AlbumViewHolder>scrollToPosition(ITEM_BELOW_THE_FOLD));
    }

    @Before
    public  void testInternetAvailable() throws Exception{
        boolean isConnect = NetworkConnectionReceiver.isConnected(mainActivityActivityTestRule.getActivity().getApplicationContext());
        Assert.assertTrue(isConnect);
    }

}
