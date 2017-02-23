package com.wqy.daily.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wqy.daily.R;
import com.wqy.daily.StringUtils;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DatasetChangedEvent;
import com.wqy.daily.interfaces.ImageLoader;
import com.wqy.daily.model.Memo;
import com.wqy.daily.mvp.ViewImpl;
import com.wqy.daily.widget.DateTimePickerFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-8.
 */

public class CreateMemoView extends ViewImpl implements ImageLoader {

    public static final String TAG = CreatePunchView.class.getSimpleName();

    @BindView(R.id.cmemo_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.cmemo_content)
    EditText etContent;

    private Memo mMemo;

    private List<Target> mImageHolders;

    private ProgressDialog mProgressDialog;

    @Override
    public int getResId() {
        return R.layout.activity_create_memo;
    }

    @Override
    public int getMenuId() {
        return R.menu.activity_cmemo;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mImageHolders = new ArrayList<>();
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO: 17-2-8 nav to parent activity and fragment
                NavUtils.navigateUpFromSameTask((Activity) getContext());
                return true;
            case R.id.cmemo_image:
                RxBus.get().post(BusAction.CMEMO_PICK_IMAGE, "");
                return true;
            case R.id.cmemo_remind:
                showDateTimeDialog();
                return true;
            case R.id.cmemo_confirm:
                // TODO: 17-2-8 create a memo
                confirm();
                return true;
            default:
                return false;

        }
    }

    @Produce(tags = {@Tag(BusAction.SET_CMEMO_TITLE)})
    public String setActivityTitle() {
        return getContext().getString(R.string.title_cmemo);
    }

    private void showDateTimeDialog() {
        DialogFragment fragment = new DateTimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DateTimePickerFragment.ARG_EVENT_TAG, BusAction.CMEMO_TIME);
        mIPresenter.showDialog(DateTimePickerFragment.TAG, fragment);
    }

    public void confirm() {
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Snackbar.make(mRootView, getContext().getString(R.string.cmemo_empty_content),
                    Snackbar.LENGTH_LONG).show();
        }
        mMemo.setCreatedAt(new Date());
        mMemo.setContent(content);
        mProgressDialog = ProgressDialog.show(getContext(), null, getContext().getString(R.string.saving_data));
        RxBus.get().post(BusAction.SAVE_MEMO, mMemo);
    }

    @Subscribe(tags = {@Tag(BusAction.MEMO_DATASET_CHANGED)})
    public void onDatasetChanged(DatasetChangedEvent event) {
        Log.d(TAG, "onDatasetChanged: ");
        mProgressDialog.dismiss();
        ((Activity) getContext()).finish();
    }

    @Subscribe(tags = {@Tag(BusAction.CMEMO_SET_MEMO)})
    public void setMemo(Memo memo) {
        mMemo = memo;
        StringUtils.renderText(getContext(), mMemo.getContent(),
                mImageHolders, this);
    }

    @Subscribe(tags = {@Tag(BusAction.CMEMO_TIME)})
    public void setRemindTime(Date event) {
        mMemo.setRemindTime(event);
    }

    @Subscribe(tags = {@Tag(BusAction.CMEMO_IMAGE)})
    public void setImage(Uri uri) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                appendImage(uri, new BitmapDrawable(getContext().getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                appendImage(uri, errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                appendImage(uri, placeHolderDrawable);
            }
        };
        mImageHolders.add(target);
        load(uri, target);
    }

    public void appendImage(Uri uri, Drawable d) {
        String s = StringUtils.encodeImageSpan(uri);
        SpannableString ss = new SpannableString(s);
        ImageSpan span = StringUtils.getSpan(d);
        ss.setSpan(span, 0, s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        etContent.append(ss);

        if (d.getIntrinsicWidth() >= etContent.getWidth()) {
            etContent.append("\n");
        }
    }

    @Override
    public void load(Uri uri, Target target) {
        Picasso.with(getContext())
                .load(uri)
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .resize(etContent.getWidth(), etContent.getWidth())
                .onlyScaleDown()
                .into(target);
    }
}
