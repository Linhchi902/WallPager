package com.example.wallpager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wallpager.R;
import com.example.wallpager.adapter.ViewPagerAdapter;
import com.example.wallpager.fragment.CategoryFragment;
import com.example.wallpager.fragment.DetailFragment;
import com.example.wallpager.fragment.FavoriteFragment;
import com.example.wallpager.fragment.MoreFragment;
import com.example.wallpager.fragment.PopularFragment;
import com.example.wallpager.fragment.WallpaperByCategoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    private PopularFragment fmPopular = new PopularFragment();
    private CategoryFragment fmCategory = new CategoryFragment();
    private FavoriteFragment fmFavorite = new FavoriteFragment();
    private DetailFragment fmDetail = new DetailFragment();
    private MoreFragment fmMore = new MoreFragment();

    private WallpaperByCategoryFragment fmWallpaper = new WallpaperByCategoryFragment();

    private BottomNavigationView bottomNavigationView;

    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fmPopular, fmCategory, fmFavorite);

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

//        tabLayout = findViewById(R.id.tab);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_star);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_airplane);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_heart);
//        tabLayout.getTabAt(3).setIcon(R.drawable.ic_more);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_menu_popular:
                viewPager.setCurrentItem(0);
//                item.setChecked(true);
//                showFragment(fmPopular);
                break;
            case R.id.item_menu_category:
                viewPager.setCurrentItem(1);
//                item.setChecked(true);
//                showFragment(fmCategory);
                break;
            case R.id.item_menu_favorite:
                viewPager.setCurrentItem(2);
//                item.setChecked(true);
//                showFragment(fmFavorite);
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

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.show(fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
