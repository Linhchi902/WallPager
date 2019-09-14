package com.example.wallpager.apputils;

import android.app.Activity;
import android.app.ProgressDialog;

import androidx.fragment.app.FragmentTransaction;

import com.example.wallpager.model.Wallpaper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    public  static String loadJSonFromAsset(Activity activity,String fileName){
        String json = null;
        try {
            InputStream in = activity.getAssets().open(fileName);
            int size = in.available();
            byte[] b = new byte[size];
            in.read(b);
            in.close();
            json = new String(b,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<Wallpaper> getListWallpapers(Activity activity, String fileName){
        ArrayList<Wallpaper> mListWallpapers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(AppUtils.loadJSonFromAsset(activity,fileName));
            JSONArray mJsonArray = jsonObject.getJSONArray("wallpapers");

            for (int i = 0; i< mJsonArray.length(); i++){
                JSONObject object = mJsonArray.getJSONObject(i);
                String name = object.getString("naslovSlike");
                String author = object.getString("autor");
                String licenca = object.getString("licenca");
                String urlImage = object.getString("urlVelikeSlikeZaPrikaz");
                String sajtOdakleJeSlika = object.getString("sajtOdakleJeSlika");
                String licenca_url = object.getString("licenca_url");
                String jedinstven_id = object.getString("jedinstven_id");

                Wallpaper mWallpaper = new Wallpaper();
                mWallpaper.setAutor(author);
                mWallpaper.setJedinstvenId(jedinstven_id);
                mWallpaper.setLicenca(licenca);
                mWallpaper.setLicencaUrl(licenca_url);
                mWallpaper.setNaslovSlike(name);
                mWallpaper.setUrlVelikeSlikeZaPrikaz(urlImage);
                mWallpaper.setSajtOdakleJeSlika(sajtOdakleJeSlika);

                mListWallpapers.add(mWallpaper);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mListWallpapers;
    }





}
