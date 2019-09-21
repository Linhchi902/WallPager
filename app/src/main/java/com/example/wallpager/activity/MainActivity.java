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
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wallpager.R;
import com.example.wallpager.adapter.ViewPagerAdapter;
import com.example.wallpager.fragment.CategoryFragment;
import com.example.wallpager.fragment.DetailFragment;
import com.example.wallpager.fragment.FavoriteFragment;
import com.example.wallpager.fragment.PopularFragment;
import com.example.wallpager.fragment.WallpaperByCategoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

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

    private final String[] PERMISTION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermistion()){
            initViews();
        }
    }

    private void initViews() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fmPopular, fmCategory, fmFavorite);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Popular");
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);

        bottomNavigationView = findViewById(R.id.bottom_nvg_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (item != null){
                    item.setChecked(false);
                }
                else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
                item = bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOffscreenPageLimit(3);

//        tabLayout = findViewById(R.id.tab);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_star);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_airplane);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_heart_white);
//        tabLayout.getTabAt(3).setIcon(R.drawable.ic_more);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_menu_popular:
                viewPager.setCurrentItem(0);
                actionBar.setTitle("Popular");
                checkFrag();
                fmPopular.initFrag();
                break;
            case R.id.item_menu_category:
                viewPager.setCurrentItem(1);
                actionBar.setTitle("Category");
                checkFrag();
                fmCategory.initFrag();
                break;
            case R.id.item_menu_favorite:
                viewPager.setCurrentItem(2);
                actionBar.setTitle("Favorite");
                checkFrag();
                fmFavorite.init();
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
        if (checkPermistion()){
            initViews();
        }
        else {
            return;
        }
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.show(fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void hideFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        fragment = getSupportFragmentManager().findFragmentByTag("fm");
        transaction.hide(fragment);
        transaction.commit();
    }

    private void checkFrag(){
        if (fmDetail.isVisible() && fmDetail != null){
            hideFragment(fmDetail);
        }
        else if (fmWallpaper.isVisible() && fmWallpaper != null){
            hideFragment(fmWallpaper);
        }
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
        return fmDetail;
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
        super.onBackPressed();
//        if (fmDetail.isVisible() ){
//            if(fmWallpaper.isVisible()){
//
//            }
//            else {
//                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
////                getSupportActionBar().setTitle();
//            }
//        }

    }
}
