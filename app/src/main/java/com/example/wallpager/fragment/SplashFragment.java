package com.example.wallpager.fragment;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.example.wallpager.R;
import com.example.wallpager.base.BaseFragment;

public class SplashFragment extends BaseFragment {
    private ProgressBar progressBar;
    @Override
    public int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    public String getTitle() {
        return "Splash";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = getActivity().findViewById(R.id.prg_bar_splash);

    }
}
