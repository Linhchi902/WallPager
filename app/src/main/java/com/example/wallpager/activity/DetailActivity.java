package com.example.wallpager.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wallpager.R;
import com.example.wallpager.base.BaseListImage;
import com.example.wallpager.db.FavoriteDatabase;
import com.example.wallpager.fragment.FavoriteFragment;
import com.example.wallpager.fragment.PopularFragment;
import com.example.wallpager.model.Wallpaper;

import java.io.File;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_detail);

        tvName = findViewById(R.id.tv_detail_name);
        tvAuthor = findViewById(R.id.tv_detail_author);
        tvLicenca = findViewById(R.id.tv_detail_licenca);
        imgWallpaper = findViewById(R.id.img_detail_wallpaper);
        lnSetWallpaper = findViewById(R.id.ln_set_wallpaper);
        lnSave = findViewById(R.id.ln_save);
        lnFavorite = findViewById(R.id.ln_favorite);
        tvFavorite = findViewById(R.id.tv_detail_favorite);
        imgIconFavorite = findViewById(R.id.img_detail_icon_favorite);


        Bundle bundle = getIntent().getBundleExtra("b");
        if (bundle != null) {


            name = bundle.getString("name");
            String author = bundle.getString("author");
            String licenca = bundle.getString("licenca");
            url = bundle.getString("img");
            tvName.setText(name);
            tvAuthor.setText(author);
            tvLicenca.setText(licenca);
            Glide.with(this).load(url).into(imgWallpaper);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setTitle(name);

        mWallpaper = (Wallpaper) bundle.getSerializable("wallpaper");
        check = bundle.getBoolean("like");
        mWallpaper.setLiked(check);
        if (!check) {
            tvFavorite.setText("Thêm vào yêu thích");
            imgIconFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_white));
        } else {
            tvFavorite.setText("Xóa khỏi yêu thích");
            imgIconFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_l));
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
                BaseListImage.itemSetWallpaperClicked(this, mWallpaper);
                break;
            case R.id.ln_save:
//                if (checkPermistion()) {
                saveImageToGallery();
//                }
                break;
            case R.id.ln_favorite:
                FavoriteFragment activity = new FavoriteFragment();
                if (mWallpaper.isLiked()) {
                    mWallpaper.setLiked(false);

                    activity.itemFavoriteClicked2(mWallpaper);
                    activity.initData();
                    if ((new PopularFragment()).getAdapter() != null)
                        (new PopularFragment()).getAdapter().notifyDataSetChanged();
                    tvFavorite.setText("Thêm vào yêu thích");
                    imgIconFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_white));

                } else {
                    mWallpaper.setLiked(true);
                    FavoriteDatabase.getInstance(this).getFavoriteDao().insert(mWallpaper);
                    activity.initData();
                    if ((new PopularFragment()).getAdapter() != null)
                        (new PopularFragment()).getAdapter().notifyDataSetChanged();

                    tvFavorite.setText("Xóa khỏi yêu thích");
                    imgIconFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_l));
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

        DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(name)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                        File.separator + "AAA" + File.separator + url);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving...");
        dialog.show();
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    Toast.makeText(DetailActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
        dm.enqueue(request);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//            ((MainActivity) getActivity()).hideFragment(this);
                Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "fail", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
