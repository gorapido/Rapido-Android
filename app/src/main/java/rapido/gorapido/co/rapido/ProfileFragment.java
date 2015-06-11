package rapido.gorapido.co.rapido;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    ImageButton IBprofilePic;
    View v;
    public int RESULT_LOAD_IMG = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeWidgets(v);
        setFullname();
        TVemail.setText(ParseHelper.getEmailFromCurrentUser());
        setNameListener();
        setPictureListener();
        setEmailListener();
        setPasswordListener();
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
        IBprofilePic = (ImageButton)v.findViewById(R.id.image_button_profile);
    }

    private void setNameListener() {
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
                            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
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
    }

    private void setPictureListener() {
        IBprofilePic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        IBprofilePic.setColorFilter(new PorterDuffColorFilter(Color.argb(100, 255, 255, 255), PorterDuff.Mode.SRC_OVER));
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                    default: {
                        IBprofilePic.clearColorFilter();
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void setEmailListener() {
        TVemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(EditItemDialogFragment.TYPE_KEY, EditItemDialogFragment.EMAIL_VALUE);
                EditItemDialogFragment dialog = new EditItemDialogFragment();
                dialog.setArguments(args);
                dialog.setEditItemDialogListener(new EditItemDialogFragment.EditItemDialogListener() {
                    @Override
                    public void onDialogPositiveClick(EditItemDialogFragment dialog) {
                        String email = dialog.getText();
                        if (ParseHelper.isEmailValid(email)) {
                            ParseHelper.setEmailForCurrentUser(email);
                            dialog.dismiss();
                            ParseHelper.saveCurrentUser();
                            TVemail.setText(ParseHelper.getEmailFromCurrentUser());
                            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), R.string.email_error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDialogNegativeClick(EditItemDialogFragment dialog) {
                    }
                });
                dialog.show(getFragmentManager(), EditItemDialogFragment.EMAIL_VALUE);
            }
        });
    }

    private void setPasswordListener(){
        TVpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(EditItemDialogFragment.TYPE_KEY, EditItemDialogFragment.PASSWORD_VALUE);
                EditItemDialogFragment dialog = new EditItemDialogFragment();
                dialog.setIsPassword(true);
                dialog.setArguments(args);
                dialog.setEditItemDialogListener(new EditItemDialogFragment.EditItemDialogListener() {
                    @Override
                    public void onDialogPositiveClick(EditItemDialogFragment dialog) {
                        String password = dialog.getText();
                        ParseHelper.setPasswordForCurrentUser(password);
                        dialog.dismiss();
                        ParseHelper.saveCurrentUser();
                        Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDialogNegativeClick(EditItemDialogFragment dialog) {
                    }
                });
                dialog.show(getFragmentManager(), EditItemDialogFragment.PASSWORD_VALUE);
            }
        });
    }

    private void setImageListener(){
        IBprofilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageButton imgButton = (ImageButton) findViewById(R.id.image_button_profile);
                // Set the Image in ImageView after decoding the String
                imgButton.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

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
