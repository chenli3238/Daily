package com.wqy.daily.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wqy on 17-2-4.
 */

public abstract class FragmentPresenterImpl extends Fragment implements IPresenter {

    public static final String TAG = FragmentPresenterImpl.class.getSimpleName();

    protected IView mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        // do some configuration
        create(savedInstanceState);

        // create an IView instance
        mView = getIView();
        mView.bindPresenter(this);

        // create root view
        View rootView = mView.create(inflater, container);

        // do some loading work
        created(savedInstanceState);

        // bind views
        mView.created();

        // bind events
        mView.bindEvent();

        // do some optional work
        prepared(savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        mView.destroy();
        destroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        start();
        mView.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        mView.stop();
        stop();
    }

    @Override
    public void create(Bundle savedInstanceState) {

    }

    @Override
    public void created(Bundle savedInstanceState) {

    }

    @Override
    public void prepared(Bundle savedInstanceState) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }
}
