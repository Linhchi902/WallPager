package com.example.wallpager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpager.R;
import com.example.wallpager.apputils.AppUtils;
import com.example.wallpager.base.BaseFragment;
import com.example.wallpager.base.BaseListImage;
import com.example.wallpager.base.BaseListImageAdapter;
import com.example.wallpager.interf.IBaseListListener;
import com.example.wallpager.model.Wallpaper;

import java.util.ArrayList;
import java.util.List;

public class WallpaperByCategoryFragment extends BaseFragment implements IBaseListListener {

    private RecyclerView rcvWallpaper;
    private String name;
    private List<Wallpaper> mWallpapers ;
    private BaseListImageAdapter adapter;


    @Override
    public int getLayoutID() {
        return R.layout.fragment_wallpaper_by_category;
    }

    @Override
    public String getTitle() {
        return "Wallpaper";
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWallpapers = new ArrayList<>();
        rcvWallpaper = getActivity().findViewById(R.id.rcv_wallpaper_by_category);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("NameCategory");
            mWallpapers =  AppUtils.getListWallpapers(getActivity(), name + ".json");
            adapter = new BaseListImageAdapter(getContext());
            adapter.setmList(mWallpapers);
            adapter.setListListener(this);
            rcvWallpaper.setAdapter(adapter);
        }
    }


    @Override
    public void itemClickListener(int position) {
        DetailFragment fmDetail = new DetailFragment();
        BaseListImage.itemClick(fmDetail,position,mWallpapers);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.panel,fmDetail)
                .hide(this)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void itemFavoriteClicked(int position) {

    }

    @Override
    public void itemSetWallpaperClicked(int position) {
        BaseListImage.itemSetWallpaperClicked(position,getContext(),mWallpapers);
    }

    @Override
    public void itemAuthorClicked(int position) {

    }
}
