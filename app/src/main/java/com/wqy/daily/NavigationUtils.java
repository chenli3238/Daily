package com.wqy.daily;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wqy.daily.model.Bigday;
import com.wqy.daily.model.Memo;
import com.wqy.daily.presenter.CreateBigdayActivity;
import com.wqy.daily.presenter.CreateMemoActivity;

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

    public static Intent memo(Context context, Memo memo) {
        Intent intent = new Intent(context, CreateMemoActivity.class);
        intent.putExtra(CreateMemoActivity.EXTRA_MEMO_ID, memo.getId());
        return intent;
    }
}
