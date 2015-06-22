package co.gorapido.rapido;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User0 on 6/3/2015.
 */
public class SignUpFragment extends Fragment{
    public EditText ETfirstName, ETlastName, ETemail, ETpassword, ETphoneNumber;
    public Button BTsignUp;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ETfirstName = (EditText)v.findViewById(R.id.edit_text_first_name_signup);
        ETlastName = (EditText)v.findViewById(R.id.edit_text_last_name_signup);
        ETemail = (EditText)v.findViewById(R.id.edit_text_email_signup);
        ETpassword = (EditText)v.findViewById(R.id.edit_text_password_signup);
        ETphoneNumber = (EditText)v.findViewById(R.id.edit_text_phone_number_signup);
        BTsignUp = (Button)v.findViewById(R.id.button_signup);
        BTsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInput()) {
                    String firstName = ETfirstName.getText().toString();
                    String lastName = ETlastName.getText().toString();
                    String email = ETemail.getText().toString();
                    String password = ETpassword.getText().toString();
                    String phoneNumber = ETphoneNumber.getText().toString();
                    ParseHelper.signUpUser(firstName, lastName, email, password, phoneNumber, getActivity());
                }else{
                    Toast.makeText(getActivity(), "Please fill out all of the forms", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private boolean validInput(){
        boolean isValid = true;
        if(isEmpty(ETfirstName)){
            isValid = false;
        }
        if(isEmpty(ETlastName)){
            isValid = false;
        }
        if(isEmpty(ETemail)){
            isValid = false;
        }
        if(isEmpty(ETpassword)){
            isValid = false;
        }
        if(isEmpty(ETphoneNumber)){
            isValid = false;
        }
        return isValid;
    }
    private boolean isEmpty(EditText text){
        return text.getText() == null;
    }
}
