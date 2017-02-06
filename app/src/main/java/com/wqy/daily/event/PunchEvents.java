package com.wqy.daily.event;

import com.wqy.daily.model.Event;

import java.util.List;

/**
 * Created by wqy on 17-2-6.
 */

public class PunchEvents {

    private List<Event> mEvents;

    public PunchEvents(List<Event> events) {
        mEvents = events;
    }

    public void setEvents(List<Event> events) {
        mEvents = events;
    }

    public List<Event> getEvents() {
        return mEvents;
    }
}
