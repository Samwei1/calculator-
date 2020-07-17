package com.example.calculator;

import android.view.View;
import android.widget.TextView;

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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.calculator.OrientationChangeAction.orientationPortrait;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> myRule = new ActivityTestRule<>(MainActivity.class);
    private String _buffer;
    class AssertPair{
        public AssertPair(String que, String res){
            question = que;
            result = res;
        }
        String question;
        String result;
    }

    // Rotate to portrait before testing
    /*Reference: Nathan Barraille, "An Espresso ViewAction that changes the orientation of the screen",
    * 2015, available at: https://gist.github.com/nbarraille/03e8910dc1d415ed9740
    * */
    @Before
    public void rotate(){
        onView(isRoot()).perform(orientationPortrait());
    }

    @Test
    public void onCreate() {
    }

    // Test perform is to test all buttons and textviews which will have certain content.
    @Test
    public void TestButtonPerform(){

        // Buttons of numbers and operations
        perform("0.1+2-3×4÷5×(6-7-8+9)%");
        onView(withId(R.id.textView)).check((matches(withText("0.1+2-3×4÷5×(6-7-8+9)%"))));
        perform("=");
        onView(withId(R.id.textView)).check(matches(withText("2.1")));

        // Test backspace button
        onView(withId(R.id.back)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("2.")));

        // Test all clear button
        onView(withId(R.id.ac)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("0")));
    }

    // Test all cases of calculation on activity main.
    @Test
    public void TestCalculation() {
        LinkedList<AssertPair> items = new LinkedList<>();
        // positive number calculation
        items.add(new AssertPair("1+1","2"));
        // brackets
        items.add(new AssertPair("1-(1+1)","-1"));
        // negative number
        items.add(new AssertPair("1+(-1)","0"));
        items.add(new AssertPair("1×(-2)","-2"));
        // decimals
        items.add(new AssertPair("1+1.5","2.5"));
        items.add(new AssertPair("0.1÷0.5","0.2"));
        // percentage
        items.add(new AssertPair("2×5%","0.1"));

        //Test exception cases
        items.add(new AssertPair("3÷0","Infinity"));
        items.add(new AssertPair("1+-1","Mathematical Error"));

        for(AssertPair pair:items){
            onView(withId(R.id.textView)).perform(setTextInTextView(pair.question));
            onView(withId(R.id.equal)).perform(click());
            onView(withId(R.id.textView)).check(matches(withText(pair.result)));
        }
    }

    //A espresso method to check and then click particular button.
    public void perform (String text){
        _buffer = text;
        next();
    }
    public void next(){
        _buffer = _buffer.trim();
        while (!_buffer.isEmpty()){
            char firstChar = _buffer.charAt(0);
            if(firstChar=='+') {
                onView(withId(R.id.add)).perform(click());
            }
            if(firstChar=='-') {
                onView(withId(R.id.minus)).perform(click());
            }
            if(firstChar=='×') {
                onView(withId(R.id.mul)).perform(click());
            }
            if (firstChar=='÷') {
                onView(withId(R.id.div)).perform(click());
            }
            if (firstChar=='(') {
                onView(withId(R.id.lb)).perform(click());
            }
            if (firstChar==')') {
                onView(withId(R.id.rb)).perform(click());
            }
            if (firstChar=='%'){
                onView(withId(R.id.per)).perform(click());
            }
            if (firstChar=='.'){
                onView(withId(R.id.point)).perform(click());
            }
            if (firstChar=='='){
                onView(withId(R.id.equal)).perform(click());
            }
            if (firstChar=='0'){
                onView(withId(R.id.c0)).perform(click());
            }
            if (firstChar=='1'){
                onView(withId(R.id.c1)).perform(click());
            }
            if (firstChar=='2'){
                onView(withId(R.id.c2)).perform(click());
            }
            if (firstChar=='3'){
                onView(withId(R.id.c3)).perform(click());
            }
            if (firstChar=='4'){
                onView(withId(R.id.c4)).perform(click());
            }
            if (firstChar=='5'){
                onView(withId(R.id.c5)).perform(click());
            }
            if (firstChar=='6'){
                onView(withId(R.id.c6)).perform(click());
            }
            if (firstChar=='7'){
                onView(withId(R.id.c7)).perform(click());
            }
            if (firstChar=='8'){
                onView(withId(R.id.c8)).perform(click());
            }
            if (firstChar=='9'){
                onView(withId(R.id.c9)).perform(click());
            }
            _buffer = _buffer.substring(1);
        }
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