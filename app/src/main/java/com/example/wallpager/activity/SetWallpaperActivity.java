package com.example.wallpager.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wallpager.R;
import com.example.wallpager.custom.CustomZoomableImageView;
import com.example.wallpager.download.DownloadFileAsync;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SetWallpaperActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private RelativeLayout relativeLayout;
    private ImageView imgClose;
    private ImageView imgOk;

    private String path;

    Bitmap bitmap1, bitmap2 ;
    DisplayMetrics displayMetrics ;
    int width, height;
    BitmapDrawable bitmapDrawable ;
    WallpaperManager wallpaperManager ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setwallpaper);
        getSupportActionBar().hide();

         path = getIntent().getStringExtra("imgSet");

        img = findViewById(R.id.img_activity_set_wallpaper);
        Glide.with(this).load(getIntent().getStringExtra("imgSet")).into(img);

        relativeLayout = findViewById(R.id.rlt_layout_snack_bar);
        imgOk = findViewById(R.id.img_ok);
        imgClose = findViewById(R.id.img_close);
        imgClose.setOnClickListener(this);
        imgOk.setOnClickListener(this);

        wallpaperManager  = WallpaperManager.getInstance(getApplicationContext());

//        bitmapDrawable = (BitmapDrawable) img.getDrawable();

        bitmap1= BitmapFactory.decodeFile(path);
//        bitmap1 = b;

//        try {
//            URL url = new URL(path);
//            img.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
//        } catch (IOException e) {
//            //Log.e(TAG, e.getMessage());
//        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_ok:
                final ProgressDialog mDialog  = new ProgressDialog(this);
                mDialog.setMessage("Setting...");


                DownloadFileAsync mDownloadFileAsync = new DownloadFileAsync("image" + System.currentTimeMillis() + ".jpg", new DownloadFileAsync.downloadInterface() {
                    @Override
                    public void onProgressUpdate(int progress) {

                    }

                    @Override
                    public void onComplete(String path) {

                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int height = displayMetrics.heightPixels;
                        int width = displayMetrics.widthPixels;

                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(path, options);

                        // Calculate inSampleSize
                        options.inSampleSize = calculateInSampleSize(options, width, height);

                        // Decode bitmap with inSampleSize set
                        options.inJustDecodeBounds = false;
                        Bitmap decodedSampleBitmap = BitmapFactory.decodeFile(path, options);


                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            myWallpaperManager.setBitmap(decodedSampleBitmap);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        mDialog.cancel();
                        Toast.makeText(SetWallpaperActivity.this,"Cài đặt hình nền thành công",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onStart() {
//                Toast.makeText(ImageActivity.this,"Start download !",Toast.LENGTH_SHORT).show();
                        mDialog.show();
                    }
                });
                mDownloadFileAsync.execute(path);


                break;
            case R.id.img_close:
                finish();
                break;
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
