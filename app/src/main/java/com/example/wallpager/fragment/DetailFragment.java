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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.wallpager.R;
import com.example.wallpager.activity.SetWallpaperActivity;
import com.example.wallpager.base.BaseFragment;
import com.example.wallpager.base.BaseListImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailFragment extends BaseFragment implements View.OnClickListener {

    private final String[] PERMISTION={
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private TextView tvName;
    private ImageView imgWallpaper;
    private TextView tvAuthor;
    private TextView tvLicenca;
    private LinearLayout lnSetWallpaper;
    private LinearLayout lnSave;
    private LinearLayout lnFavorite;
    private TextView tvFavorite;
    private ImageView imgIconFavorite;

    private String url;
    private String name;


    @Override
    public int getLayoutID() {
        return R.layout.fragment_detail;
    }

    @Override
    public String getTitle() {
        return "Detail";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        if (bundle != null){
             name = bundle.getString("name");
            String author = bundle.getString("author");
            String licenca = bundle.getString("licenca");
            url = bundle.getString("img");
            tvName.setText(name);
            tvAuthor.setText(author);
            tvLicenca.setText(licenca);
            Glide.with(getContext()).load(url).into(imgWallpaper);
        }

        lnSetWallpaper.setOnClickListener(this);
        lnSave.setOnClickListener(this);
        lnFavorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ln_set_wallpaper:
                Intent set = new Intent(getContext(), SetWallpaperActivity.class);
                set.putExtra("imgSet",url);
                getActivity().startActivity(set);
                break;
            case R.id.ln_save:
                if (checkPermistion()){
                    saveImage();
                }
                break;
            case R.id.ln_favorite:
                break;
        }
    }

    private boolean checkPermistion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (String p: PERMISTION){
                if (getActivity().checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(PERMISTION,0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermistion()){
            saveImage();
        }
        else {
            return;
        }
    }

    private void saveImage(){
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
                if (dialog.isShowing()){
                    dialog.dismiss();
                    Toast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT).show();
                }

            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
        dm.enqueue(request);

    }

}
