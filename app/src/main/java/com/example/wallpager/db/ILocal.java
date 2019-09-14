package com.example.wallpager.db;

import androidx.lifecycle.LiveData;

import com.example.wallpager.model.Wallpaper;

import java.util.List;

public interface ILocal {
    List<Wallpaper> getAllFavorite();
    Wallpaper getFavoriteById(int idWallpager);

    void addFavorite(Wallpaper mWallpaper);

    void deleteFavorite(Wallpaper mWallpaper);

    boolean isFavorite(int idWallpager);
}
