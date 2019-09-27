package com.example.wallpager.fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wallpager.R;
import com.example.wallpager.activity.MainActivity;
import com.example.wallpager.activity.SetWallpaperActivity;
import com.example.wallpager.base.BaseFragment;
import com.example.wallpager.base.BaseListImage;
import com.example.wallpager.db.FavoriteDatabase;
import com.example.wallpager.model.Wallpaper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailFragment extends BaseFragment implements View.OnClickListener {


    private TextView tvName;
    private ImageView imgWallpaper;
    private TextView tvAuthor;
    private TextView tvLicenca;
    private LinearLayout lnSetWallpaper;
    private LinearLayout lnSave;
    private LinearLayout lnFavorite;
    private TextView tvFavorite;
    private ImageView imgIconFavorite;

    private TextView tvStateFavorite;

    private Wallpaper mWallpaper;

    private String url;
    private String name;
    private boolean check;
    private int req;


    @Override
    public int getLayoutID() {
        return R.layout.fragment_detail;
    }

    @Override
    public String getTitle() {
        return "Detail";
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = getActivity().findViewById(R.id.tv_detail_name);
        tvAuthor = getActivity().findViewById(R.id.tv_detail_author);
        tvLicenca = getActivity().findViewById(R.id.tv_detail_licenca);
        imgWallpaper = getActivity().findViewById(R.id.img_detail_wallpaper);
        lnSetWallpaper = getActivity().findViewById(R.id.ln_set_wallpaper);
        lnSave = getActivity().findViewById(R.id.ln_save);
        lnFavorite = getActivity().findViewById(R.id.ln_favorite);
        tvFavorite = getActivity().findViewById(R.id.tv_detail_favorite);
        imgIconFavorite = getActivity().findViewById(R.id.img_detail_icon_favorite);


        Bundle bundle = getArguments();
        if (bundle != null) {


            name = bundle.getString("name");
            String author = bundle.getString("author");
            String licenca = bundle.getString("licenca");
            url = bundle.getString("img");
            tvName.setText(name);
            tvAuthor.setText(author);
            tvLicenca.setText(licenca);
            Glide.with(getContext()).load(url).into(imgWallpaper);
            req = bundle.getInt("request");
            mWallpaper = (Wallpaper) bundle.getSerializable("wallpaper");
            check = bundle.getBoolean("like");
            mWallpaper.setLiked(check);
        }
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);

        if (!check) {
            tvFavorite.setText("Thêm vào yêu thích");
            imgIconFavorite.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_heart_white));
        } else {
            tvFavorite.setText("Xóa khỏi yêu thích");
            imgIconFavorite.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_like_l));
        }
        lnSetWallpaper.setOnClickListener(this);
        lnSave.setOnClickListener(this);
        lnFavorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ln_set_wallpaper:
//                Intent set = new Intent(getContext(), SetWallpaperActivity.class);
//                set.putExtra("imgSet", url);
//                getContext().startActivity(set);
                BaseListImage.itemSetWallpaperClicked(getContext(), mWallpaper);
                break;
            case R.id.ln_save:
//                if (checkPermistion()) {
                saveImageToGallery();
//                }
                break;
            case R.id.ln_favorite:
                MainActivity activity = (MainActivity) getContext();
                if (mWallpaper.isLiked()) {
                    mWallpaper.setLiked(false);

                    activity.getFmFavorite().itemFavoriteClicked2(mWallpaper);
                    activity.getFmFavorite().initData();
                    if (activity.getFmPopular().getAdapter() != null)
                        activity.getFmPopular().getAdapter().notifyDataSetChanged();
                    tvFavorite.setText("Thêm vào yêu thích");
                    imgIconFavorite.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_heart_white));

                } else {
                    mWallpaper.setLiked(true);
                    FavoriteDatabase.getInstance(getContext()).getFavoriteDao().insert(mWallpaper);
                    activity.getFmFavorite().initData();
                    if (activity.getFmPopular().getAdapter() != null)
                        activity.getFmPopular().getAdapter().notifyDataSetChanged();

                    tvFavorite.setText("Xóa khỏi yêu thích");
                    imgIconFavorite.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_like_l));
                }
                break;
        }
    }

    private void saveImageToGallery() {
        File direct = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath() + "/" + "AAA" + "/");


        if (!direct.exists()) {
            direct.mkdir();
            Log.d(TAG, "dir created for first time");
        }

        DownloadManager dm = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(name)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                        File.separator + "AAA" + File.separator + url);

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Saving...");
        dialog.show();
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
        dm.enqueue(request);

    }


}
