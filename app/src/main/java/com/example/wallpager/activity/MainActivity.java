package com.example.wallpager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallpager.R;
import com.example.wallpager.adapter.ViewPagerAdapter;
import com.example.wallpager.fragment.CategoryFragment;
import com.example.wallpager.fragment.DetailFragment;
import com.example.wallpager.fragment.FavoriteFragment;
import com.example.wallpager.fragment.PopularFragment;
import com.example.wallpager.fragment.WallpaperByCategoryFragment;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;

    private ActionBar actionBar;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;


    private PopularFragment fmPopular = new PopularFragment();
    private CategoryFragment fmCategory = new CategoryFragment();
    private FavoriteFragment fmFavorite = new FavoriteFragment();
    private DetailFragment fmDetail = new DetailFragment();

    private WallpaperByCategoryFragment fmWallpaper = new WallpaperByCategoryFragment();

    private BottomNavigationView bottomNavigationView;

    private MenuItem item;
    public static boolean isDetail = false;
    public static boolean isWallpaper = false;
    public static fragPos temp = fragPos.Favoryte;

    public static enum fragPos {
        Popular,
        Favoryte,
        WallPaper
    }

    public static frag tempCa = frag.Category;
    public enum frag{
        Category
    }

    private final String[] PERMISTION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermistion()) {
            initViews();
        }
    }





    public WallpaperByCategoryFragment getFmWallpaper() {
        return fmWallpaper;
    }

    private void initViews() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Popular");

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fmPopular, fmCategory, fmFavorite);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);



        bottomNavigationView = findViewById(R.id.bottom_nvg_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
        bottomNavigationView.setVisibility(View.VISIBLE);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (item != null) {
                    item.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
                item = bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOffscreenPageLimit(3);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_menu_popular:
                viewPager.setCurrentItem(0);
                showFragment(fmPopular);
                actionBar.setTitle("Popular");
                actionBar.setDisplayHomeAsUpEnabled(false);
                break;
            case R.id.item_menu_category:
                viewPager.setCurrentItem(1);
                showFragment(fmCategory);
                actionBar.setTitle("Category");
                actionBar.setDisplayHomeAsUpEnabled(false);
                break;
            case R.id.item_menu_favorite:
                viewPager.setCurrentItem(2);
                showFragment(fmFavorite);
                actionBar.setTitle("Favorite");
                actionBar.setDisplayHomeAsUpEnabled(false);
                break;
            case R.id.item_menu_more:
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("https://play.google.com")));
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                startActivity(launchIntent);
                break;
        }
        return false;
    }

    public boolean checkPermistion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISTION) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISTION, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermistion()) {
            initViews();
        } else {
            return;
        }


    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void showFragment(Fragment fragment) {
        hideFr();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        transaction.show(fragment);
        transaction.commit();
    }

    private void hideFr() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
        transaction.remove(fmDetail);
        transaction.remove(fmWallpaper);
        transaction.commit();
    }

    public PopularFragment getFmPopular() {
        return fmPopular;
    }

    public CategoryFragment getFmCategory() {
        return fmCategory;
    }

    public FavoriteFragment getFmFavorite() {
        return fmFavorite;
    }

    public DetailFragment getFmDetail() {
        return fmDetail =  new DetailFragment();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                Toast.makeText(this, "fail", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isDetail) {
            isDetail = false;
            switch (temp) {
                case Popular:
                    showFragment(fmPopular);
                    actionBar.setTitle("Popular");
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    break;
                case Favoryte:
                    showFragment(fmFavorite);
                    actionBar.setTitle("Favorite");
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    break;
                case WallPaper:
//                    hideFr();
                    getSupportFragmentManager()
                            .beginTransaction()
//                            .add(R.id.panel,fmWallpaper)
                            .remove(fmDetail)
                            .show(fmWallpaper)
                            .commit();
                    isWallpaper = true;
//                    actionBar.setTitle(fmWallpaper.getNameShow());
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    break;
            }

        }
        else {
            if (isWallpaper){
                showFragment(fmCategory);
                isWallpaper = false;
                actionBar.setTitle("Category");
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
            else
                finish();
        }
    }
}
