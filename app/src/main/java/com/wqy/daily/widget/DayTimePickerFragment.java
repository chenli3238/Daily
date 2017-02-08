package com.wqy.daily.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hwangjr.rxbus.RxBus;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DayTimePickerEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-8.
 */

public class DayTimePickerFragment extends DialogFragment {

    public static final int ADD_ALL_POSITION = 0;

    @BindView(R.id.picker_dt_list)
    ListView mListView;

    @BindView(R.id.picker_dt_time)
    TimePicker mTimePicker;

    List<Integer> mDays;

    int mHour;

    int mMinute;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_day_time_picker, null);
        ButterKnife.bind(this, view);

        mListView.setAdapter(new CheckBoxListAdapter(Arrays.asList(
                getContext().getResources().getStringArray(R.array.day_of_week)), ADD_ALL_POSITION));
        mListView.setOnItemClickListener((parent, view12, position, id) -> {
            CheckBox cb = (CheckBox) view12.findViewById(R.id.picker_dt_cb);
            cb.setChecked(!cb.isChecked());
        });

        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener((view1, hourOfDay, minute) -> {
            mHour = hourOfDay;
            mMinute = minute;
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton(R.string.cpunch_confirm, (dialog, which) -> {
                    long[] a = mListView.getCheckedItemIds();
                    mDays = new ArrayList<>(a.length);
                    for (int i = 0; i < a.length; i++) {
                        if (i != ADD_ALL_POSITION) {
                            mDays.add(i);
                        }
                    }
                    RxBus.get().post(BusAction.DAY_TIME_PICKER_RESULT, new DayTimePickerEvent(mDays, mHour, mMinute));
                });
        return builder.create();
    }

    class CheckBoxListAdapter extends BaseAdapter {

        private List<String> mList;

        private int mCheckAllPosition;

        public CheckBoxListAdapter(List<String> list, int checkAllPosition) {
            mList = list;
            mCheckAllPosition = checkAllPosition;
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.picker_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(mList.get(position));

            if (position == mCheckAllPosition) {
                holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    ListView lV = (ListView) parent;
                    for (int i = 0; i < mList.size() && i != mCheckAllPosition; i++) {
                        lV.setItemChecked(i, isChecked);
                    }
                });
            }

            return convertView;
        }

        class ViewHolder {

            CheckBox checkBox;

            TextView textView;

            public ViewHolder(View itemView) {
                checkBox = (CheckBox) itemView.findViewById(R.id.picker_dt_cb);
                textView = (TextView) itemView.findViewById(R.id.picker_dt_entry);
            }
        }
    }
}
