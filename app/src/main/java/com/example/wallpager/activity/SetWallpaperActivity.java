package com.example.wallpager.activity;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wallpager.R;
import com.example.wallpager.download.DownloadFileAsync;
import java.io.IOException;
import uk.co.senab.photoview.PhotoViewAttacher;

public class SetWallpaperActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private RelativeLayout relativeLayout;
    private ImageView imgClose;
    private ImageView imgOk;

    private String path;
    private PhotoViewAttacher viewAttacher;
    private WallpaperManager wallpaperManager ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setwallpaper);
        getSupportActionBar().hide();

         path = getIntent().getStringExtra("imgSet");

        img = findViewById(R.id.img_activity_set_wallpaper);
        Glide.with(this).load(getIntent().getStringExtra("imgSet")).into(img);
         viewAttacher = new PhotoViewAttacher(img);
        viewAttacher.update();



        relativeLayout = findViewById(R.id.rlt_layout_snack_bar);
        imgOk = findViewById(R.id.img_ok);
        imgClose = findViewById(R.id.img_close);
        imgClose.setOnClickListener(this);
        imgOk.setOnClickListener(this);

        wallpaperManager  = WallpaperManager.getInstance(getApplicationContext());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_ok:
                final ProgressDialog mDialog  = new ProgressDialog(this);
                mDialog.setMessage("Setting...");
                mDialog.setCanceledOnTouchOutside(false);

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
//                        Bitmap decodedSampleBitmap = BitmapFactory.decodeFile(path, options);
                        Bitmap decodedSampleBitmap = viewAttacher.getVisibleRectangleBitmap();

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
                        mDialog.show();
                    }
                });
                mDownloadFileAsync.execute(path);


                break;
            case R.id.img_close:
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
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
            // native_ad_layout final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
