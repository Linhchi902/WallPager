package com.example.wallpager.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpager.R;
import com.example.wallpager.base.BaseFragment;
import com.example.wallpager.base.BaseListImageAdapter;
import com.example.wallpager.db.FavoriteDatabase;
import com.example.wallpager.interf.IBaseListListener;
import com.example.wallpager.model.Wallpaper;

import java.util.List;

public class FavoriteFragment extends BaseFragment implements IBaseListListener {

    private List<Wallpaper> mWallpapers;
    private RecyclerView rcvFavorite;
    private BaseListImageAdapter adapter;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_favorite;
    }

    @Override
    public String getTitle() {
        return "Yêu thích";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseListImageAdapter(getContext());
        initData();
        rcvFavorite =getActivity().findViewById(R.id.rcv_favorite);
        adapter.setListListener(this);
        rcvFavorite.setAdapter(adapter);
    }

    public void initData(){
        mWallpapers = FavoriteDatabase.getInstance(getContext()).getFavoriteDao().getAllFavorite();
        adapter.setmList(mWallpapers);
    }

    @Override
    public void itemClickListener(int position) {

    }

    @Override
    public void itemFavoriteClicked(int position) {

    }

    @Override
    public void itemSetWallpaperClicked(int position) {

    }

    @Override
    public void itemAuthorClicked(int position) {

    }
}
