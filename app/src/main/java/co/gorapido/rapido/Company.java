package co.gorapido.rapido;

import android.graphics.Bitmap;

/**
 * Created by rapido on 6/16/15.
 */
public class Company {
    private String name, category, phone, site, summary;
    private Bitmap logo;

    public Company(String name, String category,String phone, String site, String summary){
        this.name = name;
        this.category = category;
        this.phone = phone;
        this.site = site;
        this.summary = summary;
    }
    public void setLogo(Bitmap logo){
        this.logo = logo;
    }
    public String getCategory() {
        return category;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }

    public String getSite() {
        return site;
    }

    public String getSummary() {
        return summary;
    }
}
