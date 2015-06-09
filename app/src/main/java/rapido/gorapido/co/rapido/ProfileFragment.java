package rapido.gorapido.co.rapido;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Michael on 6/3/2015.
 */
public class ProfileFragment extends Fragment {
    TextView TVfullname, TVemail, TVpassword, TVrateRapido, TVcontactUs, TVtermsOfService, TVprivacyPolicy;
    Switch Savailable;
    ImageButton IVprofilePic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeWidgets(v);
        setFullname();
        TVemail.setText(ParseHelper.getEmailFromCurrentUser());
        TVfullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(EditItemDialogFragment.TYPE_KEY, EditItemDialogFragment.NAME_VALUE);
                EditItemDialogFragment dialog = new EditItemDialogFragment();
                dialog.setArguments(args);
                dialog.setEditItemDialogListener(new EditItemDialogFragment.EditItemDialogListener() {
                    @Override
                    public void onDialogPositiveClick(EditItemDialogFragment dialog) {
                        String name = dialog.getText();
                        String names[] = name.split(" ");
                        if (names.length > 1) {
                            ParseHelper.setStringForCurrentUser(ParseHelper.FIRST_NAME, names[0]);
                            name = "";
                            for (int i = 1; i < names.length; i++) {
                                name += names[i];
                            }
                            ParseHelper.setStringForCurrentUser(ParseHelper.LAST_NAME, name);
                            dialog.dismiss();
                            ParseHelper.saveCurrentUser();
                            setFullname();
                        } else {
                            Toast.makeText(getActivity(), R.string.name_error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDialogNegativeClick(EditItemDialogFragment dialog) {
                    }
                });
                dialog.show(getFragmentManager(), EditItemDialogFragment.NAME_VALUE);
            }
        });
        return v;
    }

    private void setFullname() {
        TVfullname.setText(ParseHelper.getStringFromCurrentUser(ParseHelper.FIRST_NAME) + " "
                + ParseHelper.getStringFromCurrentUser(ParseHelper.LAST_NAME));
    }

    public void initializeWidgets(View v){
        TVfullname = (TextView)v.findViewById(R.id.text_view_fullname_profile);
        TVemail = (TextView)v.findViewById(R.id.text_view_email_profile);
        TVpassword = (TextView)v.findViewById(R.id.text_view_password_profile);
        TVrateRapido = (TextView)v.findViewById(R.id.text_view_rate_rapido_link_profile);
        TVcontactUs = (TextView)v.findViewById(R.id.text_view_contact_us_link_profile);
        TVtermsOfService = (TextView)v.findViewById(R.id.text_view_terms_of_service_link_profile);
        TVprivacyPolicy = (TextView)v.findViewById(R.id.text_view_privacy_policy_link_profile);
        Savailable = (Switch)v.findViewById(R.id.switch_available_profile);
        IVprofilePic = (ImageButton)v.findViewById(R.id.image_button_profile);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_default, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_logout:
                ParseHelper.logout();
                Intent i = new Intent(getActivity(), SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
