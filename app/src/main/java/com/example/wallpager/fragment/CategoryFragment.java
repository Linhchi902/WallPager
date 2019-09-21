package com.example.wallpager.fragment;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpager.R;
import com.example.wallpager.activity.MainActivity;
import com.example.wallpager.adapter.CategoryAdapter;
import com.example.wallpager.apputils.AppUtils;
import com.example.wallpager.base.BaseFragment;
import com.example.wallpager.model.Category;
import com.example.wallpager.model.CategoryName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment implements CategoryAdapter.ICategory {

    public static final int REQUEST_CATEGORY = 2;
    private List<Category> mListCategories = new ArrayList<>();
    private CategoryAdapter adapter;
    private RecyclerView rcvCategory;
    private ArrayList<CategoryName> mCategoryNames = new ArrayList<>();
    private String name;


    @Override
    public int getLayoutID() {
        return R.layout.fragment_category;
    }

    @Override
    public String getTitle() {
        return "Khám phá";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFrag();
    }

    public void initFrag(){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        rcvCategory = getActivity().findViewById(R.id.rcv_category);
        mListCategories = getmListCategories();
        adapter = new CategoryAdapter(getContext(),mListCategories);
        adapter.setListener(this);
        rcvCategory.setAdapter(adapter);
    }

    private List<Category> getmListCategories(){

        try {
            JSONObject object = new JSONObject(AppUtils.loadJSonFromAsset(getActivity(),"wallpapers_categories.json"));
            JSONArray jsonArray = object.getJSONArray("categories");
            for (int i = 0; i< jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String unique_id = jsonObject.getString("unique_id");
                int locked_for_days = jsonObject.getInt("locked_for_days");
                int number_of_wallpapers = jsonObject.getInt("number_of_wallpapers");
                CategoryName categoryName = getCategoryName().get(i);
                String category_icon = jsonObject.getString("category_icon");


                Category category = new Category();
                category.setCategoryName(categoryName);
                category.setCategoryIcon(category_icon);
                category.setLockedForDays(locked_for_days);
                category.setNumberOfWallpapers(number_of_wallpapers);
                category.setUniqueId(unique_id);

                mListCategories.add(category);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mListCategories;
    }
    private List<CategoryName> getCategoryName() {
        try {
            JSONObject obj = new JSONObject(AppUtils.loadJSonFromAsset(getActivity(),"wallpapers_categories.json"));
            JSONArray m_jArry = obj.getJSONArray("categories");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject ob_category = m_jArry.getJSONObject(i);
                JSONObject ob_category_name = ob_category.getJSONObject("category_name");
                String en = ob_category_name.getString("en");
                String ar = ob_category_name.getString("ar");
                String cs = ob_category_name.getString("cs");
                String da = ob_category_name.getString("da");
                String de = ob_category_name.getString("de");
                String es = ob_category_name.getString("es");
                String fr = ob_category_name.getString("fr");
                String hr = ob_category_name.getString("hr");
                String hu = ob_category_name.getString("hu");
                String it = ob_category_name.getString("it");
                String ja = ob_category_name.getString("ja");
                String ko = ob_category_name.getString("ko");
                String ms = ob_category_name.getString("ms");
                String nb = ob_category_name.getString("nb");
                String nl = ob_category_name.getString("nl");
                String pl = ob_category_name.getString("pl");
                String pt = ob_category_name.getString("pt");
                String ro = ob_category_name.getString("ro");
                String ru = ob_category_name.getString("ru");
                String sr = ob_category_name.getString("sr");
                String sv = ob_category_name.getString("sv");
                String th = ob_category_name.getString("th");
                String tr = ob_category_name.getString("tr");
                String vi = ob_category_name.getString("vi");
                String zh = ob_category_name.getString("zh");


                CategoryName categoryName = new CategoryName();
                categoryName.setEn(en);
                categoryName.setAr(ar);
                categoryName.setCs(cs);
                categoryName.setDa(da);
                categoryName.setDe(de);
                categoryName.setEs(es);
                categoryName.setFr(fr);
                categoryName.setHr(hr);
                categoryName.setHu(hu);
                categoryName.setIt(it);
                categoryName.setJa(ja);
                categoryName.setKo(ko);
                categoryName.setMs(ms);
                categoryName.setNl(nl);
                categoryName.setNb(nb);
                categoryName.setRo(ro);
                categoryName.setPl(pl);
                categoryName.setPt(pt);
                categoryName.setRu(ru);
                categoryName.setSr(sr);
                categoryName.setSv(sv);
                categoryName.setTh(th);
                categoryName.setTr(tr);
                categoryName.setVi(vi);
                categoryName.setZh(zh);

                mCategoryNames.add(categoryName);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mCategoryNames;
    }

    @Override
    public void itemClickListener(int position) {
        WallpaperByCategoryFragment fmWallpaper = new WallpaperByCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("NameCategory", mListCategories.get(position).getUniqueId());
        fmWallpaper.setArguments(bundle);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.panel,fmWallpaper)
                    .hide(this)
                    .show(fmWallpaper)
                    .addToBackStack(null)
                    .commit();



    }
}
