package com.tohami.egyptinnovate.ui.navigation;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tohami.egyptinnovate.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {


    private final View.OnClickListener onClickListener;


    public NavigationDrawerFragment(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.drawer_events).setOnClickListener(onClickListener);
        view.findViewById(R.id.drawer_leadership).setOnClickListener(onClickListener);
        view.findViewById(R.id.drawer_language).setOnClickListener(onClickListener);
        view.findViewById(R.id.drawer_map).setOnClickListener(onClickListener);
        view.findViewById(R.id.drawer_news).setOnClickListener(onClickListener);
    }
}
