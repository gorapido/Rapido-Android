package co.gorapido.rapido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by rapido on 6/11/15.
 */
public class EditPasswordDialogFragment extends DialogFragment{
    public static final String PASSWORD_VALUE = "Edit Password";
    private EditText passwordOne, passwordTwo;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog_new_password, null);
        passwordOne = (EditText)v.findViewById(R.id.edit_text_password_one);
        passwordTwo = (EditText)v.findViewById(R.id.edit_text_password_two);
        builder.setView(v);
        builder.setMessage(PASSWORD_VALUE)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String p1 = passwordOne.getText().toString();
                        String p2 = passwordTwo.getText().toString();
                        if(p1.equals(p2)){
                            ParseHelper.setPasswordForCurrentUser(p1);
                            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), R.string.password_matching_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
