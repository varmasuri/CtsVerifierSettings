package com.varma.ctsverifiersettings;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.widget.Toast;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CtsVerifierTests {

    private final static String SCROLLING = "ScrollTo";
    private final static String CLICKING = "ClickOn";
    private static final long TIMEOUT = 15000;

    UiDevice mDevice;

    @Before
    public void setUp() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.varma.ctsverifiersettings", appContext.getPackageName());
        Intent intent = appContext.getPackageManager()
                .getLaunchIntentForPackage("com.varma.ctsverifiersettings");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Clear out any previous instances
        appContext.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg("com.varma.ctsverifiersettings").depth(0)), TIMEOUT);



    }


    @Test
    public void testMain() throws UiObjectNotFoundException {

        Bundle extras = InstrumentationRegistry.getArguments();

//        String param = extras.getString(SCROLLING);

        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("am start -n com.android.cts.verifier/.CtsVerifierActivity");
        try {
            if (extras != null) {
                if (extras.containsKey(SCROLLING)) {
                    String value = String.valueOf(Integer.parseInt(extras.get(SCROLLING).toString()));
                    scrolling(value);
                    clickingObject(value);
                }
            } else
                Toast.makeText(Tag, "Expected parameters, please provide it correctly", Toast.LENGTH_SHORT).show();

        } catch (UiObjectNotFoundException e) {

            Log.d("MyActivity", "Ui object not found" );

        }

    }

    private void scrolling(String value) throws UiObjectNotFoundException {

        new UiScrollable(new UiSelector().text(value));


    }

    private void clickingObject(String value) throws UiObjectNotFoundException {

        UiObject clicking = mDevice.findObject(new UiSelector().text(value));
        clicking.clickAndWaitForNewWindow();

    }


    @After
    public void tearDown() {
//        mDevice.pressHome();
    }
}
