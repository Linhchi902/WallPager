package com.example.wallpager.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wallpager.model.Wallpaper;

@Database(entities = {Wallpaper.class},version = 3, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static final String DB_NAME = "favorite_db";
    private static FavoriteDatabase instance;
//    abstract IFavoriteDao favoriteDao();
//
//    static synchronized FavoriteDatabase getInstance(Context context){
//        if (instance == null){
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                    FavoriteDatabase.class,DB_NAME)
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
//        return instance;
//    }

    public static FavoriteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,
                    FavoriteDatabase.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public abstract IFavoriteDao getFavoriteDao();
}
