
package com.example.wallpager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("deafult_category")
    @Expose
    private Boolean deafultCategory;
    @SerializedName("locked_for_days")
    @Expose
    private Integer lockedForDays;
    @SerializedName("number_of_wallpapers")
    @Expose
    private Integer numberOfWallpapers;
    @SerializedName("category_name")
    @Expose
    private CategoryName categoryName;
    @SerializedName("category_color")
    @Expose
    private String categoryColor;
    @SerializedName("category_icon")
    @Expose
    private String categoryIcon;
    @SerializedName("category_version")
    @Expose
    private Integer categoryVersion;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Boolean getDeafultCategory() {
        return deafultCategory;
    }

    public void setDeafultCategory(Boolean deafultCategory) {
        this.deafultCategory = deafultCategory;
    }

    public Integer getLockedForDays() {
        return lockedForDays;
    }

    public void setLockedForDays(Integer lockedForDays) {
        this.lockedForDays = lockedForDays;
    }

    public Integer getNumberOfWallpapers() {
        return numberOfWallpapers;
    }

    public void setNumberOfWallpapers(Integer numberOfWallpapers) {
        this.numberOfWallpapers = numberOfWallpapers;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public Integer getCategoryVersion() {
        return categoryVersion;
    }

    public void setCategoryVersion(Integer categoryVersion) {
        this.categoryVersion = categoryVersion;
    }

    public String getNameByLanguage(String language){
        String name = "";
        switch (language){
            case "en":
                name = getCategoryName().getEn();
                break;
            case "ar":
                name= getCategoryName().getAr();
                break;
            case "cs":
                name = getCategoryName().getCs();
                break;
            case "da":
                name = getCategoryName().getDa();
                break;
            case "de":
                name = getCategoryName().getDe();
                break;
            case "es":
                name = getCategoryName().getEs();
                break;
            case "fr":
                name = getCategoryName().getFr();
                break;
            case "hr":
                name = getCategoryName().getHr();
                break;
            case "hu":
                name = getCategoryName().getHu();
                break;
            case "in":
                name = getCategoryName().getIn();
                break;
            case "it":
                name = getCategoryName().getIt();
                break;
            case "ja":
                name = getCategoryName().getJa();
                break;
            case "ko":
                name = getCategoryName().getKo();
                break;
            case "ms":
                name = getCategoryName().getMs();
                break;
            case "nb":
                name = getCategoryName().getNb();
                break;
            case "nl":
                name = getCategoryName().getNl();
                break;
            case "pl":
                name = getCategoryName().getPl();
                break;
            case "pt":
                name = getCategoryName().getPt();
                break;
            case "ro":
                name = getCategoryName().getRo();
                break;
            case "ru":
                name = getCategoryName().getRu();
                break;
            case "sr":
                name = getCategoryName().getSr();
                break;
            case "sv":
                name = getCategoryName().getSv();
                break;
            case "th":
                name = getCategoryName().getTh();
                break;
            case "tr":
                name = getCategoryName().getTr();
                break;
            case "vi":
                name = getCategoryName().getVi();
                break;
            case "zh":
                name = getCategoryName().getZh();
                break;

        }
        return name;
    }

}
