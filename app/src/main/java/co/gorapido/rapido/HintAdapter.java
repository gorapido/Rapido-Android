package co.gorapido.rapido;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Objects;

/**
 * Created by rapido on 6/22/15.
 */
public class HintAdapter   extends ArrayAdapter<Object> {

    public HintAdapter(Context theContext, Object[] objects, int theLayoutResId) {
        super(theContext, theLayoutResId, objects);
    }
    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}