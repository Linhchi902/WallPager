package com.example.wallpager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpager.R;
import com.example.wallpager.activity.MainActivity;
import com.example.wallpager.activity.SetWallpaperActivity;
import com.example.wallpager.apputils.AppUtils;
import com.example.wallpager.base.BaseFragment;
import com.example.wallpager.base.BaseListImage;
import com.example.wallpager.base.BaseListImageAdapter;
import com.example.wallpager.interf.IBaseListListener;
import com.example.wallpager.model.Wallpaper;

import java.util.ArrayList;
import java.util.List;


public class PopularFragment extends BaseFragment implements IBaseListListener {
    public static final int REQUEST_POPULAR = 1;

    private RecyclerView rcvListImage;
    private List<Wallpaper> mWallpapers;
    private BaseListImage baseListImage;
    private BaseListImageAdapter adapter;
    private static PopularFragment instance;

    public static PopularFragment getInstance(){
        if (instance == null){
            return null;
        }
        return instance;
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_popular;
    }

    @Override
    public String getTitle() {
        return "Phổ biến";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rcvListImage = getActivity().findViewById(R.id.rcv_popular);
        adapter = new BaseListImageAdapter(getContext());
        mWallpapers = new ArrayList<>();
        mWallpapers = AppUtils.getListWallpapers(getActivity(),"popular.json");
        adapter.setmList(mWallpapers);
        adapter.setListListener(this);
        rcvListImage.setAdapter(adapter);
//        baseListImage = new BaseListImage(getContext());
//        baseListImage.setmList();
//        baseListImage.setmRecyclerView(rcvListImage);
//        baseListImage.initViews();
        //appannie.com

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
