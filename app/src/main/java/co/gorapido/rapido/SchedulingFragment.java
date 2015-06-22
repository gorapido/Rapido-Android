package co.gorapido.rapido;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by rapido on 6/22/15.
 */
public class SchedulingFragment extends Fragment {
    Date date;
    Spinner SPcategories;
    TextView TVdateTime;
    LinearLayout LLdateTime;
    HintAdapter adapter;
    EditText ETstreet, ETcity, ETstate, ETzipcode, ETdescription;
    Button Bsubmit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        ETstreet = (EditText)v.findViewById(R.id.edit_text_street);
        ETcity = (EditText)v.findViewById(R.id.edit_text_city);
        ETstate = (EditText)v.findViewById(R.id.edit_text_state);
        ETzipcode = (EditText)v.findViewById(R.id.edit_text_postal_code);
        populate();
        SPcategories = (Spinner)v.findViewById(R.id.spinner_categories);
        LLdateTime = (LinearLayout)v.findViewById(R.id.linear_layout_date_time_picker);
        TVdateTime = (TextView)v.findViewById(R.id.text_view_date_time_picker);
        ETdescription = (EditText)v.findViewById(R.id.edit_text_problem_description);
        Bsubmit = (Button)v.findViewById(R.id.button_submit);
        adapter = new HintAdapter(getActivity(), getResources().getStringArray(R.array.categories_of_companies), R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        SPcategories.setAdapter(adapter);
        SPcategories.setSelection(adapter.getCount());
        LLdateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerDialogFragment dialog = new DateTimePickerDialogFragment();
                dialog.setDateTimePickerDialogListener(new DateTimePickerDialogFragment.DateTimePickerDialogListener() {
                    @Override
                    public void onDialogPositiveClick(DateTimePickerDialogFragment dialog) {
                        date = dialog.getDateTime();
                        TVdateTime.setText(date.toString());
                        dialog.dismiss();
                    }
                });
                dialog.show(getFragmentManager(), DateTimePickerDialogFragment.TAG);
            }
        });
        Bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()) {
                    ParseHelper.setStringForCurrentUser(ParseHelper.STREET, ETstreet.getText().toString());
                    ParseHelper.setStringForCurrentUser(ParseHelper.CITY, ETcity.getText().toString());
                    ParseHelper.setStringForCurrentUser(ParseHelper.STATE, ETstate.getText().toString());
                    ParseHelper.setStringForCurrentUser(ParseHelper.POSTAL_CODE, ETzipcode.getText().toString());
                    ParseHelper.saveCurrentUser();
                    ParseHelper.createJob(date, SPcategories.getSelectedItem().toString(), ETdescription.getText().toString());
                    Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Please fill out all of the forms", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private void populate(){
        String street, city, state, zipcode;
        street = ParseHelper.getStringFromCurrentUser(ParseHelper.STREET);
        if(street != null){
            ETstreet.setText(street);
        }
        city = ParseHelper.getStringFromCurrentUser(ParseHelper.CITY);
        if(street != null){
            ETcity.setText(city);
        }
        state = ParseHelper.getStringFromCurrentUser(ParseHelper.STATE);
        if(street != null){
            ETstate.setText(state);
        }
        zipcode = ParseHelper.getStringFromCurrentUser(ParseHelper.POSTAL_CODE);
        if(street != null){
            ETzipcode.setText(zipcode);
        }
    }
    public boolean validateInput(){
        boolean isValid = true;
        if(isEmpty(ETstreet)){
            isValid = false;
        }
        if(isEmpty(ETcity)){
            isValid = false;
        }
        if(isEmpty(ETstate)){
            isValid = false;
        }
        if(isEmpty(ETzipcode)){
            isValid = false;
        }
        if(SPcategories.getSelectedItemPosition() == adapter.getCount()){
            isValid = false;
        }
        if(isEmpty(ETdescription)){
            isValid = false;
        }
        if(date == null){
            isValid = false;
        }
        return isValid;
    }
    private boolean isEmpty(EditText input){
        Object string = input.getText();
        if(string != null) {
            return string.toString().length() == 0;
        }else{
            return true;
        }
    }
}
