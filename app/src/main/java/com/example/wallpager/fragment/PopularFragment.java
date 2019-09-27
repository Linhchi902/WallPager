package com.example.wallpager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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


public class PopularFragment extends BaseFragment implements IBaseListListener {
    public static final int REQUEST_POPULAR = 1;

    private RecyclerView rcvListImage;
    private List<Wallpaper> mWallpapers;
    private BaseListImageAdapter adapter;

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

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFrag();
        //appannie.com
    }

    public void initFrag() {
        rcvListImage = getActivity().findViewById(R.id.rcv_popular);
        adapter = new BaseListImageAdapter(getContext());
        mWallpapers = new ArrayList<>();
        mWallpapers = AppUtils.getListWallpapers(getActivity(), "popular.json");

        adapter.setmList(mWallpapers);
        adapter.setListListener(this);
        rcvListImage.setAdapter(adapter);
    }

    public BaseListImageAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void itemClickListener(final int position) {
        final DetailFragment fmDetail = ((MainActivity) getActivity()).getFmDetail();
        MainActivity.temp = MainActivity.fragPos.Popular;
        MainActivity.isDetail = true;

        BaseListImage.itemClick(fmDetail, position, mWallpapers);
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
    public void itemFavoriteClicked(final Wallpaper mWallpaper) {
        MainActivity activity = (MainActivity) getActivity();
        if (mWallpaper.isLiked()) {
            mWallpaper.setLiked(false);
            activity.getFmFavorite().itemFavoriteClicked2(mWallpaper);
            activity.getFmFavorite().initData();
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Đã bỏ yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            mWallpaper.setLiked(true);
            FavoriteDatabase.getInstance(getContext()).getFavoriteDao().insert(mWallpaper);
            activity.getFmFavorite().initData();
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void itemSetWallpaperClicked(int position) {
        BaseListImage.itemSetWallpaperClicked(getContext(), mWallpapers.get(position));
    }

}
