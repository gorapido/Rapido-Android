package co.gorapido.rapido;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Michael on 6/3/2015.
 */
public class ProfileFragment extends Fragment {
    TextView TVfullname, TVemail, TVpassword, TVphoneNumber, TVrateRapido, TVcontactUs, TVtermsOfService, TVprivacyPolicy;
    Switch Savailable;
    ImageButton IBprofilePic;
    View v;
    int imgHeight = 0;
    int imgWidth = 0;
    String imgDecodableString;
    public int RESULT_LOAD_IMG = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeWidgets(v);
        setFullname();
        TVemail.setText(ParseHelper.getEmailFromCurrentUser());
        String phone = ParseHelper.getStringFromCurrentUser(ParseHelper.PHONE_NUMBER);
        if(phone != null && phone != "")
            TVphoneNumber.setText(phone);
        setNameListener();
        setPhoneNumberListener();
        setPictureListener();
        setEmailListener();
        setPasswordListener();
        setContactUsListener();
        setRateRapidoListener();
        setTermsOfServiceListener();
        setPrivacyPolicyListener();
        return v;
    }
    public void setPhoneNumberListener(){
        TVphoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditItemDialogFragment dialog = getDialog(EditItemDialogFragment.PHONE_VALUE);
                dialog.setEditItemDialogListener(new EditItemDialogFragment.EditItemDialogListener() {
                    @Override
                    public void onDialogPositiveClick(EditItemDialogFragment dialog) {
                        String phone = dialog.getText();
                        if(ParseHelper.isPhoneValid(phone)){
                            ParseHelper.setStringForCurrentUser(ParseHelper.PHONE_NUMBER, phone);
                            dialog.dismiss();
                            ParseHelper.saveCurrentUser();
                            TVphoneNumber.setText(phone);
                            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), R.string.phone_number_error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDialogNegativeClick(EditItemDialogFragment dialog) {
                    }
                });
                dialog.show(getFragmentManager(), EditItemDialogFragment.PHONE_VALUE);
            }
        });
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewTreeObserver observer = IBprofilePic.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                IBprofilePic.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                imgWidth = IBprofilePic.getWidth();
                imgHeight = IBprofilePic.getHeight();
                ParseHelper.retrieveAvatar(IBprofilePic, imgWidth, imgHeight);
            }
        });
    }

    private void setFullname() {
        TVfullname.setText(ParseHelper.getStringFromCurrentUser(ParseHelper.FIRST_NAME) + " "
                + ParseHelper.getStringFromCurrentUser(ParseHelper.LAST_NAME));
    }

    public void initializeWidgets(View v){
        TVfullname = (TextView)v.findViewById(R.id.text_view_fullname_profile);
        TVphoneNumber = (TextView)v.findViewById(R.id.text_view_phone_number_profile);
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
                EditItemDialogFragment dialog = getDialog(EditItemDialogFragment.NAME_VALUE);
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
                        callGallery();
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
                EditItemDialogFragment dialog = getDialog(EditItemDialogFragment.EMAIL_VALUE);
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
                confirmPassword();
            }
        });
    }
    private void goToLink(String link) {
        String url = link;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void setRateRapidoListener(){
        TVrateRapido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink("https://www.surveymonkey.com/s/FWCMFBG");
            }
        });
    }


    public void setContactUsListener(){
        TVcontactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("alex@gorapido.co");
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
            }
        });
    }
    public void setTermsOfServiceListener(){
        TVtermsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink("https://drive.google.com/file/d/0B4E_KqMyCBfMZkxnRUpfSkIxNlk/view?usp=sharing");
            }
        });
    }
    public void setPrivacyPolicyListener(){
        TVprivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink("https://drive.google.com/a/gorapido.co/file/d/0B4E_KqMyCBfMYm5QanI2aFZTNlk/view?usp=sharing");
            }
        });
    }

    private EditItemDialogFragment getDialog(String key) {
        Bundle args = new Bundle();
        args.putString(EditItemDialogFragment.TYPE_KEY, key);
        EditItemDialogFragment dialog = new EditItemDialogFragment();
        dialog.setArguments(args);
        return dialog;
    }

    private void confirmPassword(){
        EditItemDialogFragment dialog = getDialog(EditItemDialogFragment.CONFIRM_PASSWORD_VALUE);
        dialog.setIsPassword(true);
        dialog.setEditItemDialogListener(new EditItemDialogFragment.EditItemDialogListener() {
            @Override
            public void onDialogPositiveClick(EditItemDialogFragment dialog) {
                if (ParseHelper.loginUser(ParseHelper.getEmailFromCurrentUser(), dialog.getText())) {
                    editPassword();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), R.string.password_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDialogNegativeClick(EditItemDialogFragment dialog) {
            }
        });
        dialog.show(getFragmentManager(), EditItemDialogFragment.CONFIRM_PASSWORD_VALUE);
    }


    private void editPassword() {
        EditPasswordDialogFragment dialog = new EditPasswordDialogFragment();
        dialog.show(getFragmentManager(), EditPasswordDialogFragment.PASSWORD_VALUE);
    }
    private void callGallery(){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                Bitmap image = BitmapFactory.decodeFile(imgDecodableString);
                Bitmap scaled = Bitmap.createScaledBitmap(image, imgWidth, imgHeight, true);
                IBprofilePic.setImageBitmap(scaled);
                ParseHelper.createAvatar(scaled);
            } else {
                Toast.makeText(getActivity(), "You haven't picked an image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            Log.e("ProfileFragment", e.getMessage());
        }

    }
}
