package com.tohami.egyptinnovate.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.tohami.egyptinnovate.R;
import com.tohami.egyptinnovate.app.localization.Language;
import com.tohami.egyptinnovate.ui.base.BaseActivity;
import com.tohami.egyptinnovate.ui.navigation.NavigationDrawerFragment;
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModel;
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModelFactory;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;

    @Inject
    NewsListViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NewsListViewModel mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsListViewModel.class);

        if (savedInstanceState == null)
            mViewModel.refreshNewsList();
    }

    @Override
    protected void initializeViews() {
        Toolbar appbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nvView);
        setToolbar(appbar, R.color.colorPrimary, getString(R.string.app_name), true, false);
        navController = Navigation.findNavController(this, R.id.main_nav_container);

    }


    private View.OnClickListener onNavigationItemClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.drawer_events:
                    showSimpleSnack(getString(R.string.envents_clicked));
                    break;

                case R.id.drawer_news:
                    showSimpleSnack(getString(R.string.news_clicked));
                    break;

                case R.id.drawer_language:
                    if(appSettings.getCurrentLanguage() == Language.ENGLLSH){
                        appSettings.SetCurrentLanguage(Language.ARABIC);
                    }else {
                        appSettings.SetCurrentLanguage(Language.ENGLLSH);
                    }
                    finish();
                    startActivity(new Intent(MainActivity.this , MainActivity.class));
                    break;

                case R.id.drawer_leadership:
                    showSimpleSnack(getString(R.string.leadership_clicked));
                    break;

                case R.id.drawer_map:
                    showSimpleSnack(getString(R.string.maps_clicked));
                    break;

            }
            drawerLayout.closeDrawers();
        }
    };

    private void setUpNavDrawerListeners() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }
        };

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_fragment_container, new NavigationDrawerFragment(onNavigationItemClicked))
                .commit();

        mDrawerToggle.setToolbarNavigationClickListener(view -> navController.popBackStack());

        navigationView.setOnClickListener(view -> drawerLayout.closeDrawers());

        drawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void enableDisableNavDrawer(boolean enable) {
        mDrawerToggle.setDrawerIndicatorEnabled(enable);
        drawerLayout.setDrawerLockMode(enable ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerToggle.syncState();
    }

    @Override
    protected void setListeners() {
        setUpNavDrawerListeners();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.fragmentNewsList:
                    enableDisableNavDrawer(true);
                    break;
                case R.id.fragmentNewsDetails:
                    enableDisableNavDrawer(false);
                    break;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }


}
