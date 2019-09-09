package com.tohami.egyptinnovate.ui.base;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.tohami.egyptinnovate.R;
import com.tohami.egyptinnovate.app.settings.AppSettings;
import com.tohami.egyptinnovate.app.localization.LocalizationHelper;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public abstract class BaseActivity extends DaggerAppCompatActivity {

    protected Toolbar myToolbar;
    @Inject
    protected AppSettings appSettings ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalizationHelper.changeAppLanguage(appSettings.getCurrentLanguage().getLanguageCode() ,this);
        setContentView(getLayoutId());
        initializeViews();
        setListeners();
    }

    protected void setToolbar(Toolbar toolbar, int toolbarBackgroundColor, String title, boolean showUpButton, boolean withElevation) {
        myToolbar = toolbar;
        myToolbar.setTitle(title);
        myToolbar.setBackgroundColor(ContextCompat.getColor(this, toolbarBackgroundColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && withElevation) {
            toolbar.setElevation(getResources().getDimension(R.dimen.row_item_margin_horizontal));
        }
        setSupportActionBar(myToolbar);

        showUpActionButton(showUpButton);
    }

    public void setToolbarTitle(String title) {
        if (myToolbar != null) {
            myToolbar.setTitle(title);
        }
    }

    private void showUpActionButton(boolean show) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(show);
        }

    }

    protected void showSimpleSnack(String msg) {
            Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                    .show();
    }

    protected abstract void initializeViews();

    protected abstract void setListeners();

    protected abstract int getLayoutId();
}
