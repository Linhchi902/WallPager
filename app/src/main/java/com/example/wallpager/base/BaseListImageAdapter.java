package com.example.wallpager.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpager.interf.IBaseListListener;
import com.example.wallpager.R;
import com.example.wallpager.model.Wallpaper;

import java.util.List;

public class BaseListImageAdapter extends RecyclerView.Adapter<BaseListImageAdapter.BaseViewHolder> {

    private Context mContext;
    private List<Wallpaper> mList;
    private IBaseListListener listListener;

    public BaseListImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmList(List<Wallpaper> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setListListener(IBaseListListener listListener) {
        this.listListener = listListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image,parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        Wallpaper mWallpaper = mList.get(position);

        Glide.with(holder.img).load(mWallpaper.getUrlVelikeSlikeZaPrikaz()).into(holder.img);
        holder.tvName.setText(mWallpaper.getNaslovSlike());
        holder.tvAuthor.setText(mWallpaper.getAutor());
        holder.tvLicena.setText(mWallpaper.getLicenca());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listListener != null){
                    listListener.itemClickListener(position);
                }
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listListener != null){
                    listListener.itemClickListener(position);
                }
            }
        });
        holder.tvAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listListener != null){
                    listListener.itemAuthorClicked(position);
                }
            }
        });
        holder.tvLicena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listListener != null){
                    listListener.itemAuthorClicked(position);
                }
            }
        });
        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listListener.itemFavoriteClicked(position);
            }
        });
        holder.btnSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listListener.itemSetWallpaperClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tvName;
        private TextView tvAuthor;
        private TextView tvLicena;
        private ImageView imgFavorite;
        private Button btnSetWallpaper;
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_list_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvLicena = itemView.findViewById(R.id.tv_licenca);
            imgFavorite = itemView.findViewById(R.id.img_item_favorite);
            btnSetWallpaper = itemView.findViewById(R.id.btn_set_wall_pager);
        }
    }
}
