package com.example.wallpager.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.wallpager.model.Wallpaper;

import java.util.List;

public class WallpagerLocalData implements ILocal {
    private static WallpagerLocalData mWallpagerLocalData;
    private IFavoriteDao mDao;
    private List<Wallpaper> mListFavorite;

    public WallpagerLocalData(Context context) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(context);
        mDao = database.getFavoriteDao();
        mListFavorite = mDao.getAllFavorite();
    }

    public static WallpagerLocalData getInstance(Context context){
        if (mWallpagerLocalData == null) mWallpagerLocalData = new WallpagerLocalData(context);
        return mWallpagerLocalData;
    }

    @Override
    public List<Wallpaper> getAllFavorite() {
        return mListFavorite;
    }

    @Override
    public Wallpaper getFavoriteById(int idWallpager) {
        return mDao.getFavoriteById(idWallpager);
    }

    @Override
    public void addFavorite(Wallpaper mWallpaper) {

    }

    @Override
    public void deleteFavorite(Wallpaper mWallpaper) {

    }

    @Override
    public boolean isFavorite(int idWallpager) {
        return false;
    }
}
