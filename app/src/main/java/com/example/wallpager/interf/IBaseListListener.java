package com.example.wallpager.interf;

import com.example.wallpager.model.Wallpaper;

public interface IBaseListListener {
    void itemClickListener(int position);
    void itemFavoriteClicked(Wallpaper mWallpaper);
    void itemSetWallpaperClicked(int position);

}
