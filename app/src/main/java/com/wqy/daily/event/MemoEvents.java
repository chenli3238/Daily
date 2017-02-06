package com.wqy.daily.event;

import com.wqy.daily.model.Memo;

import java.util.List;

/**
 * Created by wqy on 17-2-6.
 */

public class MemoEvents {

    private List<Memo> mMemos;

    public MemoEvents(List<Memo> memos) {
        mMemos = memos;
    }

    public List<Memo> getMemos() {
        return mMemos;
    }

    public void setMemos(List<Memo> memos) {
        mMemos = memos;
    }
}
