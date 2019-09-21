package com.example.wallpager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpager.R;
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
    private List<Wallpaper> mWallpapers ;
    private BaseListImageAdapter adapter;
    private Boolean isLike = false;


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
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
    }


    @Override
    public void itemClickListener(int position) {
        DetailFragment fmDetail = new DetailFragment();
        BaseListImage.itemClick(fmDetail,position,mWallpapers);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fr_panel,fmDetail)
                .hide(this)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void itemFavoriteClicked(Wallpaper mWallpaper) {
        MainActivity activity = (MainActivity) getActivity();
        if (mWallpaper.isLiked()){
            mWallpaper.setLiked(false);
            activity.getFmFavorite().itemFavoriteClicked2(mWallpaper);
            activity.getFmFavorite().initData();
            Toast.makeText(getContext(), "Đã bỏ yêu thích", Toast.LENGTH_SHORT).show();
        }
        else  {


            mWallpaper.setLiked(true);
            FavoriteDatabase.getInstance(getContext()).getFavoriteDao().insert(mWallpaper);
            activity.getFmFavorite().initData();
            Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void itemSetWallpaperClicked(int position) {
        BaseListImage.itemSetWallpaperClicked(getContext(),mWallpapers.get(position));
    }

    @Override
    public void itemAuthorClicked(int position) {

    }
}
