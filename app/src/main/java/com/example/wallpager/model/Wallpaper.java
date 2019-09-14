
package com.example.wallpager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorite_table")
public class Wallpaper {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @Ignore
    @SerializedName("naslovSlike")
    @Expose
    private String naslovSlike;

    @Ignore
    @SerializedName("autor")
    @Expose
    private String autor;

    @Ignore
    @SerializedName("sajtOdakleJeSlika")
    @Expose
    private String sajtOdakleJeSlika;

    @Ignore
    @SerializedName("licenca")
    @Expose
    private String licenca;

    @Ignore
    @SerializedName("licenca_url")
    @Expose
    private String licencaUrl;

    @Ignore
    @SerializedName("urlVelikeSlikeZaPrikaz")
    @Expose
    private String urlVelikeSlikeZaPrikaz;

    @Ignore
    @SerializedName("jedinstven_id")
    @Expose
    private String jedinstvenId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
