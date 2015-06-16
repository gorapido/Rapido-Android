package co.gorapido.rapido;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by rapido on 6/16/15.
 */
public abstract class ListsFragment extends Fragment {
    protected abstract BaseAdapter setListAdapter();
    protected abstract void onListItemClick(AdapterView<?> parent, View view, int position, long id);
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hire, container, false);
        lv = (ListView) v.findViewById(R.id.list_view_categories_hire);
        lv.setAdapter(setListAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent, view, position, id);
            }
        });
        return v;
    }
}
