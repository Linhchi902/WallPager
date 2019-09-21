
package com.example.wallpager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "favorite_table")
public class Wallpaper implements Serializable {

//    @ColumnInfo(name = "id")
//    @SerializedName("id")
//    private int id;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @SerializedName("naslovSlike")
    @Expose
    private String naslovSlike;

    @SerializedName("autor")
    @Expose
    private String autor;

    @SerializedName("sajtOdakleJeSlika")
    @Expose
    private String sajtOdakleJeSlika;

    @SerializedName("licenca")
    @Expose
    private String licenca;

    @SerializedName("licenca_url")
    @Expose
    private String licencaUrl;

    @SerializedName("urlVelikeSlikeZaPrikaz")
    @Expose
    private String urlVelikeSlikeZaPrikaz;


    @SerializedName("jedinstven_id")
    private String jedinstvenId;

    @ColumnInfo(name = "liked")
    private boolean liked = false;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaslovSlike() {
        return naslovSlike;
    }

    public void setNaslovSlike(String naslovSlike) {
        this.naslovSlike = naslovSlike;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSajtOdakleJeSlika() {
        return sajtOdakleJeSlika;
    }

    public void setSajtOdakleJeSlika(String sajtOdakleJeSlika) {
        this.sajtOdakleJeSlika = sajtOdakleJeSlika;
    }

    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

    public String getLicencaUrl() {
        return licencaUrl;
    }

    public void setLicencaUrl(String licencaUrl) {
        this.licencaUrl = licencaUrl;
    }

    public String getUrlVelikeSlikeZaPrikaz() {
        return urlVelikeSlikeZaPrikaz;
    }

    public void setUrlVelikeSlikeZaPrikaz(String urlVelikeSlikeZaPrikaz) {
        this.urlVelikeSlikeZaPrikaz = urlVelikeSlikeZaPrikaz;
    }

    public String getJedinstvenId() {
        return jedinstvenId;
    }

    public void setJedinstvenId(String jedinstvenId) {
        this.jedinstvenId = jedinstvenId;
    }

}
