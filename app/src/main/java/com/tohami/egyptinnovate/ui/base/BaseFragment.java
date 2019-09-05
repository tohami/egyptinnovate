package com.tohami.egyptinnovate.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.tohami.egyptinnovate.utilities.Constants;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {

    protected abstract View initializeViews(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void setListeners();

    protected abstract int getLayoutId();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = initializeViews(getLayoutId(), inflater, container, savedInstanceState);
        setListeners();

        return rootView;
    }

    protected void showSimpleSnack(String msg) {
        if (getView() != null)
            Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT)
                    .show();
    }
}
