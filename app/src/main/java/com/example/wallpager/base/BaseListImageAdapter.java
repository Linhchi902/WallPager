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
import com.example.wallpager.db.FavoriteDatabase;
import com.example.wallpager.interf.IBaseListListener;
import com.example.wallpager.R;
import com.example.wallpager.model.Wallpaper;

import java.util.List;

public class BaseListImageAdapter extends RecyclerView.Adapter<BaseListImageAdapter.BaseViewHolder> {

    private Context mContext;
    private List<Wallpaper> mList;
    private IBaseListListener listListener;
    private boolean isLike;

    public BaseListImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseListImageAdapter(Context mContext, List<Wallpaper> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setmList(List<Wallpaper> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setListListener(IBaseListListener listListener) {
        this.listListener = listListener;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        final Wallpaper mWallpaper = mList.get(position);

        Glide.with(holder.img).load(mWallpaper.getUrlVelikeSlikeZaPrikaz()).into(holder.img);
        holder.tvName.setText(mWallpaper.getNaslovSlike());
        holder.tvAuthor.setText("by " + mWallpaper.getAutor());
        holder.tvLicena.setText(mWallpaper.getLicenca());
        if (checkStateItemFavorite(mWallpaper)){
            holder.imgFavorite.setImageDrawable(mContext.getResources()
                    .getDrawable(R.drawable.ic_like_l));
            isLike = true;
            mWallpaper.setLiked(true);
        }
        else {
            holder.imgFavorite.setImageDrawable(mContext.getResources()
                    .getDrawable(R.drawable.ic_heart_white));
            isLike = false;

            mWallpaper.setLiked(false);
        }

        if (listListener != null) {
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listListener.itemClickListener(position);
                }
            });

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listListener.itemClickListener(position);
                }
            });

            holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (checkStateItemFavorite(mWallpaper) && mWallpaper.isLiked()){
                        holder.imgFavorite.setImageDrawable(mContext.getResources()
                                .getDrawable(R.drawable.ic_heart_white));

                        listListener.itemFavoriteClicked(mWallpaper);

                    }
                    else {

                        holder.imgFavorite.setImageDrawable(mContext.getResources()
                                .getDrawable(R.drawable.ic_like_l));

                        listListener.itemFavoriteClicked(mWallpaper);
                    }
                }
            });

            holder.btnSetWallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listListener.itemSetWallpaperClicked(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {
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

    public  boolean checkStateItemFavorite(Wallpaper mWallpaper){
        List<Wallpaper> mList = FavoriteDatabase.getInstance(mContext).getFavoriteDao().getAllFavorite();
        for (int i = 0; i< mList.size(); i++){
            if (mWallpaper.getJedinstvenId().equals(mList.get(i).getJedinstvenId())  ){
                return true;
            }
        }
        return false;
    }
}
