package co.gorapido.rapido;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Michael on 6/16/2015.
 */
public class CompanyProfileFragment extends Fragment {
    public static final String COMPANY = "company";
    ImageView IVCompanyProfile;
    TextView TVCompanyName, TVCompanySummary, TVCompanySite;
    Button BTHire;
    Company current;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_company_information, container, false);
        IVCompanyProfile = (ImageView)v.findViewById(R.id.image_view_company_profile_image);
        TVCompanyName = (TextView)v.findViewById(R.id.text_view_company_profile_name);
        TVCompanySummary = (TextView)v.findViewById(R.id.text_view_company_profile_summary);
        TVCompanySite = (TextView)v.findViewById(R.id.text_view_company_profile_site);
        BTHire = (Button)v.findViewById(R.id.button_hire);
        Bundle args = getArguments();
        current = new Company(args);
        TVCompanyName.setText(current.getName());
        TVCompanySummary.setText(current.getSummary());
        IVCompanyProfile.setImageBitmap(current.getLogo());
        TVCompanySite.setText(current.getSite());
        TVCompanySite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = current.getSite();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        BTHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri telephoneNumber = Uri.parse("tel:"+current.getPhone());
//                Intent i = new Intent(Intent.ACTION_DIAL, telephoneNumber);
            }
        });
        return v;
    }
}
