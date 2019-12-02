package com.example.wallpager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpager.R;
import com.example.wallpager.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<Category> mListCategories = new ArrayList<>();
    private ICategory listener;

    public void setListener(ICategory listener) {
        this.listener = listener;
    }

    public CategoryAdapter(Context mContext, List<Category> mListCategories) {
        this.mContext = mContext;
        this.mListCategories = mListCategories;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, final int position) {
        Category mCategory = mListCategories.get(position);
        if(!isNullorEmty(mCategory.getCategoryName())){
            holder.tvNameCategory.setText(mCategory.getNameByLanguage(Locale.getDefault().getLanguage()));
        }
        else {
            holder.tvNameCategory.setText("Default");
        }
        setColor(holder.imgIconCategory,position);
        if(mCategory.getCategoryIcon() == ""){
            Glide.with(mContext).load(R.drawable.ic_gallery_wallpaper)
                    .into(holder.imgIconCategory);
        }
        else {
            Glide.with(mContext).load(mCategory.getCategoryIcon()).into(holder.imgIconCategory);
        }

        if (listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClickListener(position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mListCategories == null ? 0 : mListCategories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
         private TextView tvNameCategory;
         private ImageView imgIconCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCategory = itemView.findViewById(R.id.tv_item_name_category);
            imgIconCategory = itemView.findViewById(R.id.img_item_category);

        }
    }

    private void setColor(ImageView img,int position){
        switch (position){
            case 0:
                img.setBackgroundResource(R.color.color1);
                break;
            case 1:
                img.setBackgroundResource(R.color.color2);
                break;
            case 2:
                img.setBackgroundResource(R.color.color3);
                break;
            case 3:
                img.setBackgroundResource(R.color.color4);
                break;
            case 4:
                img.setBackgroundResource(R.color.color5);
                break;
            case 5:
                img.setBackgroundResource(R.color.color6);
                break;
            case 6:
                img.setBackgroundResource(R.color.color7);
                break;
            case 7:
                img.setBackgroundResource(R.color.color8);
                break;
            case 8:
                img.setBackgroundResource(R.color.color9);
                break;
            case 9:
                img.setBackgroundResource(R.color.color10);
                break;
        }
    }

    private   boolean isNullorEmty(Object object) {
        if (object == null)
            return true;
        if (object instanceof String) {
            if (((String) object).isEmpty())
                return true;
        } else if (object instanceof EditText) {
            return ((EditText) object).getText().toString().trim().isEmpty();
        }
        if (object instanceof List) {
            return ((List) object).isEmpty();
        }

        if (object instanceof HashMap) {
            return ((HashMap) object).isEmpty();
        }

        return object instanceof ArrayList && ((ArrayList) object).isEmpty();
    }

    public interface ICategory{
        void itemClickListener(int position);
    }
}