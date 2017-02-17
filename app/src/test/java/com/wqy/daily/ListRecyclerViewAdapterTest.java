package com.wqy.daily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqy.daily.adapter.ListRecyclerViewAdapter;
import com.wqy.daily.adapter.ViewHolder;
import com.wqy.daily.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by wqy on 17-2-17.
 */

@RunWith(MockitoJUnitRunner.class)
public class ListRecyclerViewAdapterTest {

    @Mock
    Context mContext;

    @Mock
    RecyclerView mRecyclerView;

    ListRecyclerViewAdapter<String> mAdapter;

    @Before
    public void setup() {
        mAdapter = new ListRecyclerViewAdapter<String>(mRecyclerView) {
            @Override
            public ViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bigday_backward_item, null);
                return new ViewHolder<String>(view) {
                    @Override
                    public void bindView(String data) {

                    }
                };
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        when(mRecyclerView.getAdapter()).thenReturn(mAdapter);
    }

    private List<String> createList(int n) {
        List<String> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add("test");
        }
        return list;
    }

    @Test
    public void testSetDataList() {
        int n1 = 10;
        int n2 = 5;
        int n3 = 6;

        assertNotNull(mRecyclerView);
        assertNotNull(mAdapter);
        assertNotNull(mRecyclerView.getAdapter());

        List<String> list1 = createList(n1);
        mAdapter.setDataList(list1);
        assertEquals(n1, list1.size());
        assertEquals(n1, mAdapter.getItemCount());

        List<String> list2 = createList(n2);
        mAdapter.setDataList(list2);
        assertEquals(n2, mAdapter.getItemCount());

        List<String> list3 = createList(3);
        mAdapter.setDataList(list3);
        assertEquals(n3, mAdapter.getItemCount());
    }

    @Test
    public void testAppendData() {
        int n1 = 10;
        int n2 = 5;
        int n3 = 5;

        List<String> list1 = createList(n1);
        mAdapter.appendData(list1);
        assertEquals(n1, mAdapter.getItemCount());

        List<String> list2 = createList(n2);
        mAdapter.appendData(list2);
        assertEquals(n1 + n2, mAdapter.getItemCount());

        List<String> list3 = createList(3);
        mAdapter.appendData(list3);
        assertEquals(n1 + n2 + n3, mAdapter.getItemCount());
    }
}