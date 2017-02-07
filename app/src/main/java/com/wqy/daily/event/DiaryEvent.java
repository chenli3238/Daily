package com.wqy.daily.event;

import com.wqy.daily.model.Diary;

import java.util.List;

/**
 * Created by wqy on 17-2-7.
 */

public class DiaryEvent {

    List<Diary> mDiaries;

    public DiaryEvent(List<Diary> diaries) {
        mDiaries = diaries;
    }

    public List<Diary> getDiaries() {
        return mDiaries;
    }

    public void setDiaries(List<Diary> diaries) {
        mDiaries = diaries;
    }
}
