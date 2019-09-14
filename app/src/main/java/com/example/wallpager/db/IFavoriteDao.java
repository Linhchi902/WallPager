package com.example.wallpager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wallpager.model.Wallpaper;

import java.util.List;

@Dao
public interface IFavoriteDao {

    @Insert
    long insert(Wallpaper mWallpaper);

    @Delete
    void delete(Wallpaper mWallpaper);

    @Update
    void update(Wallpaper mWallpaper);

    @Query("SELECT * FROM favorite_table")
    List<Wallpaper> getAllFavorite();

    @Query("SELECT * FROM favorite_table WHERE id = :idWallpager LIMIT 1")
    Wallpaper getFavoriteById(int idWallpager);


}
