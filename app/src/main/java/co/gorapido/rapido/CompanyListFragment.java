package co.gorapido.rapido;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by rapido on 6/16/15.
 */
public class CompanyListFragment extends Fragment {
    public static final String CATEGORY = "category";
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hire, container, false);
        Bundle args = getArguments();
        String currentCategory = args.getString(CATEGORY);
        lv = (ListView)v.findViewById(R.id.list_view_categories_hire);
        ParseHelper.getCompanies(currentCategory, getActivity(), lv);
        return v;
    }
}
