package com.example.wallpager.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wallpager.R;

public class SetWallpaperActivity extends AppCompatActivity {

    private ImageView img;
    private ImageView imgClose;
    private ImageView imgOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setwallpaper);
        img = findViewById(R.id.img_activity_set_wallpaper);
        Glide.with(this).load(getIntent().getStringExtra("imgSet")).into(img);
    }
}
