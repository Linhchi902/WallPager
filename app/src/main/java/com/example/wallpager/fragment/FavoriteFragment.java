package com.example.wallpager.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpager.R;
import com.example.wallpager.activity.MainActivity;
import com.example.wallpager.base.BaseFragment;
import com.example.wallpager.base.BaseListImage;
import com.example.wallpager.base.BaseListImageAdapter;
import com.example.wallpager.db.FavoriteDatabase;
import com.example.wallpager.interf.IBaseListListener;
import com.example.wallpager.model.Wallpaper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends BaseFragment implements IBaseListListener {

    private List<Wallpaper> mWallpapers = new ArrayList<>();
    private RecyclerView rcvFavorite;
    private BaseListImageAdapter adapter;


    public List<Wallpaper> getmWallpapers() {
        return mWallpapers;
    }

    public void setmWallpapers(List<Wallpaper> mWallpapers) {
        this.mWallpapers = mWallpapers;
    }

    public BaseListImageAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseListImageAdapter adapter) {
        this.adapter = adapter;
    }

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

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    public void init() {
        adapter = new BaseListImageAdapter(getContext());
        rcvFavorite = getActivity().findViewById(R.id.rcv_favorite);
        rcvFavorite.setAdapter(adapter);
        rcvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setListListener(this);
        initData();


    }

    public void initData() {
        mWallpapers = FavoriteDatabase.getInstance(getContext()).getFavoriteDao().getAllFavorite();
        if (mWallpapers == null) {
            Log.e("TAG", "list null");
        }
        if (adapter == null) {
            Log.e("TAG", "adapter null");
        } else {
            adapter.setmList(showList());
        }

    }

    private List<Wallpaper> showList() {
        List<Wallpaper> arr = new ArrayList<>();
        for (int i = mWallpapers.size() - 1; i >= 0; i--) {
            arr.add(mWallpapers.get(i));
        }
        return arr;
    }


    @Override
    public void itemClickListener(int position) {
        DetailFragment fmDetail = ((MainActivity) getActivity()).getFmDetail();
        MainActivity.temp = MainActivity.fragPos.Favoryte;
        MainActivity.isDetail = true;
        BaseListImage.itemClick(fmDetail, position, showList());

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
        FavoriteDatabase.getInstance(getContext()).getFavoriteDao().delete(mWallpaper);
        initData();
        Toast.makeText(getContext(), "Đã bỏ yêu thích", Toast.LENGTH_SHORT).show();
        if (((MainActivity) getActivity()).getFmPopular().getAdapter() != null)
            ((MainActivity) getActivity()).getFmPopular().getAdapter().notifyDataSetChanged();
    }

    public void itemFavoriteClicked2(Wallpaper mWallpaper2) {

        for (int i = 0; i < mWallpapers.size(); i++) {
            if (mWallpapers.get(i).getJedinstvenId().equals(mWallpaper2.getJedinstvenId())) {
                FavoriteDatabase.getInstance(getContext()).getFavoriteDao().delete(mWallpapers.get(i));
                initData();
                return;
            }

        }
    }


    @Override
    public void itemSetWallpaperClicked(int position) {
        BaseListImage.itemSetWallpaperClicked(getContext(), mWallpapers.get(position));
    }


}
