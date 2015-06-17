package co.gorapido.rapido;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Michael on 6/6/2015.
 */
public class EditItemDialogFragment extends DialogFragment {
    public interface EditItemDialogListener {
        public void onDialogPositiveClick(EditItemDialogFragment dialog);
        public void onDialogNegativeClick(EditItemDialogFragment dialog);
    }
    public static final String TYPE_KEY = "type";
    public static final String NAME_VALUE = "Edit Name";
    public static final String EMAIL_VALUE = "Edit Email";
    public static final String PHONE_VALUE = "Edit Phone Number";
    public static final String CONFIRM_PASSWORD_VALUE = "Confirm Password";
    private boolean isPassword = false;
    String type;
    EditText text;
    EditItemDialogListener listener;
    public void setEditItemDialogListener(EditItemDialogListener listener){
        this.listener = listener;
    }
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(listener == null){
            throw new ClassCastException(activity.toString()
                    + " must implement EditItemDialogListener");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle args = getArguments();
        type = args.getString(TYPE_KEY);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog_edit_item, null);
        text = (EditText)v.findViewById(R.id.edit_text_item_dialog);
        builder.setView(v);
        builder.setMessage(type)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(EditItemDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(EditItemDialogFragment.this);
                    }
                });
        if(isPassword){
            setToPassword();
        }
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public void setIsPassword(boolean isPassword){
        this.isPassword = isPassword;
    }
    private void setToPassword(){
        text.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        text.setSelection(text.getText().length());
    }
    public String getText(){
        return text.getText().toString();
    }
}
