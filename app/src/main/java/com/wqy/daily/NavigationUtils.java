package com.wqy.daily;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wqy.daily.model.Bigday;
import com.wqy.daily.presenter.CreateBigdayActivity;

/**
 * Created by wqy on 17-2-19.
 */

public class NavigationUtils {

    public static Intent editBigday(Context context) {
        Intent intent = new Intent(context, CreateBigdayActivity.class);
        intent.setAction(context.getString(R.string.action_edit));
        return intent;
    }

    public static Intent viewBigday(Context context, Bigday bigday) {
        Intent intent = new Intent(context, CreateBigdayActivity.class);
        intent.setAction(context.getString(R.string.action_view));
        intent.putExtra(CreateBigdayActivity.EXTRA_BIGDAY_ID, bigday.getId());
        return intent;
    }
}
