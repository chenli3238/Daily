package com.wqy.daily.model;

import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by wqy on 17-2-6.
 */

public class Punch {

    @Id
    private Long id;

    private Date date;

    private long duration;

    private Event event;
}
