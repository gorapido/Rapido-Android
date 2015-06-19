package co.gorapido.rapido;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Michael on 6/18/2015.
 */
public class FacebookAvatarFetcher extends AsyncTask<String, String, String> {
    public interface TaskDelegate {
        public void taskCompletionResult();
    }
    private TaskDelegate delegate;
    public static String TAG = "FacebookAvatarFetcher";
    private Bitmap avatar;
    private String resp;
    @Override
    protected String doInBackground(String... params) {
        try {
            URL image_value = new URL("https://graph.facebook.com/" + params[0] + "/picture");
            avatar = BitmapFactory.decodeStream(image_value.openConnection().getInputStream());

            resp = "Success";
        }catch( IOException e){
            Log.e(TAG, e.getMessage());
            resp = e.getMessage();
        }
        return resp;
    }
    public void setDelegate(TaskDelegate delegate){
        this.delegate = delegate;
    }
    @Override
    protected void onPostExecute(String s) {
        if(avatar != null) {
            ParseHelper.createAvatar(avatar);
            delegate.taskCompletionResult();
        }
    }
}
