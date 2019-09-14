package com.example.wallpager.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
        bundle.putString("img", mList.get(position).getUrlVelikeSlikeZaPrikaz());
        fmDetail.setArguments(bundle);
    }


    public static void itemSetWallpaperClicked(int position,Context mContext, List<Wallpaper> mList) {
        final Intent intent = new Intent(mContext.getApplicationContext(), SetWallpaperActivity.class);
        intent.putExtra("imgSet",mList.get(position).getUrlVelikeSlikeZaPrikaz());
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

    public static void onFavoriteClick(int position, List<Wallpaper> mList, Context context){
        Wallpaper mWallpaper = mList.get(position);
        FavoriteDatabase.getInstance(context).getFavoriteDao().insert(mWallpaper);
    }

}
