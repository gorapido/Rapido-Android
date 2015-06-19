package co.gorapido.rapido;

import android.os.AsyncTask;

/**
 * Created by Michael on 6/18/2015.
 */
public class FacebookIDFetcher extends AsyncTask<String, String, String> {
    public interface TaskDelegate {
        public void taskCompletionResult();
    }
    private String id;
    private FacebookIDFetcher.TaskDelegate delegate;
    @Override
    protected String doInBackground(String... params) {
        id = ParseHelper.getFacebookIDFromCurrentUser();
        return id;
    }
    public void setDelegate(TaskDelegate delegate){
        this.delegate = delegate;
    }
    @Override
    protected void onPostExecute(String s) {
        if(id != null){
            FacebookAvatarFetcher fetcher = new FacebookAvatarFetcher();
            fetcher.setDelegate(new FacebookAvatarFetcher.TaskDelegate() {
                @Override
                public void taskCompletionResult() {
                    delegate.taskCompletionResult();
                }
            });
            fetcher.execute(id);
        }
    }
}
