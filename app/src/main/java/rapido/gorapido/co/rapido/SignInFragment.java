package rapido.gorapido.co.rapido;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class SignInFragment extends Fragment {
    private EditText ETemail, ETpassword;
    private Button BTsignIn;
    private TextView TVsignUp;
    public SignInFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ETemail = (EditText)v.findViewById(R.id.edit_text_email_signin);
        ETpassword = (EditText)v.findViewById(R.id.edit_text_password_signin);

        BTsignIn = (Button)v.findViewById(R.id.button_signin);
        BTsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ETemail.getText().toString();
                String password = ETpassword.getText().toString();
                if(ParseHelper.loginUser(email, password)){
                    Toast.makeText(getActivity(), "Logged in!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getActivity(), "Username/Password combo not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TVsignUp = (TextView)v.findViewById(R.id.text_view_signup);
        TVsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SignUpActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}
