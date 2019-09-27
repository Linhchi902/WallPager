package com.example.wallpager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wallpager.model.Wallpaper;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface IFavoriteDao {

    @Insert
    void insert(Wallpaper mWallpaper);

    @Delete
    void delete(Wallpaper mWallpaper);

    @Update
    void update(Wallpaper mWallpaper);

    @Query("SELECT * FROM favorite_table")
    List<Wallpaper> getAllFavorite();


//    @Query("UPDATE favorite_table SET id =1 WHERE id= :id")
//    void setFavorite(long id);
//
//    @Query("UPDATE favorite_table SET id =0 WHERE id= :id")
//    void delFavorite(long id);

//    @Query("SELECT * FROM favorite_table WHERE jedinstvenId = :idWallpager LIMIT 1")
//    Wallpaper getFavoriteById(int idWallpager);


}
