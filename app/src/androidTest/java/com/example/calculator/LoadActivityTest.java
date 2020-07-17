package com.example.calculator;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.calculator.OrientationChangeAction.orientationLandscape;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
public class LoadActivityTest {
    @Rule
    public ActivityTestRule<Landscape> myRule = new ActivityTestRule<>(Landscape.class);

    @Before
    public void rotate(){
        onView(isRoot()).perform(orientationLandscape());
    }

    class AssertPair{
        public AssertPair(String fun, String que, String res){
            function = fun;
            question = que;
            result = res;
        }
        String function;
        String question;
        String result;
    }

    @Test
    public void testFunction(){
        LinkedList<AssertPair> items = new LinkedList<>();
        items.add(new AssertPair("2x+1","f(2)","5"));
        items.add(new AssertPair("2x-y","f(2,1)","3"));
        items.add(new AssertPair("2x-y+z","f(1,2,3)","3"));
        items.add(new AssertPair("2x-y+z×g","f(1,2,3,4)","12"));

        for (AssertPair pair:items) {
            // add new function
            openContextualActionModeOverflowMenu();
            onView(withText("New")).perform(click());
            onView(withText("f(x,y,z,g)")).perform(click());
            onView(withId(R.id.textView3)).perform(setTextInTextView(pair.function));

            //Save the function
            openContextualActionModeOverflowMenu();
            onView(withText("Save")).perform(click());
            onView(withId(R.id.lac)).perform(click());

            // Load the function
            openContextualActionModeOverflowMenu();
            onView(withText("Load")).perform(click());
            onView(withText("f(x,y,z,g)="+pair.function)).perform(swipeLeft());
            onView(withText("Open")).perform(click());

            // Test if the function is what we saved previously
            onView(withId(R.id.textView5)).check(matches(withText("f(x,y,z,g)="+pair.function)));

            // Set question text in out put bar
            onView(withId(R.id.textView3)).perform(setTextInTextView(pair.question));

            // Test if the output is expected
            onView(withId(R.id.result)).perform(click());
            onView(withId(R.id.textView5)).check(matches(withText("Result: "+pair.result)));

            // Delete function
            openContextualActionModeOverflowMenu();
            onView(withText("Load")).perform(click());
            onView(withText("f(x,y,z,g)="+pair.function)).perform(swipeLeft());
            onView(withText("Delete")).perform(click());

            // Scroll back to root
            onView(isRoot()).perform(pressBack());
        }
    }

    // Test if the listview of functions is empty, because if it contains some function,
    // it might influence the operation above

    @Test(expected = NoMatchingViewException.class)
    public void testDeletion() throws NoMatchingViewException {
        openContextualActionModeOverflowMenu();
        onView(withText("Load")).perform(click());
        onView(withText(containsString("f(x,y,z,g)="))).perform(swipeLeft());
        //TODO: write more test cases if you need
    }


    /*
     * Pavneet_Singh, "How to set a value to the textview using Espresso in Android",
     * Nov 10 2017, Available at:
     * https://stackoverflow.com/questions/47216782/how-to-set-a-value-to-the-textview-using-espresso-in-android*/
    public static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }
}