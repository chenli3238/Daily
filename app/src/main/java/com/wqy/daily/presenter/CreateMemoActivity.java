package com.wqy.daily.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.model.Memo;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreateMemoView;

import java.util.Calendar;

public class CreateMemoActivity extends BaseActivity {

    private static final int IMAGE_REQUEST_CODE = 1000;

    @Override
    public IView createIView() {
        return new CreateMemoView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        Memo memo = new Memo();
        RxBus.get().post(BusAction.CMEMO_SET_MEMO, memo);
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CMEMO_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }

    @Subscribe(tags = {@Tag(BusAction.CMEMO_PICK_IMAGE)})
    public void showPicturePicker(String s) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            RxBus.get().post(BusAction.CMEMO_IMAGE, uri);
        }
    }
}
