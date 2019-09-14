package com.example.wallpager.interf;

public interface IBaseListListener {
    void itemClickListener(int position);
    void itemFavoriteClicked(int position);
    void itemSetWallpaperClicked(int position);
    void itemAuthorClicked(int position);

}
