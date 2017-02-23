package com.wqy.daily.interfaces;

import java.util.Date;

/**
 * Created by wqy on 17-2-22.
 */

public interface Remindable {
    int getRemindId();

    Date getRemindTime();

    String getTitle();
}
