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
import static com.example.calculator.OrientationChangeAction.orientationLandscape;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class LandscapeTest {
    @Rule
    public ActivityTestRule<Landscape> myRule = new ActivityTestRule<>(Landscape.class);
    String _buffer;

    //Rotate to landscape
    /*Reference: Nathan Barraille, "An Espresso ViewAction that changes the orientation of the screen",
     * 2015, available at: https://gist.github.com/nbarraille/03e8910dc1d415ed9740
     * */
    @Before
    public void rotate(){
        onView(isRoot()).perform(orientationLandscape());
    }

    @Test
    public void onCreate() {
    }

    class AssertPair{
        public AssertPair(String que, String res){
            question = que;
            result = res;
        }
        String question;
        String result;
    }

    // Test buttons work or not
    @Test
    public void testPerform(){
        //test basic cases from main activity

        perform("0.1+2-3×4÷5×(6-7-8+9)%");
        onView(withId(R.id.textView3)).check(matches(withText("0.1+2-3×4÷5×(6-7-8+9)%")));
        onView(withId(R.id.result)).perform(click());
        onView(withId(R.id.textView5)).check(matches(withText("Result: "+"2.1")));
        onView(withId(R.id.lac)).perform(click());

        //test if function and variable buttons work or not.
        perform("xyzgπe,log(cos(sin(tan(log(pow(");
        onView(withId(R.id.textView3)).check(matches(withText("xyzgπe,log(cos(sin(tan(log(pow(")));

        //Test if backspace work or not. (the whole function like 'log(' will be deleted)
        onView(withId(R.id.lback)).perform(click());
        onView(withId(R.id.textView3)).check(matches(withText("xyzgπe,log(cos(sin(tan(log(")));
        onView(withId(R.id.lac)).perform(click());
        onView(withId(R.id.textView3)).check(matches(withText("0")));
    }

    @Test
    public void testTrigonometric(){
        LinkedList<AssertPair> items = new LinkedList<>();

        // Test trigonometric function
        items.add(new AssertPair("tan(45)","1"));
        items.add(new AssertPair("tan(0)", "0"));
        items.add(new AssertPair("tan(180)", "0"));
        items.add(new AssertPair("tan(405)", "1"));
        items.add(new AssertPair("tan(-315)", "1"));
        items.add(new AssertPair("sin(0)", "0"));
        items.add(new AssertPair("sin(30)", "0.5"));
        items.add(new AssertPair("sin(390)", "0.5"));
        items.add(new AssertPair("sin(-330)", "0.5"));
        items.add(new AssertPair("sin(90)", "1"));
        items.add(new AssertPair("sin(145)", "0.573576436351"));
        items.add(new AssertPair("sin(180)", "0"));
        items.add(new AssertPair("sin(200)", "-0.342020143326"));
        items.add(new AssertPair("sin(270)", "-1"));
        items.add(new AssertPair("sin(300)", "-0.866025403784"));
        items.add(new AssertPair("cos(0)", "1"));
        items.add(new AssertPair("cos(60)", "0.5"));
        items.add(new AssertPair("cos(90)", "0"));
        items.add(new AssertPair("cos(180)", "-1"));
        items.add(new AssertPair("cos(240)", "-0.5"));
        items.add(new AssertPair("cos(360)", "1"));

        for(AssertPair pair:items) {
            onView(withId(R.id.textView3)).perform(setTextInTextView(pair.question));
            onView(withId(R.id.result)).perform(click());
            onView(withId(R.id.textView5)).check(matches(withText("Result: "+pair.result)));
            onView(withId(R.id.lac)).perform(click());
        }

        // Test wrong input of trigonometric function
        LinkedList<AssertPair> wrongItems = new LinkedList<>();

        wrongItems.add(new AssertPair("tan(90)", "Mathematical Error"));
        wrongItems.add(new AssertPair("tan(270)", "Mathematical Error"));

        for(AssertPair pair:wrongItems){
            onView(withId(R.id.textView3)).perform(setTextInTextView(pair.question));
            onView(withId(R.id.result)).perform(click());
            onView(withId(R.id.textView3)).check(matches(withText(pair.result)));
            onView(withId(R.id.lac)).perform(click());
        }
    }

    @Test
    public void testCalculation(){
        LinkedList<AssertPair> items = new LinkedList<>();

        // Test log function.
        items.add(new AssertPair("log(2,1)","0"));
        items.add(new AssertPair("log(2,2)","1"));
        items.add(new AssertPair("log(2,0.5)","-1"));
        items.add(new AssertPair("log(2,5)","2.321928094887"));
        items.add(new AssertPair("log(2,0.2)","-2.321928094887"));

        // Test power function
        items.add(new AssertPair("pow(2,0)","1"));
        items.add(new AssertPair("pow(2,1)","2"));
        items.add(new AssertPair("pow(2,3)","8"));
        items.add(new AssertPair("pow(2,0.5)","1.414213562373"));
        items.add(new AssertPair("pow(2,(-1))","0.5"));
        items.add(new AssertPair("pow(2,(-3))","0.125"));

        items.add(new AssertPair("pow(-2,0)","1"));
        items.add(new AssertPair("pow(-2,1)","-2"));
        items.add(new AssertPair("pow(-2,3)","-8"));
        items.add(new AssertPair("pow(-2,(-1))","-0.5"));
        items.add(new AssertPair("pow(-2,(-3))","-0.125"));

        for (AssertPair pair:items){
            onView(withId(R.id.textView3)).perform(setTextInTextView(pair.question));
            onView(withId(R.id.result)).perform(click());
            onView(withId(R.id.textView5)).check(matches(withText("Result: "+pair.result)));
            onView(withId(R.id.lac)).perform(click());
        }

    }

    @Test
    public void testMoveAndBackspace() {
        perform("0.1+2-3×4÷5×(6-7-8+9)%");
        onView(withId(R.id.lback)).perform(click());
        onView(withId(R.id.textView3)).check(matches(withText("0.1+2-3×4÷5×(6-7-8+9)")));
        onView(withId(R.id.lla)).perform(click());
        onView(withId(R.id.lback)).perform(click());
        onView(withId(R.id.textView3)).check(matches(withText("0.1+2-3×4÷5×(6-7-8+)")));
        onView(withId(R.id.lac)).perform();
    }


    public void perform (String text){
        _buffer = text;
        next();
    }
    public void next(){
        _buffer = _buffer.trim();
        while (!_buffer.isEmpty()){
            char firstChar = _buffer.charAt(0);
            if(firstChar=='+') {
                onView(withId(R.id.ladd)).perform(click());
            }
            if(firstChar=='-') {
                onView(withId(R.id.lminus)).perform(click());
            }
            if(firstChar=='×') {
                onView(withId(R.id.lmul)).perform(click());
            }
            if (firstChar=='÷') {
                onView(withId(R.id.ldiv)).perform(click());
            }
            if (firstChar=='(') {
                onView(withId(R.id.llb)).perform(click());
            }
            if (firstChar==')') {
                onView(withId(R.id.lrb)).perform(click());
            }
            if (firstChar=='%'){
                onView(withId(R.id.lpercent)).perform(click());
            }
            if (firstChar=='.'){
                onView(withId(R.id.ldot)).perform(click());
            }
            if (firstChar=='='){
                onView(withId(R.id.lequal)).perform(click());
            }
            if (firstChar=='0'){
                onView(withId(R.id.l0)).perform(click());
            }
            if (firstChar=='1'){
                onView(withId(R.id.l1)).perform(click());
            }
            if (firstChar=='2'){
                onView(withId(R.id.l2)).perform(click());
            }
            if (firstChar=='3'){
                onView(withId(R.id.l3)).perform(click());
            }
            if (firstChar=='4'){
                onView(withId(R.id.l4)).perform(click());
            }
            if (firstChar=='5'){
                onView(withId(R.id.l5)).perform(click());
            }
            if (firstChar=='6'){
                onView(withId(R.id.l6)).perform(click());
            }
            if (firstChar=='7'){
                onView(withId(R.id.l7)).perform(click());
            }
            if (firstChar=='8'){
                onView(withId(R.id.l8)).perform(click());
            }
            if (firstChar=='9'){
                onView(withId(R.id.l9)).perform(click());
            }
            if (firstChar=='t'){
                _buffer = _buffer.substring(3);
                onView(withId(R.id.ltan)).perform(click());
            }
            if (firstChar=='c'){
                _buffer = _buffer.substring(3);
                onView(withId(R.id.lcos)).perform(click());
            }
            if(firstChar=='s'){
                _buffer = _buffer.substring(3);
                onView(withId(R.id.lsin)).perform(click());
            }
            if(firstChar=='l'){
                _buffer = _buffer.substring(3);
                onView(withId(R.id.llog)).perform(click());
            }
            if (firstChar=='p'){
                _buffer = _buffer.substring(3);
                onView(withId(R.id.lpow)).perform(click());
            }
            if (firstChar=='π'){
                onView(withId(R.id.lpi)).perform(click());
            }
            if (firstChar=='e'){
                onView(withId(R.id.le)).perform(click());
            }
            if (firstChar=='x'){
                onView(withId(R.id.lx)).perform(click());
            }
            if (firstChar=='y'){
                onView(withId(R.id.ly)).perform(click());
            }
            if (firstChar=='z'){
                onView(withId(R.id.lz)).perform(click());
            }
            if (firstChar=='g'){
                onView(withId(R.id.lg)).perform(click());
            }
            if (firstChar==','){
                onView(withId(R.id.lcomma)).perform(click());
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