package co.gorapido.rapido;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rapido on 6/12/15.
 */
public class HireFragment extends Fragment {
    ListView lv;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hire, container, false);
        String[] categoriesOfCompanies = getResources().getStringArray(R.array.categories_of_companies);
        lv = (ListView) v.findViewById(R.id.list_view_categories_hire);
        List<String> your_array_list = Arrays.asList(categoriesOfCompanies);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, your_array_list);
        lv.setAdapter(arrayAdapter);
        return v;
    }
}
