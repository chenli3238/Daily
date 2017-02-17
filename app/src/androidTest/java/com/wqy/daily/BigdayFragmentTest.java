package com.wqy.daily;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.wqy.daily.presenter.BigdayFragment;
import com.wqy.daily.presenter.MainActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by wqy on 17-2-17.
 */
@RunWith(AndroidJUnit4.class)
public class BigdayFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    public void test() {

    }

}
