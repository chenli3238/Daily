package com.wqy.daily.interfaces;

import java.util.Date;

/**
 * Created by wqy on 17-2-22.
 */

public interface Remindable {
    Long getId();

    Date getRemindTime();

    String getTitle();
}
