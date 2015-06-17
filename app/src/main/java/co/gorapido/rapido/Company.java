package co.gorapido.rapido;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by rapido on 6/16/15.
 */
public class Company{
    public static final String COMPANY_NAME = "company_name";
    public static final String COMPANY_CATEGORY = "company_category";
    public static final String COMPANY_PHONE = "company_phone";
    public static final String COMPANY_SITE = "company_site";
    public static final String COMPANY_SUMMARY = "company_summary";
    public static final String COMPANY_LOGO = "company_logo";
    private String name, category, phone, site, summary;
    private Bitmap logo;

    public Company(String name, String category,String phone, String site, String summary){
        this.name = name;
        this.category = category;
        this.phone = phone;
        this.site = site;
        this.summary = summary;
    }
    public Company(Bundle args){
        this.name = args.getString(COMPANY_NAME);
        this.category = args.getString(COMPANY_CATEGORY);
        this.phone = args.getString(COMPANY_PHONE);
        this.site = args.getString(COMPANY_SITE);
        this.summary = args.getString(COMPANY_SUMMARY);
        byte[] bytes = args.getByteArray(COMPANY_LOGO);
        this.logo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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

    public Bundle getAsArg(){
        Bundle args = new Bundle();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        logo.compress(Bitmap.CompressFormat.PNG, 100, bs);
        args.putString(COMPANY_NAME, name);
        args.putString(COMPANY_CATEGORY, category);
        args.putString(COMPANY_PHONE, phone);
        args.putString(COMPANY_SITE, site);
        args.putString(COMPANY_SUMMARY, summary);
        args.putByteArray(COMPANY_LOGO, bs.toByteArray());
        return args;
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
