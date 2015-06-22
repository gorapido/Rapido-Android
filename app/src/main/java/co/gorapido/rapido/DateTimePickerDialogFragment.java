package co.gorapido.rapido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by rapido on 6/22/15.
 */
public class DateTimePickerDialogFragment extends DialogFragment {
    public static final String TAG = "DateTimePickerDialogFragment";
    public interface DateTimePickerDialogListener {
        public void onDialogPositiveClick(DateTimePickerDialogFragment dialog);
    }
    DatePicker datePicker;
    TimePicker timePicker;
    DateTimePickerDialogListener listener;
    public void setDateTimePickerDialogListener(DateTimePickerDialogListener listener){
        this.listener = listener;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_date_time_picker, null);
        datePicker = (DatePicker)v.findViewById(R.id.datePicker);
        timePicker = (TimePicker)v.findViewById(R.id.timePicker);
        builder.setView(v);
        builder.setMessage("Pick a date and time")
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(DateTimePickerDialogFragment.this);
                    }
                });
        return builder.create();
    }
    public Date getDateTime(){
        GregorianCalendar cal = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        return cal.getTime();
    }
}
