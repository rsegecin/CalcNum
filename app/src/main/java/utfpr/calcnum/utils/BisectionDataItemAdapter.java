package utfpr.calcnum.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import utfpr.calcnum.R;
import utfpr.calcnum.data.DataBisection;

/**
 * Created by rsegecin on 3/21/2016.
 */
public class BisectionDataItemAdapter extends ArrayAdapter<DataBisection> {

    Context context;
    int layoutResourceId;
    ArrayList<DataBisection> bisections;

    private static class ItemViewHolder {
        TextView tvA;
        TextView tvB;
        TextView tvX;
        TextView tvFa;
        TextView tvFb;
        TextView tvFx;
    }

    public BisectionDataItemAdapter(Context contextParam, int resource, ArrayList<DataBisection> objects) {
        super(contextParam, resource, objects);
        context = contextParam;
        layoutResourceId = resource;
        bisections = objects;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        return getCustomView(position, view, viewGroup);
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        return getCustomView(pos, view, viewGroup);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = (LinearLayout) convertView;
        ItemViewHolder vh = new ItemViewHolder();

        if (view == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) vi.inflate(layoutResourceId, null);
        }

        vh.tvA = (TextView) view.findViewById(R.id.tvA);
        vh.tvB = (TextView) view.findViewById(R.id.tvB);
        vh.tvX = (TextView) view.findViewById(R.id.tvX);
        vh.tvFa = (TextView) view.findViewById(R.id.tvFa);
        vh.tvFb = (TextView) view.findViewById(R.id.tvFb);
        vh.tvFx = (TextView) view.findViewById(R.id.tvFx);

        view.setTag(vh);

        NumberFormat formatter;
        formatter = new DecimalFormat("0.###E0");

        vh.tvA.setText(String.valueOf(formatter.format(bisections.get(position).A)));
        vh.tvB.setText(String.valueOf(formatter.format(bisections.get(position).B)));
        vh.tvX.setText(String.valueOf(formatter.format(bisections.get(position).X)));
        vh.tvFa.setText(String.valueOf(formatter.format(bisections.get(position).Fa)));
        vh.tvFb.setText(String.valueOf(formatter.format(bisections.get(position).Fb)));
        vh.tvFx.setText(String.valueOf(formatter.format(bisections.get(position).Fx)));

        return view;
    }
}
