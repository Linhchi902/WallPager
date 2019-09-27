package com.example.wallpager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpager.R;
import com.example.wallpager.activity.DetailActivity;
import com.example.wallpager.activity.MainActivity;
import com.example.wallpager.apputils.AppUtils;
import com.example.wallpager.base.BaseFragment;
import com.example.wallpager.base.BaseListImage;
import com.example.wallpager.base.BaseListImageAdapter;
import com.example.wallpager.db.FavoriteDatabase;
import com.example.wallpager.interf.IBaseListListener;
import com.example.wallpager.model.Wallpaper;

import java.util.ArrayList;
import java.util.List;

public class WallpaperByCategoryFragment extends BaseFragment implements IBaseListListener {

    private RecyclerView rcvWallpaper;
    private String name;
    private String nameShow;
    private List<Wallpaper> mWallpapers;
    private BaseListImageAdapter adapter;
    private Boolean isLike = false;


    @Override
    public int getLayoutID() {
        return R.layout.fragment_wallpaper_by_category;
    }

    @Override
    public String getTitle() {
        return "Wallpaper by category";
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        setActionBar();

    }

    public void init() {
        mWallpapers = new ArrayList<>();
        rcvWallpaper = getActivity().findViewById(R.id.rcv_wallpaper_by_category);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("NameCategory");
            mWallpapers = AppUtils.getListWallpapers(getActivity(), name + ".json");
            nameShow = args.getString("name");
            adapter = new BaseListImageAdapter(getContext());
            adapter.setmList(mWallpapers);
            adapter.setListListener(this);
            rcvWallpaper.setAdapter(adapter);
        }
    }

    public BaseListImageAdapter getAdapter() {
        return adapter;
    }

    public String getNameShow() {
        return nameShow;
    }

    public void setActionBar() {
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(nameShow);
    }


    @Override
    public void itemClickListener(int position) {
        DetailFragment fmDetail = ((MainActivity) getActivity()).getFmDetail();
        BaseListImage.itemClick(fmDetail, position, mWallpapers);
        MainActivity.isDetail = true;
        MainActivity.temp = MainActivity.fragPos.WallPaper;

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .remove(fmDetail)
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .add(R.id.panel, fmDetail)
                .hide(this)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void itemFavoriteClicked(Wallpaper mWallpaper) {
        MainActivity activity = (MainActivity) getActivity();
        if (mWallpaper.isLiked()) {
            mWallpaper.setLiked(false);
            activity.getFmFavorite().itemFavoriteClicked2(mWallpaper);
            activity.getFmFavorite().initData();
            if (((MainActivity) getActivity()).getFmPopular().getAdapter() != null)
                ((MainActivity) getActivity()).getFmPopular().getAdapter().notifyDataSetChanged();
            Toast.makeText(getContext(), "Đã bỏ yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            mWallpaper.setLiked(true);
            FavoriteDatabase.getInstance(getContext()).getFavoriteDao().insert(mWallpaper);
            activity.getFmFavorite().initData();
            if (((MainActivity) getActivity()).getFmPopular().getAdapter() != null)
                ((MainActivity) getActivity()).getFmPopular().getAdapter().notifyDataSetChanged();
            Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void itemSetWallpaperClicked(int position) {
        BaseListImage.itemSetWallpaperClicked(getContext(), mWallpapers.get(position));
    }

}
