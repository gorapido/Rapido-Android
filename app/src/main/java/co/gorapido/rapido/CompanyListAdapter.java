package co.gorapido.rapido;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by rapido on 6/16/15.
 */
public class CompanyListAdapter extends BaseAdapter{
    private List<Company> objects;

    private Activity context;

    public CompanyListAdapter(List<Company> objects, Activity context) {
        // TODO Auto-generated constructor stub
        super();
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (objects != null) {
            return objects.size();
        }
        return 0;

    }
    public void add(Company company){
        objects.add(company);
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        TextView companyName = null;
        ImageView companyImage = null;

        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.list_item_company, parent,false);
        } else {
            view = convertView;
        }
        companyName = (TextView)view.findViewById(R.id.text_view_company_name);
        companyImage = (ImageView)view.findViewById(R.id.image_view_company_image);

        if (objects != null) {
            if (objects.get(position).getName() != null) {
                companyName.setText(objects.get(position).getName());
                if (objects.get(position).getLogo() != null) {
                    companyImage.setImageBitmap(objects.get(position).getLogo());
                } else {
                    companyName.setText("Company");
                    companyImage.setImageResource(R.drawable.profile);
                }

            }

        }
        return view;

    }

}
