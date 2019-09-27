package com.example.wallpager.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.wallpager.activity.DetailActivity;
import com.example.wallpager.activity.SetWallpaperActivity;
import com.example.wallpager.db.FavoriteDatabase;
import com.example.wallpager.fragment.DetailFragment;
import com.example.wallpager.model.Wallpaper;
import java.util.List;

public class BaseListImage{


    public static void itemClick(DetailFragment fmDetail, int position, List<Wallpaper> mList){

        Bundle bundle = new Bundle();
        bundle.putString("name", mList.get(position).getNaslovSlike());
        bundle.putString("author",mList.get(position).getAutor());
        bundle.putString("licenca",mList.get(position).getLicenca());
        bundle.putBoolean("like",mList.get(position).isLiked());
        bundle.putString("img", mList.get(position).getUrlVelikeSlikeZaPrikaz());
        bundle.putSerializable("wallpaper", mList.get(position));
        fmDetail.setArguments(bundle);
    }


    public static void itemSetWallpaperClicked(Context mContext, Wallpaper mWallpaper) {
        final Intent intent = new Intent(mContext.getApplicationContext(), SetWallpaperActivity.class);
        intent.putExtra("imgSet",mWallpaper.getUrlVelikeSlikeZaPrikaz());
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("Vui Lòng đợi");
        dialog.show();
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }

            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
        mContext.startActivity(intent);
    }


}
