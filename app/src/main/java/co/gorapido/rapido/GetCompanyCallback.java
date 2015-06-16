package co.gorapido.rapido;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;

/**
 * Created by Michael on 6/16/15.
 */
public class GetCompanyCallback implements GetDataCallback {
    Company company;
    CompanyListAdapter listAdapter;
    public GetCompanyCallback(Company company, CompanyListAdapter listAdapter){
        super();
        this.company = company;
        this.listAdapter = listAdapter;
    }
    @Override
    public void done(byte[] bytes, ParseException e) {
        if(e == null) {
            Bitmap logo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            company.setLogo(logo);
            listAdapter.add(company);
        }else{
            Toast.makeText(ParseHelper.context, "Error fetching image", Toast.LENGTH_SHORT).show();
        }
    }
}