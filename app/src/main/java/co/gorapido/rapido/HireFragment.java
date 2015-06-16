package co.gorapido.rapido;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rapido on 6/12/15.
 */
public class HireFragment extends ListsFragment {
    @Override
    protected BaseAdapter setListAdapter() {
        String[] categoriesOfCompanies = getResources().getStringArray(R.array.categories_of_companies);
        List<String> listOfCategories = Arrays.asList(categoriesOfCompanies);
        return new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listOfCategories);
    }

    @Override
    protected void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        String currentCategory = parent.getItemAtPosition(position).toString();
        Intent i = new Intent(getActivity(), ListingActivity.class);
        i.putExtra(CompanyListFragment.CATEGORY, currentCategory);
        startActivity(i);
    }

}
