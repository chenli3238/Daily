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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

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
import com.wqy.daily.widget.BooleanPickerFragment;
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

    public static final String TAG = CreateMemoView.class.getSimpleName();

    @BindView(R.id.cmemo_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.cmemo_content)
    EditText etContent;

    @BindView(R.id.cmemo_remind_time)
    TextView tvRemindTime;

    private Memo mMemo;

    private List<Target> mImageHolders;

    private ProgressDialog mProgressDialog;

    private int imageMaxSize = 0;

    private boolean creatingMemo = true;

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
        etContent.post(() -> {
            imageMaxSize = getImageMaxWidth();
            showText();
        });
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
                ((Activity) getContext()).finish();
                return true;
            case R.id.cmemo_image:
                RxBus.get().post(BusAction.CMEMO_PICK_IMAGE, "");
                return true;
            case R.id.cmemo_remind:
                showDateTimeDialog();
                return true;
            case R.id.cmemo_confirm:
                // TODO: 17-2-8 create a editMemo
                confirm();
                return true;
            case R.id.cmemo_delete:
                showDeleteDialog();
                return true;
            default:
                return false;

        }
    }

    @Override
    public void setMenu(Menu menu) {
        super.setMenu(menu);
        if (creatingMemo) {
            setCreating();
        } else {
            setEditing();
        }
    }

    @Produce(tags = {@Tag(BusAction.SET_CMEMO_TITLE)})
    public String setActivityTitle() {
        return getContext().getString(R.string.title_cmemo);
    }


    @Subscribe(tags = {@Tag(BusAction.CMEMO_CREATING)})
    public void setCreatingMemo(Boolean event) {
        if (event) {
            setCreating();
        } else {
            setEditing();
        }
    }

    public void setCreating() {
        creatingMemo = true;
        disableMenu(getMenu());
    }

    public void setEditing() {
        creatingMemo = false;
        enableMenu(getMenu());
    }

    public void enableMenu(Menu menu) {
        if (menu == null) return;
        menu.findItem(R.id.cmemo_delete).setVisible(true);
    }

    public void disableMenu(Menu menu) {
        if (menu == null) return;
        menu.findItem(R.id.cmemo_delete).setVisible(false);
    }

    private void showDateTimeDialog() {
        DialogFragment fragment = new DateTimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DateTimePickerFragment.ARG_EVENT_TAG, BusAction.CMEMO_TIME);
        fragment.setArguments(bundle);
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

    public void showDeleteDialog() {
        BooleanPickerFragment fragment = new BooleanPickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BooleanPickerFragment.ARG_EVENT_TAG, BusAction.DELETE_MEMO);
        if (mMemo.getDeleted()) {
            bundle.putString(BooleanPickerFragment.ARG_MESSAGE, mResources.getString(R.string.delete_bigday));
        } else {
            bundle.putString(BooleanPickerFragment.ARG_MESSAGE, mResources.getString(R.string.cmemo_set_deleted));
        }

        fragment.setArguments(bundle);
        mIPresenter.showDialog(BooleanPickerFragment.TAG, fragment);
    }

    @Subscribe(tags = {@Tag(BusAction.DELETE_MEMO)})
    public void deleteMemo(Boolean b) {
        if (!b) return;
        mProgressDialog = ProgressDialog.show(getContext(), null, mResources.getString(R.string.deleting_data));
        if (mMemo.getDeleted()) {
            RxBus.get().post(BusAction.DELETE_MEMO, mMemo);
        } else {
            mMemo.setDeleted(true);
            RxBus.get().post(BusAction.SAVE_MEMO, mMemo);
        }
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
    }

    @Subscribe(tags = {@Tag(BusAction.CMEMO_TIME)})
    public void setRemindTime(Date event) {
        Log.d(TAG, "setRemindTime: ");
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

    private void showText() {
        if (mMemo == null || TextUtils.isEmpty(mMemo.getContent())) return;
        SpannableStringBuilder builder = new SpannableStringBuilder();

        StringUtils.renderText(getContext(), mMemo.getContent(),
                builder, mImageHolders, this, etContent);
        Log.d(TAG, "setMemo:\n" + builder);
    }

    private int getImageMaxWidth() {
        if (etContent.getWidth() > 0) {
            return etContent.getWidth();
        }
        int rootWidth = ((AppCompatActivity) getContext()).getWindow().getDecorView().getWidth();
        int width = (int) (rootWidth - getContext().getResources().getDimension(R.dimen.margin_16) * 2);
        return width;
    }

    public void appendImage(Uri uri, Drawable d) {
        String s = StringUtils.encodeImageSpan(uri);
        SpannableString ss = new SpannableString(s);
        ImageSpan span = StringUtils.getSpan(d);
        ss.setSpan(span, 0, s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        etContent.append("\n\n");
        etContent.append(ss);
        etContent.append("\n\n");
        Log.d(TAG, "appendImage: " + etContent.getText());
    }

    @Override
    public void load(Uri uri, Target target) {
        Picasso.with(getContext())
                .load(uri)
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .resize(imageMaxSize, imageMaxSize)
                .onlyScaleDown()
                .into(target);
    }
}
